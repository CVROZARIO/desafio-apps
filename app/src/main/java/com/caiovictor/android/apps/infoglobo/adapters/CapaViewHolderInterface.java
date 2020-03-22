package com.caiovictor.android.apps.infoglobo.adapters;

import android.content.Context;
import android.widget.ImageView;

public interface CapaViewHolderInterface {

    Context getContext();
    void setTitle(String v);
    void setSecao(String v);
    void setLastUpdate(String v);
    void setLastUpdate(int v);
    ImageView getThumbView();

}
