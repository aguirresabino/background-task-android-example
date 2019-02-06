package com.example.aguirresabino.taskbackgroundexample;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button entrar;
    private EditText email, senha;

    public static Handler handler;

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

        handler = new LoginHandler();

    }

    private class LoginHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Integer code = (Integer) msg.obj;

            entrar.setEnabled(true);

            if(code == 200) Toast.makeText(getApplicationContext(), "Usuário logado | Código" + code.toString(), Toast.LENGTH_LONG).show();
            else if(code == 401) Toast.makeText(getApplicationContext(), "Usuário não encontrado | Código " + code.toString(), Toast.LENGTH_LONG).show();

            entrar.setText("Entrar");
        }
    }
}
