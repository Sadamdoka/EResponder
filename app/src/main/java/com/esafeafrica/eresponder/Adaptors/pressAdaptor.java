package com.esafeafrica.eresponder.Adaptors;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.eresponder.Holder.pressHolder;
import com.esafeafrica.eresponder.Model.Press;
import com.esafeafrica.eresponder.R;

import java.util.List;

public class pressAdaptor extends RecyclerView.Adapter<pressHolder> {

    private Context context;
    private List<Press> pressList;
    private OnItemClickListener listener;

    public pressAdaptor(Context context, List<Press> pressList, OnItemClickListener listener) {
        this.context = context;
        this.pressList = pressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public pressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_press, parent, false);
        pressHolder holder = new pressHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull pressHolder holder, int position) {
        holder.bind(pressList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return pressList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Press press);
    }
}
