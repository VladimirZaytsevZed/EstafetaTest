package com.volodia.estafetatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.volodia.estafetatest.model.Task;
import com.volodia.estafetatest.ui.FragmentLogin;
import com.volodia.estafetatest.ui.FragmentTaskDetails;
import com.volodia.estafetatest.ui.FragmentTasksList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Router {

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    @Inject
    PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        AppEstafeta.get(this).getComponentApp().inject(this);

        if (savedInstanceState != null) return;
        if (prefsManager.getUserLogin() != null) {
            AppEstafeta.get(this).createComponentNet(prefsManager.getUserLogin(), prefsManager.getUserPass());
            showTasksList();
        } else {
            showLoginScreen();
        }

    }

    public void logout(){
        prefsManager.clearUser();
        showLoginScreen();
    }

    @Override
    public void loginSuccess(String login, String pass) {
        prefsManager.saveLoginPass(login, pass);
        showTasksList();
    }

    public void showTasksList() {
        getFragmentManager().beginTransaction().replace(R.id.fl_content, new FragmentTasksList()).
                setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void showTaskDetails(Task task) {
        getFragmentManager().beginTransaction().add(R.id.fl_content, FragmentTaskDetails.newInstance(task)).
                setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
    }

    public void showLoginScreen() {
        getFragmentManager().beginTransaction().replace(R.id.fl_content, new FragmentLogin()).
                setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }


}
