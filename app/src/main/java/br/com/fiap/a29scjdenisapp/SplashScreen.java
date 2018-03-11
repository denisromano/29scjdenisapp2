package br.com.fiap.a29scjdenisapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.fiap.a29scjdenisapp.model.utils;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        //Executando o método que iniciará nossa animação
        carregar();
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash);
        anim.reset();
        //Pegando o nosso objeto criado no layout
        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

           if (!TextUtils.isEmpty(utils.getLoginPrefs(prefs)) && !TextUtils.isEmpty(utils.getLoginSenhaPrefs(prefs))) {
               // Após o tempo definido irá executar a próxima tela
               Intent intent = new Intent(SplashScreen.this,
                       MenuActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               startActivity(intent);
               SplashScreen.this.finish();
           }else {
               // Após o tempo definido irá executar a próxima tela
               Intent intent = new Intent(SplashScreen.this,
                       LoginActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               startActivity(intent);
               SplashScreen.this.finish();
           }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}