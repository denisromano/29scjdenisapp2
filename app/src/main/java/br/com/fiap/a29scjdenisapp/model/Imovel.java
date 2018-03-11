package br.com.fiap.a29scjdenisapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Imovel  implements Serializable {

    private String id;
    @SerializedName(value = "tipo")
    private String tipo;
    @SerializedName(value = "localizacao")
    private String localizacao;
    @SerializedName(value = "tamanho")
    private String tamanho;
    @SerializedName(value = "ano")
    private String ano;
    @SerializedName(value = "valor")
    private String valor;
    @SerializedName(value = "quartos")
    private String quartos;
    @SerializedName(value = "banheiros")
    private String banheiros;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuartos() {
        return quartos;
    }

    public void setQuartos(String quartos) {
        this.quartos = quartos;
    }

    public String getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(String banheiros) {
        this.banheiros = banheiros;
    }

    public Imovel(String tipo, String localizacao, String tamanho, String ano, String valor, String quartos, String banheiros) {
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.ano = ano;
        this.valor = valor;
        this.quartos = quartos;
        this.banheiros = banheiros;
    }

    public Imovel(String id, String tipo, String localizacao, String tamanho, String ano, String valor, String quartos, String banheiros) {
        this.id = id;
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.ano = ano;
        this.valor = valor;
        this.quartos = quartos;
        this.banheiros = banheiros;
    }

}
