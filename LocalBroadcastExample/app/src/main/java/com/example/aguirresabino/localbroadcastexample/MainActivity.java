package com.example.aguirresabino.localbroadcastexample;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button entrar;
    private EditText email, senha;

    private LocalBroadcastLoginService localBroadcastLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.entrar = findViewById(R.id.entrar);
        this.email = findViewById(R.id.email);
        this.senha = findViewById(R.id.senha);

        entrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entrar.setText("Aguardando...");
                        entrar.setEnabled(false);
                        Intent intent = new Intent(MainActivity.this, LoginService.class);
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("senha", senha.getText().toString());
                        startService(intent);
                    }
                }
        );

        localBroadcastLoginService = new LocalBroadcastLoginService(this);
        LocalBroadcastManager.getInstance(MainActivity.this)
                .registerReceiver(localBroadcastLoginService, new IntentFilter(LocalBroadcastLoginService.LOCAL_BROADCAST_LOGIN_SERVICE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(MainActivity.this)
                .unregisterReceiver(localBroadcastLoginService);
    }
}
