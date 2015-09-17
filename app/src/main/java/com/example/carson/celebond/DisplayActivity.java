package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.List;


public class DisplayActivity extends Activity {
    String partyCode;
    ParseObject activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent i = this.getIntent();
        partyCode = i.getStringExtra("partyCode");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        query.whereEqualTo("partyCode",partyCode);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null){
                    Log.d("db",e.getMessage());
                }else{
                    for(ParseObject act: list){
                        activity = act;
                        ParseFile parseFile = act.getParseFile("picFull");
                        if (parseFile == null){
                            Log.d("db","null picFull");
                        }else{
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        ImageView imgv_display = (ImageView)findViewById(R.id.imgv_display);
                                        imgv_display.setImageBitmap(bitmap);
                                    } else {
                                        Log.d("db", e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_yes){
            Toast.makeText(this, "Select yes", Toast.LENGTH_SHORT);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_chooseBtn);
            linearLayout.setVisibility(View.GONE);
            JSONArray jsonArray = activity.getJSONArray("joinUser");
            jsonArray.put(ParseUser.getCurrentUser().getUsername());
            activity.put("joinUser", jsonArray);
            activity.saveInBackground();
            JSONArray jsonArray1 = ParseUser.getCurrentUser().getJSONArray("attended");
            jsonArray1.put(activity.getObjectId());
            ParseUser.getCurrentUser().put("attended",jsonArray1);

        }
        if (v.getId() == R.id.btn_maybe){
            Toast.makeText(this,"Select maybe", Toast.LENGTH_SHORT).show();
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_chooseBtn);
            linearLayout.setVisibility(View.GONE);
        }
        if (v.getId() == R.id.btn_no){
            Toast.makeText(this,"Select no", Toast.LENGTH_SHORT).show();
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_chooseBtn);
            linearLayout.setVisibility(View.GONE);
        }

    }


}
