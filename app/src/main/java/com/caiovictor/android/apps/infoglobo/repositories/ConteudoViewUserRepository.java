package com.caiovictor.android.apps.infoglobo.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.caiovictor.android.apps.infoglobo.models.AppDatabase;
import com.caiovictor.android.apps.infoglobo.models.ConteudoViewUser;
import com.caiovictor.android.apps.infoglobo.models.ConteudoViewUserDAO;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ConteudoViewUserRepository {

    private static final String TAG = "CapaRepository";

    private static ConteudoViewUserRepository instance;

    AppDatabase mAppDatabase;
    private ConteudoViewUserRepositoryCallback mRepositoryListener;

    private ConteudoViewUserDAO mConteudoViewUserDAO;

    public static ConteudoViewUserRepository getInstance(Context context,  ConteudoViewUserRepositoryCallback repositoryListener){
        if(instance == null){
            instance = new ConteudoViewUserRepository(context,  repositoryListener);
        }
        return instance;
    }

    public ConteudoViewUserRepository(Context context,  ConteudoViewUserRepositoryCallback repositoryListener) {
        this.mAppDatabase = AppDatabase.getDatabase(context);
        this.mRepositoryListener = repositoryListener;
        this.mConteudoViewUserDAO = mAppDatabase.conteudoViewUserDao();
    }

    public LiveData<List<ConteudoViewUser>> getViews() {
        return mAppDatabase.conteudoViewUserDao().fetchAllViews();
    }

    public LiveData<Long> getCountViewsByConteudoId(long id) {
        return mAppDatabase.conteudoViewUserDao().countViewsByConteudoId(id);
    }

    public void insert(final ConteudoViewUser conteudoViewUser) {
        DBOperationsAsyncTask insertAsyncTask = new DBOperationsAsyncTask(mConteudoViewUserDAO, mRepositoryListener, DBOperations.TIPO_OPERATION_INSERT);
        insertAsyncTask.execute(conteudoViewUser);
    }

    private static class DBOperationsAsyncTask extends AsyncTask<ConteudoViewUser, Void, Long> {

        private ConteudoViewUserDAO mAsyncTaskDao;
        private ConteudoViewUserRepositoryCallback mRepositoryListener;
        private int mOperationType;

        DBOperationsAsyncTask(ConteudoViewUserDAO dao, ConteudoViewUserRepositoryCallback listener, int operationType) {
            mAsyncTaskDao = dao;
            mRepositoryListener = listener;
            mOperationType = operationType;
        }

        @Override
        protected Long doInBackground(final ConteudoViewUser... conteudoViewUsers) {
            long value = -1;

            switch (mOperationType) {
                case DBOperations.TIPO_OPERATION_INSERT:
                    value = mAsyncTaskDao.insert(conteudoViewUsers[0]);
                    break;
                //case DBOperations.TIPO_OPERATION_DELETE:
                //    break;
            }

            return value;
        }

        @Override
        protected void onPostExecute(Long value) {
            super.onPostExecute(value);

            if (mRepositoryListener == null)
                return;

            switch (mOperationType) {
                case DBOperations.TIPO_OPERATION_INSERT:
                    mRepositoryListener.onInserted(value);
                    break;
                //case DBOperations.TIPO_OPERATION_DELETE:
                //    break;
            }
        }
    }
    public interface ConteudoViewUserRepositoryCallback {
        void onInserted(long id);
        //void onDelete(int affected);
    }

}
