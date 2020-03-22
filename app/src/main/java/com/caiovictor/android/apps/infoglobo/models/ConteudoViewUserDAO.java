package com.caiovictor.android.apps.infoglobo.models;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ConteudoViewUserDAO {

    @Insert
    Long insert(ConteudoViewUser conteudoViewUser);

    @Query("SELECT * FROM ConteudoViewUser")
    LiveData<List<ConteudoViewUser>> fetchAllViews();

    @Query("SELECT COUNT(1) FROM ConteudoViewUser CVU WHERE CVU.conteudo_id = :id")
    LiveData<Long> countViewsByConteudoId(long id);

}
