package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends Activity {

    private boolean parseIsInitialize = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void onButtonClick(View v){
        if (v.getId() == R.id.btn_LOGIN){
            EditText tf_username = (EditText)findViewById(R.id.tf_username);
            EditText tf_password = (EditText)findViewById(R.id.tf_password);
            EditText tf_cPassword = (EditText)findViewById(R.id.tf_cPassword);

            String str_username = tf_username.getText().toString();
            String str_password = tf_password.getText().toString();
            String str_cPassword = tf_cPassword.getText().toString();

            // Trim
            str_username = str_username.trim();
            str_password = str_password.trim();
            str_cPassword = str_cPassword.trim();

            // Check data
            if (!str_password.equals(str_cPassword)){
                Toast t_wrongCPassword = Toast.makeText(this, "Wrong Password!", Toast.LENGTH_LONG);
                t_wrongCPassword.show();
            }else {
                // Register to Parse
                ParseUser user = new ParseUser();
                user.setUsername(str_username);
                user.setPassword(str_password);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("db", "Successed");
                            Toast.makeText(getApplicationContext(), "Sign up Success", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.d("db", "Failed" + "  " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Sign up Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
}
