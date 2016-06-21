package com.socket.net.voicecommand;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/**
 * Created by bhegde on 20-06-2016.
 */
public class AsynchConnectionTask extends AsyncTask<String, String, TCPClient>{

    private static String COMMAND = "hello server ";
    private TCPClient  tcpClient;
    private Handler mHandler;
    private static final String TAG = "AsynchConnectionTask";

    public AsynchConnectionTask(Handler handler, String command){
        this.COMMAND = command;
        this.mHandler = handler;
    }

    @Override
    protected void onPreExecute()
    {

        super.onPreExecute();
    }

    @Override
    protected TCPClient doInBackground(String... strings) {
        Log.d(TAG, "In do in background");

        try {
//            MessageCallback msgCallBack = new MessageCallback() {
//                @Override
//                public void callbackMessageReceiver(String message) {
//                    publishProgress(message);
//                }
//            };
          //  tcpClient = new TCPClient(mHandler,COMMAND,"159.203.161.191",msgCallBack);
            tcpClient = new TCPClient(mHandler,COMMAND,"159.203.161.191");

        }
        catch(Exception e){
            System.out.println("Exception : AsynchConnectionTask : "+e.getMessage());
        }
        tcpClient.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
//        Log.d(TAG, "In progress update, values: " + values.toString());
//        if(values[0].equals("shutdown")){
//            tcpClient.sendMessage(COMMAND);
//            tcpClient.stopClient();
//            mHandler.sendEmptyMessageDelayed(Main2Activity.SHUTDOWN, 2000);
//
//        }else{
//            tcpClient.sendMessage("wrong");
//            mHandler.sendEmptyMessageDelayed(Main2Activity.ERROR, 2000);
//            tcpClient.stopClient();
//        }
    }

    @Override
    protected void onPostExecute(TCPClient result){
        super.onPostExecute(result);
//        Log.d(TAG, "In on post execute");
//        if(result != null && result.isRunning()){
//            result.stopClient();
//        }
       // result.stopClient();
      //  mHandler.sendEmptyMessageDelayed(Main2Activity.SENT, 4000);

    }
}
