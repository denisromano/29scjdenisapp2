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

import br.com.fiap.a29scjdenisapp.adapter.LoginAdapter;
import br.com.fiap.a29scjdenisapp.api.LoginAPI;
import br.com.fiap.a29scjdenisapp.api.ClickRecyclerView_Interface;
import br.com.fiap.a29scjdenisapp.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginListaFragment extends Fragment implements ClickRecyclerView_Interface{


    private RecyclerView rvLogin;
    private RecyclerView.LayoutManager mLayoutManager;
    private LoginAdapter adapter;
    private List<Login> users = new ArrayList<>();

    public LoginListaFragment() {
        // Required empty public constructor
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lista_login, container, false);

        preencheRecyclerView("");
        return v;
    }

    private void preencheRecyclerView(String pesquisa){
        if(!pesquisa.equals("") && pesquisa !=null){
            retornaLogin(pesquisa);
            rvLogin = (RecyclerView) v.findViewById(R.id.rvLogin);
            mLayoutManager = new LinearLayoutManager(getActivity());
            rvLogin.setLayoutManager(mLayoutManager);
            adapter = new LoginAdapter(getActivity(),users,this);
            rvLogin.setAdapter(adapter);
        }
        else
        {
            retornaLogin();
            rvLogin = (RecyclerView) v.findViewById(R.id.rvLogin);
            mLayoutManager = new LinearLayoutManager(getContext());
            rvLogin.setLayoutManager(mLayoutManager);
            adapter = new LoginAdapter(getActivity(),users,this);
            rvLogin.setAdapter(adapter);
        }
    }


    @Override
    public void onCustomClick(Object object){
        Login u = (Login) object;

        if(u==null){
            Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), getString(R.string.loginNaoEncontrado), Snackbar.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(getActivity(),ManuLoginActivity.class);
            intent.putExtra("Login",u);
            startActivity(intent);
        }
    }

    @Override
    public void onCloseButton(Object object){
        final Login u = (Login) object;

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getActivity().getWindow().getDecorView().getRootView().getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity().getWindow().getDecorView().getRootView().getContext());
        }
        builder.setTitle(getString(R.string.deletarLogin))
                .setMessage(getString(R.string.msgDeletarUsuario))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        apagaUsuarioLogin(u.getLogin());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Faça nada
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void retornaLogin(){
        Thread t = (new Thread()
        {
            public void run()
            {
                try
                {
                    LoginAPI api = getRetrofit().create(LoginAPI.class);
                    users = api.findAll().execute().body();
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

    private void retornaLogin(final String login){
        Thread t = (new Thread()
        {
            public void run()
            {
                try
                {
                    LoginAPI api = getRetrofit().create(LoginAPI.class);
                    users = api.findByLogin(login).execute().body();
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

    private void apagaUsuarioLogin(String login){
        LoginAPI api = getRetrofit().create(LoginAPI.class);

        api.deleteLogin(login).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), getString(R.string.DeletarLogin), Snackbar.LENGTH_LONG).show();
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
