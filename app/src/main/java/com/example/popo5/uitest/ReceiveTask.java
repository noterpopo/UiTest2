package com.example.popo5.uitest;

import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by popo on 2018/4/26.
 */

public class ReceiveTask extends AsyncTask<Void,Void,String>{
    DatagramSocket ds =null;
    DatagramPacket dp=null;
    MsgListener mMsgListener=null;

    public ReceiveTask(MsgListener msgListener) {
        this.mMsgListener = msgListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String str=null;
        try {
            ds=new DatagramSocket(8002);
            while (true){
                byte[] bytes=new byte[1024];
                dp=new DatagramPacket(bytes,bytes.length);
                ds.receive(dp);
                str=new String(dp.getData(),0,dp.getLength());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ds.close();
        }
        return str;
    }

    @Override
    protected void onPostExecute(String str) {
        mMsgListener.onReceiveMsg(str);
    }
}
