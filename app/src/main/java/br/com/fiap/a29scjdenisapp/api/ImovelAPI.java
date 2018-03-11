package br.com.fiap.a29scjdenisapp.api;


import java.util.List;

import br.com.fiap.a29scjdenisapp.model.Imovel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ImovelAPI {

    @GET("/imoveis")
    Call<List<Imovel>> findAll();

    @POST("/imoveis")
    Call<Void> salvar(@Body Imovel i);

    @POST("/login")
    Call<Void> atualizar(@Body Imovel i);

    @DELETE("/imoveis/imovel/{id}")
    Call<Void> deleteImovel(@Path("id") String id);
}
