package kr.sofac.goodtns.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.R;
import kr.sofac.goodtns.adapter.AdapterPushList;
import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.dto.ManagerDTO;
import kr.sofac.goodtns.dto.PushMessage;
import kr.sofac.goodtns.server.Server;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.orm.SugarRecord.listAll;
import static kr.sofac.goodtns.Constants.APP_PREFERENCES;
import static kr.sofac.goodtns.Constants.GOOGLE_CLOUD_PREFERENCE;
import static kr.sofac.goodtns.Constants.IS_AUTHORIZATION;

public class MainActivity extends BaseActivity {

    ListView listViewPush;
    private static long backPressed;
    private ArrayList<PushMessage> pushMessages;
    private AdapterPushList adapterPushList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPush = (ListView) findViewById(R.id.id_push_list_view);

        updateViewList();
    }

    @Override
    protected void onResume() {
        updateViewList();
        super.onResume();
    }

    protected void updateViewList() {
        pushMessages = (ArrayList<PushMessage>) listAll(PushMessage.class);
        adapterPushList = new AdapterPushList(this, pushMessages);
        listViewPush.setAdapter(adapterPushList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_log_out:
                logOutApp();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOutApp() {
        Intent intentSplashActivity = new Intent(this, SplashActivity.class);
        SharedPreferences preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_AUTHORIZATION, false);
        editor.apply();
        intentSplashActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        editor.commit();
        PushMessage.deleteAll(PushMessage.class);
        startActivity(intentSplashActivity);
    }
}
