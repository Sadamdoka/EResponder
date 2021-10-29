package com.esafeafrica.eresponder.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.pressAdaptor;
import com.esafeafrica.eresponder.Model.Press;
import com.esafeafrica.eresponder.R;

import static com.wakaimalabs.responder.Config.Validation.ConvertImage;
import static com.wakaimalabs.responder.Config.Validation.getDrawable;


public class pressHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView address, dates;
    private LinearLayout img;
    private String id, userid, dat;

    public pressHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        address = itemView.findViewById(R.id.item_pr_address);
        dates = itemView.findViewById(R.id.item_pr_date);
        img = itemView.findViewById(R.id.item_pr_img);
    }

    public void bind(final Press press, final pressAdaptor.OnItemClickListener listener) {
        id = press.getId();
        dat = press.getDatereg();
        address.setText(press.getAddress());
        dates.setText(press.getDateAddress());
        img.setBackground(getDrawable(context, ConvertImage(press.getPic1())));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(press);
            }
        });
    }
}
