package com.caiovictor.android.apps.infoglobo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.caiovictor.android.apps.infoglobo.models.Conteudo.Conteudo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Capa implements Parcelable {

    @SerializedName("conteudos")
    @Expose()
    private List<Conteudo> conteudos = new ArrayList<>();

    // O PRODUTO DEVERIA POSSUIR ID PARA CONTROLE INTERNO E MENOR UTILIZAÇÃO DE RECURSOS PARA
    // DISTINÇÃO E MANUTENÇÃO DO MESMO
    @SerializedName("produto")
    @Expose()
    private String produto;

    public Capa(List<Conteudo> conteudos, String produto){
        //this.conteudos = conteudos;
        this.produto = produto;
    }

    public Capa(){

    }

    protected Capa(Parcel in) {
       // conteudos = in.createTypedArrayList(Conteudo.CREATOR);
        produto = in.readString();
    }

    public static final Creator<Capa> CREATOR = new Creator<Capa>() {
        @Override
        public Capa createFromParcel(Parcel in) {
            return new Capa(in);
        }

        @Override
        public Capa[] newArray(int size) {
            return new Capa[size];
        }
    };

    public List<Conteudo> getConteudos(){
        return conteudos;
    }

    public void setConteudos(List<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeTypedList(conteudos);
        parcel.writeString(produto);
    }
}
