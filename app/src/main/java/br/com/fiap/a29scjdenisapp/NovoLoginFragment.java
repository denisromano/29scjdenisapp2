package br.com.fiap.a29scjdenisapp;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import br.com.fiap.a29scjdenisapp.api.LoginAPI;
import br.com.fiap.a29scjdenisapp.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NovoLoginFragment extends Fragment implements View.OnClickListener{

    private EditText etLogin, etSenha;
    private ProgressBar progressBar;
    private Button btSalvarUsuarioNovo;
    private Spinner spinner1;

    public NovoLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_novo_login, container, false);

            etLogin = (EditText) v.findViewById(R.id.etNovoLogin);
            etSenha = (EditText) v.findViewById(R.id.etNovaSenha);
            progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
            btSalvarUsuarioNovo = (Button) v.findViewById(R.id.btSalvarLoginNovo);
            btSalvarUsuarioNovo.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSalvarLoginNovo:
                salvar(v);
                break;
        }
    }
    private void salvar(final View v){
        progressBar.setVisibility(View.VISIBLE);
        if(checar())
        {
            LoginAPI api = getRetrofit().create(LoginAPI.class);
            Login u = new Login(etLogin.getText().toString(), etSenha.getText().toString());

            api.salvaLogin(u).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response)
                {
                    Snackbar.make(v, getString(R.string.salvaLogin), Snackbar.LENGTH_LONG).show();
                    limpar();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t)
                {
                    System.out.println(t);
                    Snackbar.make(v, getString(R.string.errorConexao), Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean checar(){
        if(etLogin.getText().toString().length()==0){
            etLogin.setError(getString(R.string.campoUsuarioObg));
            return false;
        }else if(etSenha.getText().toString().length()==0){
            etSenha.setError(getString(R.string.campoSenhObg));
            return false;
        }else{
            return true;
        }
    }

    private Retrofit getRetrofit()
    {
        return new Retrofit.Builder().baseUrl("https://denisimoveis.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void limpar()
    {
        etLogin.setText("");
        etSenha.setText("");
        etLogin.requestFocus();
    }
}
