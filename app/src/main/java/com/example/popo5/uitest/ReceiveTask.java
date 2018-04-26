package com.example.popo5.uitest;

import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by popo on 2018/4/26.
 */

public class ReceiveTask extends AsyncTask<Void,Void,Void>{
    DatagramSocket ds =null;
    DatagramPacket dp=null;
    MsgListener mMsgListener=null;

    public ReceiveTask(MsgListener msgListener) {
        this.mMsgListener = msgListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ds=new DatagramSocket(8002);
            while (true){
                byte[] bytes=new byte[1024];
                dp=new DatagramPacket(bytes,bytes.length);
                ds.receive(dp);
                String str=new String(dp.getData(),0,dp.getLength());
                mMsgListener.onReceiveMsg(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ds.close();
        }
        return null;
    }
}
