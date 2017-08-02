package kr.sofac.goodtns.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        Spinner spinnerLogin = (Spinner) findViewById(R.id.spinner_login);

        ArrayList<String> stringsSpinnerLanguage = new ArrayList<>();
        stringsSpinnerLanguage.add("Client");
        stringsSpinnerLanguage.add("Staff");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringsSpinnerLanguage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLogin.setAdapter(adapter);

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
