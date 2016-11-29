package com.volodia.estafetatest;

import com.volodia.estafetatest.model.Task;

/**
 * Created by Volodia on 28.11.2016.
 */

public interface Router {

    void logout();

    void loginSuccess(String login, String pass);

    void showTasksList();

    void showTaskDetails(Task task);

    void showLoginScreen();
}
