package kr.sofac.goodtns.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.sofac.goodtns.R;
import kr.sofac.goodtns.adapter.AdapterPushList;
import kr.sofac.goodtns.dto.PushMessage;

import static kr.sofac.goodtns.Constants.APP_PREFERENCES;
import static kr.sofac.goodtns.Constants.IS_AUTHORIZATION;

public class MainActivity extends BaseActivity {

    ListView listViewPush;
    private static long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPush = (ListView) findViewById(R.id.id_push_list_view);
        ArrayList<PushMessage> pushMessages = new ArrayList<>();
        pushMessages.add(new PushMessage("Hello push 1","Message push 12321 32 2 3123 3 123","02.08.2017"));
        pushMessages.add(new PushMessage("Hello push 1","Message push 12321 32 2 3123 3 123","02.08.2017"));
        pushMessages.add(new PushMessage("Hello push 1","Message push 12321 32 2 3123 3 123","02.08.2017"));

        AdapterPushList adapterPushList = new AdapterPushList(this, pushMessages);
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
        switch (item.getItemId()){
            case R.id.id_log_out :
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
        startActivity(intentSplashActivity);
    }
}
