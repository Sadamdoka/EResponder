package com.esafeafrica.eresponder.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.anounceAdaptor;
import com.esafeafrica.eresponder.Model.Anounce;
import com.esafeafrica.eresponder.R;

import static com.esafeafrica.eresponder.Config.Validation.ConvertImage;
import static com.esafeafrica.eresponder.Config.Validation.getDrawable;


public class anounceHolder extends RecyclerView.ViewHolder {

    private LinearLayout parent, mage;
    private TextView name, title, detail, dat;
    private Context context;
    private String id, status;

    public anounceHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.anounce_parent);
        mage = itemView.findViewById(R.id.anounce_pic);
        name = itemView.findViewById(R.id.anounce_name);
        title = itemView.findViewById(R.id.anounce_title);
        detail = itemView.findViewById(R.id.anounce_detail);
        dat = itemView.findViewById(R.id.anounce_time);
    }

    public void bind(final Anounce anounce, final anounceAdaptor.OnItemClickListener listener) {
        id = anounce.getId();
        status = anounce.getStatus();
        name.setText(anounce.getName());
        title.setText(anounce.getTitle());
        detail.setText(anounce.getDetails());
        mage.setBackground(getDrawable(context, ConvertImage(anounce.getPic())));
        dat.setText(anounce.getDatereg());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(anounce);
            }
        });
    }
}
