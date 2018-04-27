package com.example.popo5.uitest;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by popo on 2018/4/26.
 */

public class SendTask extends AsyncTask<Void,Void,Void> {
    String content=null;
    DatagramSocket ds=null;
    DatagramPacket dp=null;
    SendListener sendListener=null;
    String ip;

    public SendTask(String ip,String content, DatagramSocket ds, DatagramPacket dp,SendListener sendListener) {
        this.content = content;
        this.ds = ds;
        this.dp = dp;
        this.sendListener=sendListener;
        this.ip=ip;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            byte[] bytes=content.getBytes();
            dp=new DatagramPacket(bytes,bytes.length, InetAddress.getByName(ip),8002);
            ds.send(dp);
        }catch (Exception e){
            Log.d("hhh",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        sendListener.onSend(content);
    }
}
