package com.example.carson.celebond;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyPartyActivity extends Activity {
    ProgressBarCircularIndeterminate progressBar;
    ArrayList<HashMap<String,Object>> partyList = new ArrayList<HashMap<String,Object>>();
    private ArrayList<String> clickList = new ArrayList<String>();
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_party);

        progressBar = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);
        progressBar.setVisibility(View.VISIBLE);
        //把資料加入ArrayList中
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        query.whereEqualTo("owner", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Log.d("db", e.getMessage());
                } else {
                    for (ParseObject activity : list) {
                        HashMap<String, Object> item = new HashMap<String, Object>();
                        int i = (int) (Math.random()*8);
                        Log.d("db", String.valueOf(i));
                        switch(i){
                            case 0:
                                item.put("icon", R.drawable.icon_party_1);
                                break;
                            case 1:
                                item.put("icon", R.drawable.icon_party_2);
                                break;
                            case 2:
                                item.put("icon", R.drawable.icon_party_3);
                                break;
                            case 3:
                                item.put("icon", R.drawable.icon_party_4);
                                break;
                            case 4:
                                item.put("icon", R.drawable.icon_party_5);
                                break;
                            case 5:
                                item.put("icon", R.drawable.icon_party_6);
                                break;
                            case 6:
                                item.put("icon", R.drawable.icon_party_7);
                                break;
                            case 7:
                                item.put("icon", R.drawable.icon_party_8);
                                break;
                            case 8:
                                item.put("icon", R.drawable.icon_party_9);
                                break;
                        }
                        item.put("title", activity.getString("partyName"));
                        item.put("date", activity.getString("date"));
                        item.put("time", activity.getString("time"));

                        Log.d("db", activity.getString("partyName"));
                        clickList.add(activity.getObjectId());
                        partyList.add(item);

                    }
                    Log.d("db", String.valueOf(partyList.isEmpty()));
                    //新增SimpleAdapter
                    adapter = new SimpleAdapter(
                            MyPartyActivity.this,
                            partyList,
                            R.layout.list_view_party,
                            new String[]{"icon", "title", "date", "time"},
                            new int[]{R.id.imgv_icon, R.id.txv_title, R.id.txv_date, R.id.txv_time});
                    ListView listView = (ListView) findViewById(R.id.listView1);
                    listView.setAdapter(adapter);
                    listView.setTextFilterEnabled(true);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("db", String.valueOf(position));
                            Log.d("db", clickList.get(position));
                            Intent i = new Intent(MyPartyActivity.this, PartyManageActivity.class);
                            i.putExtra("id", clickList.get(position));
                            startActivity(i);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
