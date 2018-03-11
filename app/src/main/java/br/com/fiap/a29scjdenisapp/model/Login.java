package br.com.fiap.a29scjdenisapp.model;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Login implements Serializable {

    private String id;
    @SerializedName(value = "usuario")
    private String login;
    @SerializedName(value = "senha")
    private String senha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Login(){}

    public Login(String usuario, String senha)
    {
        this.login = usuario;
        this.senha = senha;
    }

    public Login(String id, String usuario, String senha)
    {
        this.id = id;
        this.login = usuario;
        this.senha = senha;
    }
}
