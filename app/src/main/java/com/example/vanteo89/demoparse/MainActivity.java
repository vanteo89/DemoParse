package com.example.vanteo89.demoparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vanteo89.custom.CustomActivity;
import com.example.vanteo89.utils.AlertDialogFragment;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends CustomActivity {
 private EditText edtUserName,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.login);
       // setTouchNClick(R.id.btnRegister);
        setTouchNClick(R.id.btnLogin);
        edtPassword= (EditText) findViewById(R.id.edt_password);
        edtUserName = (EditText) findViewById(R.id.edt_username);

        /*ParseObject testObject = new ParseObject("TestObject");
        testObject.put("levan", "hoi");

        testObject.saveInBackground();//no arg*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.btnRegister){
            startActivityForResult(new Intent(this,Register.class),10);

        }else {
            String username=edtUserName.getText().toString();
            String pass = edtPassword.getText().toString();
            if(username.length()==0 || pass.length()==0){
                alertDialog();
                return;
            }
            final ProgressDialog progressDialog=ProgressDialog.show(this, null, getString(R.string.alert_wait));
            ParseUser.logInInBackground(username, pass, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    progressDialog.dismiss();
                    if(parseUser!=null){
                        UserList.parseUser=parseUser;
                        startActivity(new Intent(MainActivity.this,UserList.class));
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this,"Error orrcured Login ",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }

    private void alertDialog() {
        AlertDialogFragment dialogFragment=new AlertDialogFragment();
        dialogFragment.show(getFragmentManager(),"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK){
            finish();
        }
    }
}
