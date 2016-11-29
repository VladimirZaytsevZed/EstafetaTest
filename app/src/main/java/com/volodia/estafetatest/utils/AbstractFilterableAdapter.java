package com.volodia.estafetatest.utils;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilterableAdapter<T, A> extends RecyclerView.Adapter<VHItem<T, A>> {

    protected List<T> currentItems;
    protected List<T> allItems;


    public AbstractFilterableAdapter(List<T> allItems) {
        this.allItems = allItems;
        this.currentItems = new ArrayList<>(allItems);
    }

    public void animateTo(List<T> items) {
        applyAndAnimateRemovals(items);
        applyAndAnimateAdditions(items);
        applyAndAnimateMovedItems(items);
    }

    private void applyAndAnimateRemovals(List<T> newItems) {
        for (int i = currentItems.size() - 1; i >= 0; i--) {
            final T item = currentItems.get(i);
            if (!newItems.contains(item)) {
                removeItem(i);
            }
        }
    }

    public T removeItem(int position) {
        final T item = currentItems.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void addItem(int position, T item) {
        currentItems.add(position, item);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final T item = currentItems.remove(fromPosition);
        currentItems.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void applyAndAnimateAdditions(List<T> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            final T item = newItems.get(i);
            if (!currentItems.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<T> newItems) {
        for (int toPosition = newItems.size() - 1; toPosition >= 0; toPosition--) {
            final T item = newItems.get(toPosition);
            final int fromPosition = currentItems.indexOf(item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        return currentItems.size();
    }

    @Override
    public void onBindViewHolder(VHItem<T, A> holder, int position) {
        holder.applyData(currentItems.get(position));
    }

    public abstract boolean compare(String query, T item);

    public void filter(String query) {
        final List<T> filteredItems = new ArrayList<>();
        if (query.isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            query = query.toLowerCase();
            for (T item : allItems) {
                if (compare(query, item)) {
                    filteredItems.add(item);
                }
            }
        }
        animateTo(filteredItems);
    }

    public void removeItem(T item) {
        allItems.remove(item);
    }

    public void addItem(T item) {
        allItems.add(item);
    }

    public SearchView.OnQueryTextListener getQueryTextListener(RecyclerView recyclerView) {
        return new QueryTextListener(recyclerView);
    }

    public T getItem(int adapterPosition) {
        return currentItems.get(adapterPosition);
    }

    class QueryTextListener implements SearchView.OnQueryTextListener {

        List<Bitmap> list = new ArrayList<>();

        private final RecyclerView recyclerView;

        public QueryTextListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            for (int i = 0; i < 100; i++) {
                list.add(Bitmap.createBitmap(1000, 1000, Bitmap.Config.ALPHA_8));
            }
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filter(newText);
            recyclerView.scrollToPosition(0);
            return true;
        }
    }

}
