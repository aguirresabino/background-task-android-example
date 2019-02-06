package com.example.aguirresabino.taskbackgroundexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService extends Service implements Runnable {

    private String email, senha;

    private final String urlBase = "http://ag-ifpb-sgd-server.herokuapp.com/login";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.email = intent.getStringExtra("email");
        this.senha = intent.getStringExtra("senha");

        new Thread(this).start();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void run() {
        this.logar();
    }

    public void logar() {
        final String url = urlBase;

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", senha)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Integer code = client.newCall(request).execute().code();

            Message message = new Message();
            message.obj = code;
            MainActivity.handler.sendMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
