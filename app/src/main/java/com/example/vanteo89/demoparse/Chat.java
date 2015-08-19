package com.example.vanteo89.demoparse;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vanteo89.custom.CustomActivity;
import com.example.vanteo89.model.Conversation;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vanteo89 on 14/08/2015.
 */
public class Chat extends CustomActivity {
    public static final String TAG=Chat.class.getSimpleName();
    private ArrayList<Conversation> consList;
    private ChatAdapter mChatAdapter;

    private EditText edtCompose;
    private String buddy;
    private Date lastMsgDate;
    private boolean isRunnning;
    private static Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        consList = new ArrayList<Conversation>();
        ListView list = (ListView) findViewById(R.id.list_item);
        mChatAdapter = new ChatAdapter();
        list.setAdapter(mChatAdapter);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);

        edtCompose = (EditText) findViewById(R.id.edt_send_text);
        edtCompose.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        setTouchNClick(R.id.btn_send);
        buddy = getIntent().getStringExtra("EXTRA_DATA");
      //  getActionBar().setTitle(buddy);
        getSupportActionBar().setTitle(buddy);
        handler = new Handler();


    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunnning = true;
        loadConversationList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunnning = false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_send) {
            sendMessage();
        }
    }
/*Call this method to send message to opponent . It does nothing if the text is empty otherwise it creates
 a Parse Object for chat Message and send it to parse Server*/
    private void sendMessage() {
        if (edtCompose.length() == 0) {
            return;
        }
        InputMethodManager imn= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(edtCompose.getWindowToken(),0);

        String s=edtCompose.getText().toString();
        final Conversation c=new Conversation(s,new Date(),UserList.parseUser.getUsername());
        c.setStatus(Conversation.STATUS_SENDING);
        consList.add(c);
        mChatAdapter.notifyDataSetChanged();
        edtCompose.setText(null);

        ParseObject po=new ParseObject("Chat");
        po.put("sender",UserList.parseUser.getUsername());
        po.put("receiver",buddy);
        po.put("message",s);
        po.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    c.setStatus(Conversation.STATUS_SENT);
                }else {
                    c.setStatus(Conversation.STATUS_FAILED);
                }
                mChatAdapter.notifyDataSetChanged();
            }
        });

    }

    private void loadConversationList() {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");

        if (consList.size()==0) {
            //load all message...
            ArrayList<String> al = new ArrayList<String>();
            al.add(buddy);
            al.add(UserList.parseUser.getUsername());
            q.whereContainedIn("sender", al);
            q.whereContainedIn("receiver", al);

        } else {
            if (lastMsgDate != null) {
                q.whereGreaterThan("createdAt", lastMsgDate);
            }
            q.whereEqualTo("sender", buddy);
            q.whereEqualTo("receiver", UserList.parseUser.getUsername());


        }
        q.orderByDescending("createdAt");
        q.setLimit(30);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list != null && list.size() > 0) {
                    for (int i = list.size() - 1; i >= 0; i--) {
                        ParseObject po = list.get(i);
                        Conversation c = new Conversation(po.getString("message"), po.getCreatedAt(), po.getString("sender"));
                        consList.add(c);
                        if (lastMsgDate == null || lastMsgDate.before(c.getDate())) {
                            lastMsgDate = c.getDate();
                            //TODO

                        }
                        mChatAdapter.notifyDataSetChanged();
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRunnning) {
                            loadConversationList();
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ChatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return consList.size();
        }

        @Override
        public Conversation getItem(int position) {
            return consList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Conversation c = consList.get(position);
            if (c.isSent()) {
                convertView = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
            } else {
                convertView = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

            }
            TextView lbl = (TextView) convertView.findViewById(R.id.tv1);

            lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, c.getDate().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0));
            lbl= (TextView) convertView.findViewById(R.id.tv2);
            lbl.setText(c.getMsg());
            lbl= (TextView) convertView.findViewById(R.id.tv3);
            if (c.isSent()){
                if(c.getStatus()==Conversation.STATUS_SENT){
                    lbl.setText("Delivered");
                }else if(c.getStatus()==Conversation.STATUS_SENDING){
                    lbl.setText("Sending...");
                }else {
                    lbl.setText("Failed");
                }
            }else{
                lbl.setText("");
            }

            return convertView;
        }
    }
}
