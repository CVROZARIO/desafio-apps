package com.caiovictor.android.apps.infoglobo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caiovictor.android.apps.infoglobo.R;
import com.caiovictor.android.apps.infoglobo.models.Capa;
import com.caiovictor.android.apps.infoglobo.models.Conteudo.Conteudo;
import com.caiovictor.android.apps.infoglobo.util.Commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConteudoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Conteudo> mConteudos = new ArrayList<>();
    private OnCapaConteudoListener mOnCapaConteudoListener;

    public ConteudoAdapter(OnCapaConteudoListener onCapaConteudoListener){
        this.mOnCapaConteudoListener = onCapaConteudoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case Conteudo.TypeMode.TYPE_LINKEXTERNO: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_capa_linkexterno, parent, false);
                return new CapaLinkExternoViewHolder(view, mOnCapaConteudoListener);
            }

            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_capa_materia, parent, false);
                return new CapaMateriaViewHolder(view, mOnCapaConteudoListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RequestOptions requestOptions = null;
        Conteudo conteudo = mConteudos.get(position);
        final CapaViewHolderInterface capaViewHolderInterface = (CapaViewHolderInterface) holder;

        if (conteudo.getImagens() != null && conteudo.getImagens().length > 0) {
            requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.conteudo_thumb_placeholder)
                    .centerCrop();
        }

        capaViewHolderInterface.setTitle(conteudo.getTitulo());
        capaViewHolderInterface.setSecao(conteudo.getSecao().getNome());

        Date date = Commons.getDateFromString(conteudo.getAtualizadoEm());
        if(date != null) {
            capaViewHolderInterface.setLastUpdate(Commons.getHumanElapsedTimeFrom(capaViewHolderInterface.getContext(), date));
        }else{
            capaViewHolderInterface.setLastUpdate(R.string.human_elapsed_time_unknow);
        }

        if (requestOptions != null) {
            if(capaViewHolderInterface.getThumbView().getVisibility() != View.VISIBLE) {
                capaViewHolderInterface.getThumbView().setVisibility(View.VISIBLE);
            }
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .load(conteudo.getImagens()[0].getUrl())// ASSUME QUE A PRIMERIA IMAGEM DA LISTA É A THUMB, JÁ QUE NÃO HÁ FLAG PARA TAL
                    .into(capaViewHolderInterface.getThumbView());
        }else{
            if(capaViewHolderInterface.getThumbView().getVisibility() != View.GONE) {
                capaViewHolderInterface.getThumbView().setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mConteudos.get(position).getTypeMode();
    }

    @Override
    public int getItemCount() {
        return mConteudos.size();
    }

    public void setQueryExhausted(){
        mConteudos.add(new Conteudo());
        notifyDataSetChanged();
    }

    public void setConteudos(List<Conteudo> conteudos){
        this.mConteudos = conteudos;
        notifyDataSetChanged();
    }

}
