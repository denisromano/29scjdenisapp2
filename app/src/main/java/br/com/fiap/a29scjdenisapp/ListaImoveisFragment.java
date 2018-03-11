package br.com.fiap.a29scjdenisapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.a29scjdenisapp.adapter.ImovelAdapter;
import br.com.fiap.a29scjdenisapp.api.ImovelAPI;
import br.com.fiap.a29scjdenisapp.api.ClickRecyclerView_Interface;
import br.com.fiap.a29scjdenisapp.model.Imovel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaImoveisFragment extends Fragment implements ClickRecyclerView_Interface {

    private RecyclerView rvImovel;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImovelAdapter adapter;
    private List<Imovel> imoveis = new ArrayList<>();

    public ListaImoveisFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lista_imoveis, container, false);

        preencheRecyclerView("");

        return v;
    }


    private void preencheRecyclerView(String pesquisa){
            retornaImoveis();
            rvImovel = (RecyclerView) v.findViewById(R.id.rvImovel);
            mLayoutManager = new LinearLayoutManager(getActivity());
            rvImovel.setLayoutManager(mLayoutManager);
            adapter = new ImovelAdapter(getActivity(),imoveis,this);
            rvImovel.setAdapter(adapter);

    }


    @Override
    public void onCustomClick(Object object){
        Imovel i = (Imovel) object;

        if(i==null)
        {
            Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), getString(R.string.errEncontrarImovel), Snackbar.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(getActivity(),
                    ManuImovelActivity.class);

            intent.putExtra("Imovel",i);
            startActivity(intent);
        }
    }

    @Override
    public void onCloseButton(Object object) {
        final Imovel i = (Imovel) object;

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getActivity().getWindow().getDecorView().getRootView().getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity().getWindow().getDecorView().getRootView().getContext());
        }
        builder.setTitle(getString(R.string.deletarImovel))
                .setMessage(getString(R.string.msgDeletarImovel))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        apagaImovel(i.getTipo());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void retornaImoveis(){
        Thread t = (new Thread()
        {
            public void run()
            {
                try
                {
                    ImovelAPI api = getRetrofit().create(ImovelAPI.class);
                    imoveis = api.findAll().execute().body();
                }
                catch(NetworkOnMainThreadException | IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void apagaImovel(String tipo){
        ImovelAPI api = getRetrofit().create(ImovelAPI.class);

        api.deleteImovel(tipo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), getString(R.string.msgDeletarImovelSucess), Snackbar.LENGTH_LONG).show();
                preencheRecyclerView("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), getString(R.string.errorConexao), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl("https://denisimoveis.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
