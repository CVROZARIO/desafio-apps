package com.caiovictor.android.apps.infoglobo.models;

import com.caiovictor.android.apps.infoglobo.util.Converters;

import java.io.Serializable;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class ConteudoViewUser implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "conteudo_id")
    private long conteudoId;

    @ColumnInfo(name = "view_at")
    @TypeConverters({Converters.class})
    private Date viewDate;

    public ConteudoViewUser(long conteudoId, Date viewDate){
        this.conteudoId = conteudoId;
        this.viewDate = viewDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConteudoId() {
        return conteudoId;
    }

    public void setConteudoId(int conteudoId) {
        this.conteudoId = conteudoId;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }
}
