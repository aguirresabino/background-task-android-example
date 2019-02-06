package com.example.aguirresabino.localbroadcastexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class LocalBroadcastLoginService extends BroadcastReceiver {

    public static final String LOCAL_BROADCAST_LOGIN_SERVICE = "local.broadcast.login.service";

    private AppCompatActivity activity;

    public LocalBroadcastLoginService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Button entrar = this.activity.findViewById(R.id.entrar);
        entrar.setText("Entrar");
        entrar.setEnabled(true);

        Integer code = intent.getIntExtra("code", 404);

        if(code == 200) Toast.makeText(activity, "Usuário logado | Código" + code.toString(), Toast.LENGTH_LONG).show();
        else Toast.makeText(activity, "Usuário não encontrado | Código " + code.toString(), Toast.LENGTH_LONG).show();
    }
}
