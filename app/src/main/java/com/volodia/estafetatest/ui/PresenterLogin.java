package com.volodia.estafetatest.ui;

import android.util.Log;

import com.volodia.estafetatest.AppEstafeta;
import com.volodia.estafetatest.Router;
import com.volodia.estafetatest.model.Task;
import com.volodia.estafetatest.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Volodia on 28.11.2016.
 */
public class PresenterLogin {

    FragmentLogin fragmentLogin;
    Router router;

    @Inject
    ApiService apiService;

    public PresenterLogin(FragmentLogin fragmentLogin, Router router) {
        this.fragmentLogin = fragmentLogin;
        this.router = router;
    }

    public void login(final String login, final String password) {
        AppEstafeta.get(fragmentLogin.getActivity()).createComponentNet(login, password).inject(this);

        Call<List<Task>> call = apiService.testTasks();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                fragmentLogin.setSignInProgress(false);
                if (response.isSuccessful()) {
                    fragmentLogin.setSignInProgress(false);
                    router.loginSuccess(login, password);
                } else {
                    fragmentLogin.onLoginFail(response.message() );
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                fragmentLogin.setSignInProgress(false);
                fragmentLogin.onLoginFail(t.getMessage());
            }
        });
    }

}
