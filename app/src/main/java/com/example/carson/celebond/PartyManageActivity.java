package com.example.carson.celebond;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class PartyManageActivity extends Activity {
    ParseObject activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manage);

        Intent i = getIntent();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        query.whereEqualTo("objectId", i.getStringExtra("id"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null){
                    Log.d("db", e.getMessage());
                }else{
                    for (ParseObject act :list){
                        activity = act;
                    }

                    ParseFile parseFile = activity.getParseFile("picFull");
                    if (parseFile == null) {
                        Log.d("db", "null");
                    } else {
                        Log.d("db", "YES");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    ImageView imgv = (ImageView)findViewById(R.id.imgv_picFull);
                                    imgv.setImageBitmap(bitmap);
                                } else {
                                    Log.d("db", e.getMessage());
                                }
                            }
                        });
                    }
                    JSONArray jsonArray = activity.getJSONArray("joinUser");
                    TextView txv = (TextView)findViewById(R.id.txv_join);
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String name = jsonArray.getString(i);
                            Log.d("db", name);
                            txv.append(name + "\n");
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

}
