package com.example.popo5.uitest;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by popo on 2018/4/26.
 */

public class ReceiveTask extends AsyncTask<Void,Integer,String> {
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    MsgListener mMsgListener = null;
    String str = null;

    public ReceiveTask(MsgListener msgListener) {
        this.mMsgListener = msgListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            ds = new DatagramSocket(8002);
            while (true) {
                byte[] bytes = new byte[1024];
                dp = new DatagramPacket(bytes, bytes.length);
                ds.receive(dp);
                str = new String(dp.getData(), 0, dp.getLength());
                Log.d("hhh", str);
                publishProgress(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ds.close();
        }
        return str;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (values[0] == 1) {
            Log.d("hhh", "fin");
            mMsgListener.onReceiveMsg(str);
        }
    }
}

