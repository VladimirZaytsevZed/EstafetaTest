package com.volodia.estafetatest.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volodia.estafetatest.R;
import com.volodia.estafetatest.model.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Volodia on 27.11.2016.
 */
public class FragmentTaskDetails extends Fragment {
    public static final String TASK_KEY = "task";

    @BindView(R.id.tv_task_details)
    TextView tv_task_details;

    Task task;

    public static FragmentTaskDetails newInstance(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TASK_KEY, task);

        FragmentTaskDetails fragment = new FragmentTaskDetails();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            task = (Task) bundle.getSerializable(TASK_KEY);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.spinner).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tv_task_details.setText(task.toString());
    }
}
