package com.esafeafrica.eresponder.Holder;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.eventAdaptor;
import com.esafeafrica.eresponder.Model.Emergency;
import com.esafeafrica.eresponder.R;

import java.io.IOException;


public class eventHolder extends RecyclerView.ViewHolder {
    private Context context;
    private LinearLayout parent;
    private TextView name, event, brief, extras, location, dat;
    private String id, status, lati, longi;


    public eventHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.event_parent);
        name = itemView.findViewById(R.id.event_name);
        event = itemView.findViewById(R.id.event_event);
        brief = itemView.findViewById(R.id.event_brief);
        extras = itemView.findViewById(R.id.event_extras);
        location = itemView.findViewById(R.id.event_loca);
        dat = itemView.findViewById(R.id.event_time);
    }

    public void bind(final Emergency emergency, final eventAdaptor.OnItemClickListener listener) throws IOException {
        id = emergency.getId();
        status = emergency.getStatus();
        name.setText(emergency.getName());
        event.setText(emergency.getEvent());
        brief.setText(emergency.getTopic());
        extras.setText(emergency.getDetails());
        location.setText(emergency.getLocation());
        dat.setText(emergency.getDatereg());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(emergency);
            }
        });
    }
}
