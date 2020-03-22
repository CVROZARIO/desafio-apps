package com.caiovictor.android.apps.infoglobo.models.Conteudo;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Conteudo implements Parcelable {

    @IntDef({TypeMode.TYPE_MATERIA, TypeMode.TYPE_LINKEXTERNO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeMode {
        int TYPE_MATERIA = 1;
        int TYPE_LINKEXTERNO = 2;
    }

    private int id;

    private String[] autores;

    // TODO NÃO HÁ CONTEÚDO COM ESTE FLAG TRUE
    // NECESSÁRIO CONHECER PADRÃO DE CONTEUDO DO INFORME PARA MELHOR EXEBIÇÃO E APROVEITAMENTO
    // GERANDO MELHORES RESULTADOS AO ANÚNCIOS/ANUNCIANTES
    private Boolean informePublicitario;
    private String subTitulo;
    private String texto;

    private Video[] videos;

    private String atualizadoEm;
    private String publicadoEm;

    private Secao secao;

    private String tipo;

    private String titulo;
    private String url;
    private String urlOriginal;

    private Imagem[] imagens;

    public Conteudo(){

    }

    protected Conteudo(Parcel in) {
        id = in.readInt();
        autores = in.createStringArray();
        byte tmpInformePublicitario = in.readByte();
        informePublicitario = tmpInformePublicitario == 0 ? null : tmpInformePublicitario == 1;
        subTitulo = in.readString();
        texto = in.readString();
        videos = (Video[]) in.readArray(Video.class.getClassLoader());
        atualizadoEm = in.readString();
        publicadoEm = in.readString();
        secao = in.readParcelable(Secao.class.getClassLoader());
        tipo = in.readString();
        titulo = in.readString();
        url = in.readString();
        urlOriginal = in.readString();
        imagens = (Imagem[]) in.readArray(Imagem.class.getClassLoader());
    }

    public static final Creator<Conteudo> CREATOR = new Creator<Conteudo>() {
        @Override
        public Conteudo createFromParcel(Parcel in) {
            return new Conteudo(in);
        }

        @Override
        public Conteudo[] newArray(int size) {
            return new Conteudo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeArray(autores);
        parcel.writeByte((byte)(informePublicitario ? 1 : 0));
        parcel.writeString(subTitulo);
        parcel.writeString(texto);
        parcel.writeArray(videos);
        parcel.writeString(atualizadoEm);
        parcel.writeString(publicadoEm);
        parcel.writeParcelable(secao, i);
        parcel.writeString(tipo);
        parcel.writeString(titulo);
        parcel.writeString(url);
        parcel.writeString(urlOriginal);
        parcel.writeArray(imagens);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getAutores() {
        return autores;
    }

    public void setAutores(String[] autores) {
        this.autores = autores;
    }

    public Boolean getInformePublicitario() {
        return informePublicitario;
    }

    public void setInformePublicitario(Boolean informePublicitario) {
        this.informePublicitario = informePublicitario;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Video[] getVideos() {
        return videos;
    }

    public void setVideos(Video[] videos) {
        this.videos = videos;
    }

    public String getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(String atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getPublicadoEm() {
        return publicadoEm;
    }

    public void setPublicadoEm(String publicadoEm) {
        this.publicadoEm = publicadoEm;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTypeMode() {
        if(tipo.toUpperCase().equals("LINKEXTERNO")){// PODERIA SER POR INT ID (SUGESTÃO)
            return TypeMode.TYPE_LINKEXTERNO;
        }else{
            return TypeMode.TYPE_MATERIA;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public Imagem[] getImagens() {
        return imagens;
    }

    public void setImagens(Imagem[] imagens) {
        this.imagens = imagens;
    }
}
