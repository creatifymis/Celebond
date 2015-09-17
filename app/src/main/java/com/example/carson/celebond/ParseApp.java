package com.example.carson.celebond;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "ThU3lvZkV860ZHWHo7YiTlYliyXD53SzEYeS14ll", "ze4hdT0dK6FbuOaeMyBB3vRW8snRNbbQtUJgaatz");
        ParseUser.enableAutomaticUser();
        ParseACL defauAcl = new ParseACL();

        defauAcl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defauAcl, true);

    }
}
