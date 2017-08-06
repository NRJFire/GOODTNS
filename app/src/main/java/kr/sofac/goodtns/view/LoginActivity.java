package kr.sofac.goodtns.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.R;
import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.server.Server;
import kr.sofac.goodtns.server.retrofit.ManagerRetrofit;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static kr.sofac.goodtns.Constants.APP_PREFERENCES;
import static kr.sofac.goodtns.Constants.IS_AUTHORIZATION;
import static kr.sofac.goodtns.Constants.SERVER_RESPONSE_ERROR;

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
        String password = md5Custom(md5Custom(editPassword.getText().toString()));
        String login = editLogin.getText().toString();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO(login, password, getGoogleKey());

        if ("".equals(password) && "".equals(login)) {
            Toast.makeText(LoginActivity.this, getString(R.string.filed_empty), Toast.LENGTH_SHORT).show();
        } else {
            if (spinnerLogin.getSelectedItem().toString().equals("Client")) {
                if (new Server().authorizationUser(authorizationDTO)) {
                    startMainActivity();
                } else {
                    Toast.makeText(this, "Error data!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (new Server().authorizationManager(authorizationDTO)) {
                    startMainActivity();
                } else {
                    Toast.makeText(this, "Error data!", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    public String getGoogleKey(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        return sharedPref.getString(Constants.GOOGLE_CLOUD_PREFERENCE, "");
    }


    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    public void startMainActivity() {
        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_AUTHORIZATION, true);
        editor.apply();
        editor.commit();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
