package com.volodia.estafetatest.api;

import com.volodia.estafetatest.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/mobilesurveytasks/gettestsurveytasks")
    Call<List<Task>> testTasks();

}