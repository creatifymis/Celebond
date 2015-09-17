package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.gc.materialdesign.views.Card;
import com.parse.FindCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }else{

            ParseUser currentUser = ParseUser.getCurrentUser();

            if (currentUser != null){
                Log.d("db","Has Logined with " + currentUser.getUsername());
                Toast.makeText(MainActivity.this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();

            }else{

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }

        }
    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_create){
            Intent i = new Intent(this, CategoryActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btn_cardBox){
            Intent i = new Intent(this, CardBoxActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btn_logout){
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        if (v.getId() == R.id.btn_search){
            EditText tf_search = (EditText)findViewById(R.id.tf_search);
            String str = tf_search.getText().toString();
            str = str.trim();
            if(str.length() != 4){
                Toast.makeText(MainActivity.this,"Wrong code!",Toast.LENGTH_LONG).show();
            }else{
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
                query.whereEqualTo("partyCode", str);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null || list.isEmpty()) {
                            Toast.makeText(MainActivity.this, "查無此活動!", Toast.LENGTH_LONG).show();
                            Log.d("db","none");
                            return;
                        } else {
                            Log.d("db","yes");
                            EditText tf_search = (EditText)findViewById(R.id.tf_search);
                            tf_search.setText(null);
                            Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                            String str = list.get(0).getString("partyCode");
                            i.putExtra("partyCode", str);
                            startActivity(i);
                        }
                    }
                });
            }
        }
        if (v.getId() == R.id.btn_myParty){
            Intent i = new Intent(this, MyPartyActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.btn_attended){
            Intent i = new Intent(this, AttendedActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
