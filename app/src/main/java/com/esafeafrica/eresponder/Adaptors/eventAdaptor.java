package com.esafeafrica.eresponder.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.eresponder.Holder.eventHolder;
import com.esafeafrica.eresponder.Model.Emergency;
import com.esafeafrica.eresponder.R;

import java.io.IOException;
import java.util.List;

public class eventAdaptor extends RecyclerView.Adapter<eventHolder> {
    private Context context;
    private List<Emergency> emergencyList;
    private OnItemClickListener listener;


    public eventAdaptor(Context context, List<Emergency> emergencyList, OnItemClickListener listener) {
        this.context = context;
        this.emergencyList = emergencyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public eventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        eventHolder holder = new eventHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventHolder holder, int position) {
        try {
            holder.bind(emergencyList.get(position), listener);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return emergencyList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Emergency emergency);
    }
}
