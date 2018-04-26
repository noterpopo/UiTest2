package com.example.popo5.uitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String mip;
    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerview;
    private MsgAdapter adapter;
    private ReceiveTask receiveTask;
    private DatagramPacket dp=null;
    private DatagramSocket ds=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        mip=intent.getStringExtra("ip");
        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.send);
        msgRecyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerview.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerview.setAdapter(adapter);
        try {
            ds=new DatagramSocket(8001);
        }catch (Exception e){
            e.printStackTrace();
        }
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
                    byte[] bytes=content.getBytes();
                    try{
                        dp=new DatagramPacket(bytes,bytes.length, InetAddress.getByName(mip),8002);
                        ds.send(dp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        receiveTask=new ReceiveTask(new MsgListener() {
            @Override
            public void onReceiveMsg(String str) {
                Msg msg=new Msg(str,Msg.TYPE_RECEIVED);
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size()-1);
                msgRecyclerview.scrollToPosition(msgList.size()-1);
            }
        });
        receiveTask.execute();
    }
}
