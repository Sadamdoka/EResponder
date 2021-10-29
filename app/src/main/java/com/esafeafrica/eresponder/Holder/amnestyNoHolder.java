package com.esafeafrica.eresponder.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.amnestyNoAdaptor;
import com.esafeafrica.eresponder.Model.Numbers;
import com.esafeafrica.eresponder.R;


public class amnestyNoHolder extends RecyclerView.ViewHolder {
    private Context context;
    private LinearLayout parent;
    private TextView name, phone, email;
    private String id, userid, dat;

    public amnestyNoHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.contact_parent);
        name = itemView.findViewById(R.id.contact_name);
        phone = itemView.findViewById(R.id.contact_tel);
        email = itemView.findViewById(R.id.contact_email);
    }

    public void bind(final Numbers numbers, final amnestyNoAdaptor.OnItemClickListener listener) {
        id = numbers.getId();
        dat = numbers.getDatereg();
        name.setText(numbers.getNames());
        email.setText(numbers.getEmail());
        phone.setText(numbers.getPhone());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(numbers);
            }
        });
    }
}
