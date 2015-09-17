package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditCardActivity extends Activity {
    public static Activity thisActivity;

    private EditText tf_partyName;
    private EditText tf_partyContent;
    private EditText tf_date;
    private EditText tf_time;
    private EditText tf_place;
    private EditText tf_entrepreneur;
    private EditText tf_phone;
    private EditText tf_remark;

    private String partyName;
    private String partyContent;
    private String date;
    private String time;
    private String place;
    private String entrepreneur;
    private String phone;
    private String remark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_next){

            tf_partyName = (EditText)findViewById(R.id.tf_partyName);
            tf_partyContent = (EditText)findViewById(R.id.tf_partyContent);
            tf_date = (EditText)findViewById(R.id.tf_date);
            tf_time = (EditText)findViewById(R.id.tf_time);
            tf_place = (EditText)findViewById(R.id.tf_place);
            tf_entrepreneur = (EditText)findViewById(R.id.tf_entrepreneur);
            tf_phone = (EditText)findViewById(R.id.tf_phone);
            tf_remark = (EditText)findViewById(R.id.tf_remark);

            partyName = tf_partyName.getText().toString();
            partyContent = tf_partyContent.getText().toString();
            date = tf_date.getText().toString();
            time = tf_time.getText().toString();
            place = tf_place.getText().toString();
            entrepreneur = tf_entrepreneur.getText().toString();
            phone = tf_phone.getText().toString();
            remark = tf_remark.getText().toString();

            Intent i = new Intent(EditCardActivity.this, ConfirmActivity.class);
            i.putExtra("partyName",partyName);
            i.putExtra("partyContent", partyContent);
            i.putExtra("date",date);
            i.putExtra("time",time);
            i.putExtra("place",place);
            i.putExtra("entrepreneur",entrepreneur);
            i.putExtra("phone",phone);
            i.putExtra("remark",remark);

            startActivity(i);
        }
    }

}
