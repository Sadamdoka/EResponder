package com.esafeafrica.eresponder.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.contactAdaptor;
import com.esafeafrica.eresponder.Model.Contact;
import com.esafeafrica.eresponder.R;


public class contactHolder extends RecyclerView.ViewHolder {
    private Context context;
    private LinearLayout parent;
    private TextView name, phone, email;
    private String id, userid, dat;

    public contactHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.contact_parent);
        name = itemView.findViewById(R.id.contact_name);
        phone = itemView.findViewById(R.id.contact_tel);
        email = itemView.findViewById(R.id.contact_email);
    }

    public void bind(final Contact contact, final contactAdaptor.OnItemClickListener listener) {
        id = contact.getId();
        userid = contact.getUserid();
        dat = contact.getDatereg();
        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(contact);
            }
        });
    }
}
