package com.caiovictor.android.apps.infoglobo.models.Conteudo;

import android.os.Parcel;
import android.os.Parcelable;

public class Secao implements Parcelable {

    private String nome;
    private String url;

    public Secao(String nome, String url){
        this.nome = nome;
        this.url = url;
    }

    public Secao() {
    }

    protected Secao(Parcel in) {
        nome = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Secao> CREATOR = new Creator<Secao>() {
        @Override
        public Secao createFromParcel(Parcel in) {
            return new Secao(in);
        }

        @Override
        public Secao[] newArray(int size) {
            return new Secao[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
