package br.com.fiap.a29scjdenisapp.api;

import java.util.List;

import br.com.fiap.a29scjdenisapp.model.Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginAPI {

    @GET("login/usuario/{usuario}")
    Call<Login> buscaLogin(@Path("usuario")String usuario);

    @GET("/login")
    Call<List<Login>> findAll();

    @POST("/login")
    Call<Void> salvaLogin(@Body Login u);

    @GET("login/usuario/{nomeUsuario}")
    Call<List<Login>> findByLogin(@Path("nomeUsuario") String nomeUsuario);

    @POST("/login")
    Call<Void> atualizarLogin(@Body Login u);

    @DELETE("/login/usuario/{id}")
    Call<Void> deleteLogin(@Path("id") String id);

}
