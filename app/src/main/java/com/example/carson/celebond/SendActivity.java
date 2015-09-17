package com.example.carson.celebond;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class SendActivity extends Activity {
    Bitmap picSend ;
    String partyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent i = this.getIntent();
        partyCode = i.getStringExtra("partyCode");
        // Get Bitmap From Parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Activity");
        query.whereEqualTo("partyCode", partyCode);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    for (ParseObject activity : list) {
                        Log.d("db", activity.getString("partyName"));
                        ParseFile parseFile = activity.getParseFile("picSend");
                        if (parseFile == null) {
                            Log.d("db", "null");
                        } else {
                            Log.d("db", "YES");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        ImageView imgv = (ImageView) findViewById(R.id.imgv_finalSmall);
                                        imgv.setImageBitmap(bitmap);
                                        picSend = bitmap;
                                    } else {
                                        Log.d("db", e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.d("db", e.getMessage());
                }
            }
        });
        //-----------------------------------------
    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_share){
            // Create the new Intent using the 'Send' action.
            Intent share = new Intent(Intent.ACTION_SEND);
            // Set the MIME type
            share.setType("image/png");
            // Create the URI from the media
            //File media = new File();
            //Uri uri = Uri.parse("android.resource://com.example.carson.celebond/" + R.drawable.thebirthofrobert);
            Uri uri = getImageUri(this, picSend);
            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri);

            // Broadcast the Intent.
            startActivity(Intent.createChooser(share, "Share to"));
        }
        if (v.getId() == R.id.btn_finish){
            Intent i = new Intent(this, MainActivity.class);
            this.finish();
            //EditCardActivity.thisActivity.finish();

        }
    }
    public Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
        Log.d("db",path);
        return Uri.parse(path);
    }
}
