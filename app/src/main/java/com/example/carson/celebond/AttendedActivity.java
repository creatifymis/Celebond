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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class AttendedActivity extends Activity {
    ProgressBarCircularIndeterminate progressBar;
    ArrayList<HashMap<String,Object>> partyList = new ArrayList<HashMap<String,Object>>();
    private ArrayList<String> clickList = new ArrayList<String>();
    private SimpleAdapter adapter;
    JSONArray jsArray;
    JSONArray jsArray_temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended);

        progressBar = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);
        progressBar.setVisibility(View.VISIBLE);

        jsArray = new JSONArray();
        jsArray_temp = ParseUser.getCurrentUser().getJSONArray("attended");
        Log.d("db", String.valueOf(jsArray_temp.length()));
        //新增資料至ArrayList
        while(jsArray_temp.length() != 0){
            Log.d("db", "11");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
            try {
                query.whereEqualTo("objectId",jsArray_temp.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                List<ParseObject> list = query.find();
                if (list.isEmpty()){
                    Log.d("db", "delete " + jsArray_temp.getString(0));
                    jsArray_temp.remove(0);

                }else{
                    Log.d("db","yes");
                    for (ParseObject activity : list) {
                        HashMap<String, Object> item = new HashMap<String, Object>();
                        item.put("icon", R.drawable.img1);
                        item.put("title", activity.getString("partyName"));
                        item.put("date", activity.getString("date"));
                        item.put("time", activity.getString("time"));

                        Log.d("db", activity.getString("partyName"));
                        clickList.add(activity.getObjectId());
                        partyList.add(item);
                    }
                    try {
                        jsArray.put(jsArray_temp.get(0));
                        jsArray_temp.remove(0);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ParseUser.getCurrentUser().put("attended", jsArray);
        //建立SimpleAdapter
        adapter = new SimpleAdapter(
                AttendedActivity.this,
                partyList,
                R.layout.list_view_party,
                new String[] { "icon", "title","date", "time" },
                new int[]{R.id.imgv_icon, R.id.txv_title, R.id.txv_date, R.id.txv_time});
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listView.setTextFilterEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

}
