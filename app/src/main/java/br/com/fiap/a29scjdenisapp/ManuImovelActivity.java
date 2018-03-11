package br.com.fiap.a29scjdenisapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class ManuImovelActivity extends AppCompatActivity {

    private Imovel i;
    private FloatingActionButton fab;

    private EditText etTipoImovelManu, etLocalizaImovelManu,
            etITamanhoImovelManu, etAnoImovelManu, etValorImovelManu;
    private Button btSalvarImovel;
    private ProgressBar progressBar;
    private Spinner spBanheiroManu, spQuartosManu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_imovel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        i = (Imovel) getIntent().getSerializableExtra("Imovel");

        etTipoImovelManu = (EditText) findViewById(R.id.etTipoImovelManu);
        etLocalizaImovelManu = (EditText) findViewById(R.id.etLocalizacaoImovelManu);
        etITamanhoImovelManu = (EditText) findViewById(R.id.etTamanhoImovelManu);
        etAnoImovelManu = (EditText) findViewById(R.id.etAnoImovelManu);
        etValorImovelManu = (EditText) findViewById(R.id.etValorImovelManu);
        spQuartosManu = (Spinner) findViewById(R.id.spQuartosImovelManu);
        spBanheiroManu = (Spinner) findViewById(R.id.spBanheirosImovelManu);
        btSalvarImovel = (Button) findViewById(R.id.btSalvarImovelManu);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar = (ProgressBar) findViewById(R.id.progressConf);
        preencheCampos();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void preencheCampos() {
        etTipoImovelManu.setText(i.getTipo());
        etLocalizaImovelManu.setText(i.getLocalizacao());
        etITamanhoImovelManu.setText(i.getTamanho());
        etAnoImovelManu.setText(i.getAno());
        etValorImovelManu.setText(i.getValor());
        //TODO: Resolver o retorno do vaor
        String.valueOf(spQuartosManu.getSelectedItem());
        String.valueOf(spBanheiroManu.getSelectedItem());
    }

    public void modificarImovel(View v) {
        etTipoImovelManu.setEnabled(true);
        etLocalizaImovelManu.setEnabled(true);
        etITamanhoImovelManu.setEnabled(true);
        etAnoImovelManu.setEnabled(true);
        etValorImovelManu.setEnabled(true);
        spQuartosManu.setEnabled(true);
        spBanheiroManu.setEnabled(true);
        etTipoImovelManu.requestFocus();
        Snackbar.make(v, getString(R.string.campoLiberado), Snackbar.LENGTH_LONG).show();
        fab.setVisibility(View.GONE);
        btSalvarImovel.setVisibility(View.VISIBLE);
    }

    private void bloqueaCampos() {
        etTipoImovelManu.setEnabled(false);
        etLocalizaImovelManu.setEnabled(false);
        etITamanhoImovelManu.setEnabled(false);
        etAnoImovelManu.setEnabled(false);
        etValorImovelManu.setEnabled(false);
        spQuartosManu.setEnabled(false);
        spBanheiroManu.setEnabled(false);
        fab.setVisibility(View.VISIBLE);
        btSalvarImovel.setVisibility(View.GONE);
    }

    public void salvarImovel(final View v){
        progressBar.setVisibility(View.VISIBLE);
        if(verificaCampos())
        {
            ImovelAPI api = getRetrofit().create(ImovelAPI.class);
            Imovel i = new Imovel(etTipoImovelManu.getText().toString(),
                    etLocalizaImovelManu.getText().toString(),
                    etITamanhoImovelManu.getText().toString(),
                    etAnoImovelManu.getText().toString(),
                    etValorImovelManu.getText().toString(),
                    String.valueOf(spQuartosManu.getSelectedItem()),
                    String.valueOf(spBanheiroManu.getSelectedItem()));

            api.salvar(i).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response)
                {
                    Snackbar.make(v, getString(R.string.imovelAtualizado), Snackbar.LENGTH_LONG).show();
                    bloqueaCampos();
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

    private boolean verificaCampos(){
        if(etTipoImovelManu.getText().toString().length()==0){
            etTipoImovelManu.setError(getString(R.string.tipoImovelObg));
            return false;
        }
        else if(etLocalizaImovelManu.getText().toString().length()==0){
            etLocalizaImovelManu.setError(getString(R.string.localizacaoImovelObg));
            return false;
        }
        else if(etITamanhoImovelManu.getText().toString().length()==0){
            etITamanhoImovelManu.setError(getString(R.string.tamanhoImovelObg));
            return false;
        }
        else if(etAnoImovelManu.getText().toString().length()==0){
            etAnoImovelManu.setError(getString(R.string.anoImovelObg));
            return false;
        }
        else if(etValorImovelManu.getText().toString().length()==0){
            etValorImovelManu.setError(getString(R.string.valorImovelObg));
            return false;
        }
        else if(spQuartosManu.getSelectedItem().toString().length()==0){
            //TODO: lembrar de resolver a mensagem do GET
            //etQuartosImovelManu.setError(getString(R.string.quartosImovelObg));
            return false;
        }
        else if(spBanheiroManu.getSelectedItem().toString().length()==0){
            //TODO: lembrar de resolver a mensagem do GET
            //etBanheirosImovelManu.setError(getString(R.string.banheiroImovelObg));
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
}
