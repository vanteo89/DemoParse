package com.example.vanteo89.demoparse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vanteo89.custom.CustomActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by vanteo89 on 17/08/2015.
 */
public class Register extends CustomActivity {
    private EditText user;

    private EditText pwd;
    private EditText email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTouchNClick(R.id.btnRegister);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String u = user.getText().toString();
        String p = pwd.getText().toString();
        String e = email.getText().toString();
        if (u.length() == 0 || p.length() == 0 || e.length() == 0) {
            Toast.makeText(Register.this, "Please fill full the fields", Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.alert_wait));
        final ParseUser pu = new ParseUser();
        pu.setUsername(u);
        pu.setEmail(e);
        pu.setPassword(p);
        pu.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                progressDialog.dismiss();
                if (e == null) {
                    UserList.parseUser = pu;
                    startActivity(new Intent(Register.this, UserList.class));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(Register.this, "Error occurred while SignUp", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }
}
