package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ChooseBirthdayThemeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_birthday_theme);
    }
    public void onImageClick(View v){
        if(v.getId() == R.id.imgv_ThemeBir1) {
            Intent i = new Intent(ChooseBirthdayThemeActivity.this, EditCardActivity.class);
            startActivity(i);
        }
    }

}
