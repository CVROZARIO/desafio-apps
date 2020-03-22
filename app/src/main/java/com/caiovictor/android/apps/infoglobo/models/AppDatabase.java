package com.caiovictor.android.apps.infoglobo.models;

import android.content.Context;

import com.caiovictor.android.apps.infoglobo.util.Converters;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ConteudoViewUser.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ConteudoViewUserDAO conteudoViewUserDao();

    public static final String DATABASE_NAME = "info_globo_db";

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
