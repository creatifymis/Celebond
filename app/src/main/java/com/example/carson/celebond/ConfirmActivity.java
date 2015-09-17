package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ConfirmActivity extends Activity {
    Intent intent;
    Bitmap picSend;
    Bitmap picFull;
    ProgressBarCircularIndeterminate progressBar;
    Button btn_confirm;
    Button btn_cancel;

    private String partyName;
    private String partyContent;
    private String date;
    private String time;
    private String place;
    private String entrepreneur;
    private String phone;
    private String remark;
    private String partyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        intent = this.getIntent();
        partyName = intent.getStringExtra("partyName");
        partyContent = intent.getStringExtra("partyContent");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        place = intent.getStringExtra("place");
        entrepreneur = intent.getStringExtra("entrepreneur");
        phone = intent.getStringExtra("phone");
        remark = intent.getStringExtra("remark");

        partyCode = getPartyCode();

        ImageView imageView = (ImageView)findViewById(R.id.imageView1);
        picFull = getBitmapFull();
        imageView.setImageBitmap(picFull);

        progressBar = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);


    }
    public Bitmap getBitmapFull(){
        Bitmap bitmap ;
        Bitmap background;
        background = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg_small);
        Bitmap.Config bitmapConfig = background.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null){
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }

        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = background.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialiased Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(255, 255, 255));
        // text size in pixels
        paint.setTextSize(150);
        // text shadow
        paint.setShadowLayer(10f, 0f, 1f, Color.BLACK);

        paint.setStrokeWidth(10);


        // draw text to the Canvas center

        Rect bounds1 = new Rect();
        Rect bounds2 = new Rect();
        Rect bounds3 = new Rect();


        paint.getTextBounds(partyName, 0, partyName.length(), bounds1);
        paint.getTextBounds(partyContent, 0, partyContent.length(), bounds2);
        paint.getTextBounds(date, 0, date.length(), bounds3);

        int x1 = (background.getWidth() - bounds1.width())/2;
        int x2 = (background.getWidth() - bounds2.width())/2;
        int x3 = (background.getWidth() - bounds3.width())/2;

        int y1 = (background.getHeight() + bounds1.height())/7;
        int y2 = (background.getHeight() + bounds2.height()*2)/3;
        int y3 = (background.getHeight() + bounds3.height()*4)/2;


        canvas.drawText(partyName, x1, y1, paint);
        canvas.drawText(partyContent, x2, y2, paint);
        canvas.drawText(date, x3, y3, paint);


        return bitmap;
    }
    public Bitmap getBitmapSend(){
        Bitmap bitmap ;
        Bitmap background;
        background = BitmapFactory.decodeResource(this.getResources(), R.drawable.robert_cakeplanet);
        Bitmap.Config bitmapConfig = background.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null){
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }

        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = background.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialiased Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(73, 73, 73));
        // text size in pixels
        paint.setTextSize(100);
        // text shadow
        paint.setShadowLayer(0f, 0f, 0f, Color.BLACK);

        paint.setStrokeWidth(10);

        // Code Paint
// new antialiased Paint
        Paint paintCode = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paintCode.setColor(Color.rgb(255, 255, 255));
        // text size in pixels
        paintCode.setTextSize(80);
        // text shadow
        paintCode.setShadowLayer(0f, 0f, 0f, Color.BLACK);

        paintCode.setStrokeWidth(10);
        // draw text to the Canvas center

        Rect bounds1 = new Rect();
        Rect bounds2 = new Rect();
        Rect bounds3 = new Rect();
        Rect bounds4 = new Rect();

        paint.getTextBounds(partyName, 0, partyName.length(), bounds1);
        paint.getTextBounds(partyContent, 0, partyContent.length(), bounds2);
        paint.getTextBounds(date, 0, date.length(), bounds3);
        paint.getTextBounds(partyCode, 0, partyCode.length(), bounds4);

        int x1 = (background.getWidth() - bounds1.width())/2;
        int x2 = (background.getWidth() - bounds2.width())/2;
        int x3 = (background.getWidth() - bounds3.width())/2;
        int x4 = 1280;

        int y1 = 500;
        int y2 = 900;
        int y3 = 1300;
        int y4 = 1920;


        canvas.drawText(partyName, x1, y1, paint);
        canvas.drawText(partyContent, x2, y2, paint);
        canvas.drawText(date, x3, y3, paint);
        canvas.drawText(partyCode, x4, y4, paintCode);



        return bitmap;
    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_confirm){
            btn_confirm.setEnabled(false);
            btn_cancel.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            ParseObject activity = new ParseObject("Activity");
            activity.put("partyCode", partyCode);
            activity.put("partyName",partyName);
            activity.put("partyContent", partyContent);
            activity.put("date", date);
            activity.put("time", time);
            activity.put("place", place);
            activity.put("entrepreneur", entrepreneur);
            activity.put("phone", phone);
            activity.put("remark", remark);
            activity.put("owner", ParseUser.getCurrentUser());
            picSend = getBitmapSend();
            activity.put("picSend", bmpToParseFile("picSend.png", picSend));
            activity.put("picFull", bmpToParseFile("picFull.png", picFull));
            JSONArray joinUser = new JSONArray();
            activity.put("joinUser",joinUser);



            activity.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ConfirmActivity.this, "Success", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(ConfirmActivity.this, SendActivity.class);
                        i.putExtra("partyCode", partyCode);

                        startActivity(i);
                        ConfirmActivity.this.finish();
                    }else{
                        btn_confirm.setEnabled(true);
                        btn_cancel.setEnabled(true);
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(ConfirmActivity.this, "Error", Toast.LENGTH_LONG).show();
                        Log.d("db",e.getMessage());
                    }
                }
            });

        }
        if (v.getId() == R.id.btn_cancel){

           this.finish();
        }
    }
    public ParseFile bmpToParseFile(String name, Bitmap bmp){
        byte[] byteArray;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byteArray = outputStream.toByteArray();

        ParseFile file = new ParseFile(name, byteArray);
        file.saveInBackground();

        return file;
    }

    public String getPartyCode(){
        // Only Number
        String code = "";
        final int LENGTH = 4;
        while (code.length() < LENGTH){
            int num = (int)(Math.random()*10);
            code += String.valueOf(num);
        }
        return code;
        // Number & Letter
        /*String code = "";
        final int LENGTH = 4;
        while (code.length() < LENGTH){
            int num = (int)(Math.random()*(90-50+1))+50;

            if (num > 57 && num < 65){//排除非數字
                continue;
            }
            else if (num == 73 || num == 76 || num == 79){//排除I、L、O
                continue;
            }
            else{
                code += (char)num;
            }
        }

        return code.toLowerCase();*/
    }



}
