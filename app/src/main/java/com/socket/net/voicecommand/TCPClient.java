package com.socket.net.voicecommand;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by bhegde on 20-06-2016.
 */
public class TCPClient {
    private static final int PORT = 12348;
    private static final String TAG = "TCPClient";
    private final Handler mHandler;
    private String ipNumber, incomingMessage, command;
    BufferedReader in;
    PrintWriter out;
    private MessageCallback listener = null;
    private boolean mRun = false;

    public TCPClient(Handler mHandler, String command, String ipNumber, MessageCallback listener) {
        this.listener         = listener;
        this.ipNumber         = ipNumber;
        this.command          = command ;
        this.mHandler         = mHandler;
    }

    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
            mHandler.sendEmptyMessageDelayed(Main2Activity.SENDING, 1000);
            Log.d(TAG, "Sent Message: " + message);

        }
    }

    public void stopClient(){
        Log.d(TAG, "Client stopped!");
        mRun = false;
    }

    public boolean isRunning(){
        return mRun;
    }

    public void run() {

        mRun = true;

        try {
            // Creating InetAddress object from ipNumber passed via constructor from IpGetter class.
            InetAddress serverAddress = InetAddress.getByName(ipNumber);

            Log.d(TAG, "Connecting...");

            mHandler.sendEmptyMessageDelayed(Main2Activity.CONNECTING,1000);

            Socket socket = new Socket(serverAddress, PORT);

            try {

                // Create PrintWriter object for sending messages to server.
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //Create BufferedReader object for receiving messages from server.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Log.d(TAG, "In/Out created");

                this.sendMessage(command);

                mHandler.sendEmptyMessageDelayed(Main2Activity.SENDING,2000);

                //Listen for the incoming messages while mRun = true
                while (mRun) {
                    incomingMessage = in.readLine();
                    if (incomingMessage != null && listener != null && !"\n".equals(incomingMessage )) {

                        listener.callbackMessageReceiver(incomingMessage);

                        System.out.println("Server reply : "+incomingMessage);

                    }
                    incomingMessage = null;

                }

                Log.d(TAG, "Received Message: " +incomingMessage);

            } catch (Exception e) {

                Log.d(TAG, "Error", e);
                mHandler.sendEmptyMessageDelayed(Main2Activity.ERROR, 2000);

            } finally {

                out.flush();
                out.close();
                in.close();
                socket.close();
                mHandler.sendEmptyMessageDelayed(Main2Activity.SENT, 3000);
                Log.d(TAG, "Socket Closed");
            }

        } catch (Exception e) {

            Log.d(TAG, "Error", e);
            mHandler.sendEmptyMessageDelayed(Main2Activity.ERROR, 2000);

        }

    }
}
