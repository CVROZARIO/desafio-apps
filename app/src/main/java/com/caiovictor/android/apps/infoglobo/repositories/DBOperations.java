package com.caiovictor.android.apps.infoglobo.repositories;

public abstract class DBOperations {

    protected static final int TIPO_OPERATION_INSERT = 1;
    protected static final int TIPO_OPERATION_UPDATE = 2;
    protected static final int TIPO_OPERATION_DELETE = 3;

    public interface DBOperationsCallback {
        void onInserted(long id);
        void onUpdatedDeleted(int affected);
        void onDeleted(int affected);
    }

}