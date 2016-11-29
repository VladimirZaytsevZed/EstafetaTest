package com.volodia.estafetatest.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.volodia.estafetatest.utils.ProgressLayout;
import com.volodia.estafetatest.R;
import com.volodia.estafetatest.Router;
import com.volodia.estafetatest.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Volodia on 28.11.2016.
 */
public class FragmentLogin extends Fragment {

    @BindView(R.id.et_login)
    EditText et_login;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.pl_sign_in)
    ProgressLayout pl_sign_in;

    PresenterLogin presenterLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterLogin = new PresenterLogin(this, (Router) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @OnClick(R.id.btn_sign_in)
    public void onSignInClick(View v) {
        if (!validateFields())
            return;
        if (pl_sign_in.isInProgress()) return;
        Utils.hideKeyBoard(et_password);
        presenterLogin.login(et_login.getText().toString(), et_password.getText().toString());
    }

    public void setSignInProgress(boolean show) {
        pl_sign_in.setProgressVisibility(show);
    }

    public void onLoginFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        boolean isValid = Utils.validateNotEmptyEditText(et_login, getString(R.string.empty_login))
                & Utils.validateNotEmptyEditText(et_password, getString(R.string.empty_password));
        return isValid;
    }
}
