package com.caiovictor.android.apps.infoglobo.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caiovictor.android.apps.infoglobo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CapaMateriaViewHolder extends RecyclerView.ViewHolder implements CapaViewHolderInterface, View.OnClickListener {

    Context context;
    OnCapaConteudoListener mOnCapaConteudoListener;
    TextView title, secao, lastUpdate;
    ImageView thumbView;

    public CapaMateriaViewHolder(@NonNull View itemView, OnCapaConteudoListener onCapaConteudoListener) {
        super(itemView);

        this.context = itemView.getContext();

        this.mOnCapaConteudoListener = onCapaConteudoListener;

        this.title = itemView.findViewById(R.id.title);
        this.secao = itemView.findViewById(R.id.secao);
        this.lastUpdate = itemView.findViewById(R.id.last_update);
        this.thumbView = itemView.findViewById(R.id.thumb);

        itemView.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCapaConteudoListener.onCapaShareClick(getAdapterPosition());
            }
        });

        itemView.findViewById(R.id.conteudo_wrapper).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnCapaConteudoListener.onCapaItemClick(getAdapterPosition(), view);
    }

    @Override
    public Context getContext(){
        return this.context;
    }

    @Override
    public void setTitle(String v) {
        this.title.setText(v);
    }

    @Override
    public void setSecao(String v){
        this.secao.setText(v);
    }

    @Override
    public void setLastUpdate(String v) {
        this.lastUpdate.setText(v);
    }

    @Override
    public void setLastUpdate(int v) {
        this.lastUpdate.setText(v);
    }

    @Override
    public ImageView getThumbView() {
        return this.thumbView;
    }
}
