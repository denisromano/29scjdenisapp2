package br.com.fiap.a29scjdenisapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.fiap.a29scjdenisapp.api.LoginAPI;
import br.com.fiap.a29scjdenisapp.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar mProgressView;
    private TextView etLogin;
    private TextView etSenha;
    private Button btLogin;
    private ProgressBar login_incio;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        etLogin = (EditText)findViewById(R.id.etLogin);
        etSenha = (EditText)findViewById(R.id.etSenha);
        btLogin = (Button)findViewById(R.id.btLogin);
        login_incio = (ProgressBar) findViewById(R.id.progressbar);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

    }

    public void logar (View view){
        login_incio.setVisibility(View.VISIBLE);
        if(campoVazio()) {
            LoginAPI api = getRetrofit().create(LoginAPI.class);
            Login u = new Login();
            api.buscaLogin(etLogin.getText().toString())
                    .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Login u = response.body();
                            if ((u.getLogin() == null) && (u.getSenha() == null)) {
                                Toast.makeText(LoginActivity.this, getString(R.string.UsuarioSenhaIncorreto), Toast.LENGTH_LONG).show();
                                etSenha.setText("");
                                etSenha.requestFocus();
                                login_incio.setVisibility(View.GONE);
                            }else{
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            LoginActivity.this.finish();
                            startActivity(intent);
                            saveOnPreferences(u.getLogin(),u.getSenha());
                            login_incio.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, getString(R.string.UsuarioSenhaIncorreto), Toast.LENGTH_LONG).show();
                            etSenha.setText("");
                            etSenha.requestFocus();
                            login_incio.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private boolean campoVazio(){
        if(etLogin.getText().toString().length()==0){
            etLogin.setError(getString(R.string.campoUsuarioObg));
            return false;
        }
        else if((etSenha.getText().toString().length()==0)){
            etSenha.setError(getString(R.string.campoSenhObg));
            return false;
        }
        else{
            return true;
        }
    }

    public void saveOnPreferences(String login, String senha){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("login", login);
            editor.putString("senha", senha);
            //editor.commit();
            editor.apply();

    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl("https://denisimoveis.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
