package com.example.aguirresabino.localbroadcastexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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

        //Requisição com json
//        MediaType mediaType = MediaType.parse("application/json");
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("email", this.email);
//        jsonObject.addProperty("password", this.senha);
//
//        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        //Requisição com FormBody
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", senha)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {

            //Enviando mensagem em broadcast
            LocalBroadcastManager.getInstance(LoginService.this)
                    .sendBroadcast(
                            new Intent(LocalBroadcastLoginService.LOCAL_BROADCAST_LOGIN_SERVICE)
                            .putExtra("code", client.newCall(request).execute().code())
                    );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}