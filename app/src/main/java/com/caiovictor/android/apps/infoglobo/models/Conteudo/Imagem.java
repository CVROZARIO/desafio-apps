package com.caiovictor.android.apps.infoglobo.models.Conteudo;

import android.os.Parcel;
import android.os.Parcelable;

public class Imagem implements Parcelable {

    private String autor;
    private String fonte;
    private String legenda;
    private String url;

    public Imagem(String autor, String fonte, String legenda, String url) {
        this.autor = autor;
        this.fonte = fonte;
        this.legenda = legenda;
        this.url = url;
    }

    public Imagem(){
    }

    protected Imagem(Parcel in) {
        autor = in.readString();
        fonte = in.readString();
        legenda = in.readString();
        url = in.readString();
    }

    public static final Creator<Imagem> CREATOR = new Creator<Imagem>() {
        @Override
        public Imagem createFromParcel(Parcel in) {
            return new Imagem(in);
        }

        @Override
        public Imagem[] newArray(int size) {
            return new Imagem[size];
        }
    };

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(autor);
        parcel.writeString(fonte);
        parcel.writeString(legenda);
        parcel.writeString(url);
    }
}
