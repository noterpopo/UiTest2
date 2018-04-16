package com.example.popo5.uitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerview;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniMsgs();
        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.send);
        msgRecyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerview.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerview.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String content=inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerview.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });
        }
    private void iniMsgs(){
        Msg msg1=new Msg("Hello guys",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("Hello",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3=new Msg("Wonderful",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
