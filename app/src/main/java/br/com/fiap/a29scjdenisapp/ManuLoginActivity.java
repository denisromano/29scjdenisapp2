package br.com.fiap.a29scjdenisapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import br.com.fiap.a29scjdenisapp.api.LoginAPI;
import br.com.fiap.a29scjdenisapp.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManuLoginActivity extends AppCompatActivity {

    private Login l;

    // Referencias
    private AutoCompleteTextView etLogin,
            etSenha;
    private FloatingActionButton fab;
    private Button btSalvarLogin;
    private ProgressBar login_progressConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        l = (Login) getIntent().getSerializableExtra("Login");

        //Referencias
        etLogin = (AutoCompleteTextView) findViewById(R.id.etLoginManu);
        etSenha = (AutoCompleteTextView) findViewById(R.id.etSenhaManu);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btSalvarLogin = (Button) findViewById(R.id.btSalvarLogin);
        login_progressConf = (ProgressBar) findViewById(R.id.progressConf);
        carregarCampos();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregarCampos() {
        etLogin.setText(l.getLogin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void modificarLogin(View v) {
        etLogin.setEnabled(true);
        etSenha.setEnabled(true);
        etLogin.requestFocus();
        Snackbar.make(v, getString(R.string.campoLiberado), Snackbar.LENGTH_LONG).show();
        fab.setVisibility(View.GONE);
        btSalvarLogin.setVisibility(View.VISIBLE);
    }

    private void bloqueaCampos() {
        etLogin.setEnabled(false);
        etSenha.setEnabled(false);
        fab.setVisibility(View.VISIBLE);
        btSalvarLogin.setVisibility(View.GONE);
        etSenha.setText("");
    }

    private Retrofit getRetrofit()
    {
        return new Retrofit.Builder().baseUrl("https://denisimoveis.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private boolean checar() {
        if (etLogin.getText().toString().length() == 0) {
            etLogin.setError(getString(R.string.campoUsuarioObg));
            return false;
        } else if (etSenha.getText().toString().length() == 0) {
            etSenha.setError(getString(R.string.campoSenhObg));
            return false;
        } else {
            return true;
        }
    }

    public void salvarLogin(final View v)
    {
        //mostra barra de progresso
        login_progressConf.setVisibility(View.VISIBLE);
        if(checar())
        {
                LoginAPI api = getRetrofit().create(LoginAPI.class);
                Login u = new Login(etLogin.getText().toString(), etSenha.getText().toString());
                api.atualizarLogin(u).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)
                    {
                        Snackbar.make(v, getString(R.string.loginAtualizado), Snackbar.LENGTH_LONG).show();
                        bloqueaCampos();
                        login_progressConf.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t)
                    {
                        System.out.println(t);
                        Snackbar.make(v, getString(R.string.errorConexao), Snackbar.LENGTH_LONG).show();
                        login_progressConf.setVisibility(View.GONE);
                    }
                });
        }
        else
        {
            login_progressConf.setVisibility(View.GONE);
        }
    }
}
