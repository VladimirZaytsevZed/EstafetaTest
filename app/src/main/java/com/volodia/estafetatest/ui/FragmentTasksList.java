package com.volodia.estafetatest.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.volodia.estafetatest.AppEstafeta;
import com.volodia.estafetatest.R;
import com.volodia.estafetatest.Router;
import com.volodia.estafetatest.model.Task;
import com.volodia.estafetatest.utils.AbstractFilterableAdapter;
import com.volodia.estafetatest.utils.VHItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Volodia on 27.11.2016.
 */
public class FragmentTasksList extends Fragment implements AdapterView.OnItemSelectedListener {


    @Inject
    PresenterTasksList presenterTasksList;


    @BindView(R.id.rv_tasks)
    RecyclerView rv_tasks;
    private SearchView searchView;
    Spinner spinner;
    TasksAdapter tasksAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        AppEstafeta.get(getActivity())
                .getComponentNet()
                .plus(new ModuleTasksList(this, (Router) getActivity()))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        ButterKnife.bind(this, view);
        rv_tasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterTasksList.release();
    }

    @OnClick(R.id.bt_logout)
    public void logoutClick() {
        presenterTasksList.logout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();


        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<TaskAtr> adapter = new ArrayAdapter<TaskAtr>(getActivity(),
                android.R.layout.simple_spinner_item, TaskAtr.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);

        spinner.setAdapter(adapter);

        if (tasksAdapter == null) {
            presenterTasksList.request();
        } else {
            searchView.setOnQueryTextListener(tasksAdapter.getQueryTextListener(rv_tasks));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (tasksAdapter == null) return;
        tasksAdapter.setSelectedAtr((TaskAtr) parent.getItemAtPosition(position));
        searchView.setQuery("", false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setData(List<Task> tasks) {
        tasksAdapter = new FragmentTasksList.TasksAdapter(tasks);
        rv_tasks.setAdapter(tasksAdapter);
        searchView.setOnQueryTextListener(tasksAdapter.getQueryTextListener(rv_tasks));
    }

    public void loadDataFailed(String message) {
        rv_tasks.setBackgroundColor(Color.GREEN);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    private class TasksAdapter extends AbstractFilterableAdapter<Task, TasksAdapter> {

        TaskAtr selectedAtr = TaskAtr.BRAND;

        public TasksAdapter(List<Task> data) {
            super(data);
        }

        @Override
        public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
            return new TaskViewHolder(view, this);
        }

        @Override
        public boolean compare(String query, Task item) {
            String text;
            switch (selectedAtr) {
                case BRAND:
                    text = item.getBrand();
                    break;
                case CARRIER:
                    text = item.getCarrier();
                    break;
                case DRIVER:
                    text = item.getDriver();
                    break;
                default:
                    text = item.getModel();
                    break;
            }

            return text != null && text.toLowerCase().startsWith(query);
        }

        public void setSelectedAtr(TaskAtr position) {
            selectedAtr = position;
        }
    }

    private void showTask(Task task) {
        presenterTasksList.taskSelected(task);
    }

    class TaskViewHolder extends VHItem<Task, TasksAdapter> {

        @BindView(R.id.tv_brand)
        TextView tv_brand;

        @BindView(R.id.tv_model)
        TextView tv_model;

        @BindView(R.id.tv_carrier)
        TextView tv_carrier;

        @BindView(R.id.tv_driver)
        TextView tv_driver;

        public TaskViewHolder(View itemView, final TasksAdapter adapter) {
            super(itemView, adapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTask(adapter.getItem(getAdapterPosition()));
                }
            });
        }

        @Override
        public void applyData(Task task) {
            tv_brand.setText(createString(R.string.brand, task.getBrand()));
            tv_model.setText(createString(R.string.model, task.getModel()));
            tv_carrier.setText(createString(R.string.carrier, task.getCarrier()));
            tv_driver.setText(createString(R.string.driver, task.getDriver()));
        }

        public Spannable createString(int stringRes, String value) {
            String text = getString(stringRes);
            SpannableStringBuilder span = new SpannableStringBuilder(text);
            span.append(" : ");
            span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, text.length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (value != null && value.length() > 0) {
                span.append(" ").append(value);
            }
            return span;
        }

    }

    public enum TaskAtr {
        BRAND("Brand"), MODEL("Model"), CARRIER("Carrier"), DRIVER("Driver");

        String name;

        TaskAtr(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
