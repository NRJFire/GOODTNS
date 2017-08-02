package kr.sofac.goodtns.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import kr.sofac.goodtns.R;
import timber.log.Timber;

import static kr.sofac.goodtns.Constants.APP_PREFERENCES;
import static kr.sofac.goodtns.Constants.IS_AUTHORIZATION;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static SharedPreferences preferences;
    Intent intent;
    private static long backPressed;
    EditText editPassword, editLogin;
    Button buttonLogin;
    Spinner spinnerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        buttonLogin.setOnClickListener(this);
        intent = new Intent(this, MainActivity.class);


//        try {
//        ManagerInfoDTO.deleteAll(ManagerInfoDTO.class);
//        PermissionDTO.deleteAll(PermissionDTO.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Timber.e("Clear DB");
    }

    private void initUI() {

        spinnerLogin = (Spinner) findViewById(R.id.spinner_login);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editLogin = (EditText) findViewById(R.id.editLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    @Override
    public void onClick(View v) {
        String password = editPassword.getText().toString();
        String login = editLogin.getText().toString();

        if ("".equals(password) && "".equals(login)) {
            Toast.makeText(LoginActivity.this, getString(R.string.filed_empty), Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("============login============== " + login.toString());
            System.out.println("============password============== " + password);
            System.out.println("============spinnerLogin============== " + spinnerLogin.getSelectedItem().toString());



            preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_AUTHORIZATION, true);
            editor.apply();
            editor.commit();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            CheckAuthorizationOnServer task = new CheckAuthorizationOnServer();
//            task.execute(editLogin.getText().toString(), editPassword.getText().toString());
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.toast_logout), Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}
