package com.example.carson.celebond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONArray;


public class LoginActivity extends Activity {

    //Facebook Login
    LoginButton loginButton;
    CallbackManager callbackManager;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Facebook Initialize
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Facebook Login
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        // App code
                        Profile profile = Profile.getCurrentProfile();
                        Log.d("FB", profile.getName());

                        // Register to Parse
                        String username = profile.getName();
                        String password = profile.getId();
                        RegisterToDatabase(username, password);

                        //
                        new GraphRequest(
                                accessToken.getCurrentAccessToken(),
                                "/me/friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {

                                    }
                                }
                        ).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("FB", "Facebook Login cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("FB", "Facebook Login fail");
                    }
                });
    }

    public void RegisterToDatabase(final String name, final String pass){
        // Register to Database

        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(pass);
        user.put("attended",new JSONArray());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("db", "Successed");
                    loginToParse(name, pass);
                } else {
                    Log.d("db", "Failed" + e.getMessage());
                    loginToParse(name, pass);
                }
            }
        });
        //---------------------------



    }
    public void loginToParse(String name, String pass){
        // Login to Parse
        ParseUser.logInInBackground(name, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Log.d("db", "Login Successed");
                    Toast.makeText(LoginActivity.this, "Login Successed!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    LoginActivity.this.finish();

                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Log.d("db", "Login Failed");
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onButtonClick(View v) {
        EditText edt_acc = (EditText) findViewById(R.id.edt_acc);
        EditText edt_pass = (EditText) findViewById(R.id.edt_pass);
        if (v.getId() == R.id.btn_login) {
            String username = edt_acc.getText().toString();
            String password = edt_pass.getText().toString();

            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null ){

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{

                        Toast.makeText(LoginActivity.this, "Wrong username or password!",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        if (v.getId() == R.id.btn_signUp) {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
            finish();
        }
    }
}
