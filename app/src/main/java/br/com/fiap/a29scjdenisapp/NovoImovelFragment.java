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

import br.com.fiap.a29scjdenisapp.api.ImovelAPI;
import br.com.fiap.a29scjdenisapp.model.Imovel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NovoImovelFragment extends Fragment implements View.OnClickListener {

    private EditText etTipoImovel,etLocalizacaoImovel,etTamanhoImovel,
            etAnoImovel,etValorImovel;
    private Button btSalvarNovoImovel;
    private ProgressBar login_progressBar;
    private Spinner spBanheiro, spQuartos;

    public NovoImovelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_novo_imovel, container, false);

        etTipoImovel = (EditText) v.findViewById(R.id.etTipoImovel);
        etLocalizacaoImovel = (EditText) v.findViewById(R.id.etLocalizacaoImovel);
        etTamanhoImovel = (EditText) v.findViewById(R.id.etTamanhoImovel);
        etAnoImovel = (EditText) v.findViewById(R.id.etAnoImovel);
        etValorImovel = (EditText) v.findViewById(R.id.etValorImovel);
        spQuartos = (Spinner) v.findViewById(R.id.spQuartosImovel);
        spBanheiro = (Spinner) v.findViewById(R.id.spBanheirosImovel);
        btSalvarNovoImovel = (Button) v.findViewById(R.id.btSalvarNovoImovel);
        btSalvarNovoImovel.setOnClickListener(this);

        login_progressBar = (ProgressBar) v.findViewById(R.id.progressbar);

        return v;
    }

    @Override
    public void onClick(View v)    {
        switch (v.getId()) {
            case R.id.btSalvarNovoImovel:
                salvarImovel(v);
                break;
        }
    }

    private void salvarImovel(final View v){
        login_progressBar.setVisibility(View.VISIBLE);
        if(checar())
        {
            ImovelAPI api = getRetrofit().create(ImovelAPI.class);
            Imovel i = new Imovel(etTipoImovel.getText().toString(),
                    etLocalizacaoImovel.getText().toString(),
                    etTamanhoImovel.getText().toString(),
                    etAnoImovel.getText().toString(),
                    etValorImovel.getText().toString(),
                    String.valueOf(spQuartos.getSelectedItem()),
                    String.valueOf(spBanheiro.getSelectedItem())
                    );

            api.salvar(i).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response)
                {
                    Snackbar.make(v, getString(R.string.imovelSalvo), Snackbar.LENGTH_LONG).show();
                    limpa();
                    login_progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t)
                {
                    System.out.println(t);
                    Snackbar.make(v, getString(R.string.errorConexao), Snackbar.LENGTH_LONG).show();
                    login_progressBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            login_progressBar.setVisibility(View.GONE);
        }
    }

    private boolean checar(){
        if(etTipoImovel.getText().toString().length()==0){
            etTipoImovel.setError(getString(R.string.tipoImovelObg));
            return false;
        }
        else if(etLocalizacaoImovel.getText().toString().length()==0){
            etLocalizacaoImovel.setError(getString(R.string.localizacaoImovelObg));
            return false;
        }
        else if(etTamanhoImovel.getText().toString().length()==0){
            etTamanhoImovel.setError(getString(R.string.tamanhoImovelObg));
            return false;
        }
        else if(etAnoImovel.getText().toString().length()==0){
            etAnoImovel.setError(getString(R.string.anoImovelObg));
            return false;
        }
        else if(etValorImovel.getText().toString().length()==0){
            etValorImovel.setError(getString(R.string.valorImovelObg));
            return false;
        }
        else if(String.valueOf(spQuartos.getSelectedItem()).length()==0){
            //TODO: lembrar de resolver a mensagem do GET
            //spQuartos.setError(getString(R.string.quartosImovelObg));
            return false;
        }
        else if(String.valueOf(spBanheiro.getSelectedItem()).length()==0){
            //TODO: lembrar de resolver a mensagem do GET
            //etBanheirosImovel.setError(getString(R.string.banheiroImovelObg));
            return false;
        }else{
            return true;
        }
    }

    private void limpa(){
        etTipoImovel.setText("");
        etLocalizacaoImovel.setText("");
        etTamanhoImovel.setText("");
        etAnoImovel.setText("");
        etValorImovel.setText("");
        spQuartos.setSelection(0);
        spBanheiro.setSelection(0);
        etTipoImovel.requestFocus();
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl("https://denisimoveis.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
