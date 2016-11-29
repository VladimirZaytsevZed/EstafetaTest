package com.volodia.estafetatest.utils;

import android.content.Context;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Volodia on 28.11.2016.
 */
public class Utils {

    public static boolean validateNotEmptyEditText(EditText textView, String error) {
        if (textView.getText().toString().replaceAll("\\s", "").isEmpty()) {
            textView.setError(error);
            if (!textView.isSelected())
                textView.addTextChangedListener(new ErrorTextWatcher(textView));
            textView.setSelected(true);
            return false;
        }
        return true;
    }

    private static class ErrorTextWatcher extends SimpleTextWatcher {
        private final EditText target;

        public ErrorTextWatcher(EditText target) {
            this.target = target;
        }

        @Override public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            target.setSelected(false);
            target.removeTextChangedListener(this);
        }
    }

    public static void hideKeyBoard(EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }


}
