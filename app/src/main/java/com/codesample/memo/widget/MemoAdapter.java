package com.codesample.memo.widget;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codesample.memo.R;
import com.codesample.memo.data.Memo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ItemViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position, Memo memo);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewTime;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

    private final OnItemClickListener listener;
    private List<Memo> data;

    public MemoAdapter(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(List<Memo> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Memo memo = data.get(position);
        LocalDateTime time = LocalDateTime.ofEpochSecond(memo.time, 0, ZoneOffset.UTC);
        holder.textViewTitle.setText(memo.title);
        holder.textViewTime.setText(time.toString());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position, memo));
    }

    @Override
    public int getItemCount() {
        return data==null? 0:data.size();
    }
}
