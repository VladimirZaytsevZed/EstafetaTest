package com.volodia.estafetatest.ui;

import android.util.Log;

import com.volodia.estafetatest.Router;
import com.volodia.estafetatest.model.Task;
import com.volodia.estafetatest.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Volodia on 27.11.2016.
 */
public class PresenterTasksList {


    FragmentTasksList fragmentTasksList;
    ApiService apiService;
    Router router;
    Call<List<Task>> taskCall;

    private boolean released;

    public PresenterTasksList(FragmentTasksList fragmentTasksList, ApiService apiService, Router router) {
        this.fragmentTasksList = fragmentTasksList;
        this.apiService = apiService;
        this.router = router;
    }

    public void request() {
        taskCall = apiService.testTasks();
        if (released) {
            return;
        }
        taskCall.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    fragmentTasksList.setData(response.body());

                } else {
                    fragmentTasksList.loadDataFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                fragmentTasksList.loadDataFailed(t.getMessage());
            }
        });
    }

    public void taskSelected(Task item) {
        router.showTaskDetails(item);
    }

    public void logout() {
        router.logout();
    }

    public void release() {
        if (taskCall != null && taskCall.isExecuted()) {
            taskCall.cancel();
        }
        released = true;
        fragmentTasksList = null;
        router = null;
    }
}
