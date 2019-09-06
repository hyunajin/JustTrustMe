package com.example.asus.justtrustme;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private ArrayList<Contact> datas;
    private Context context;

    public RecyclerAdapter(ArrayList<Contact> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = datas.get(position);

        holder.contacts_Name.setText(contact.getName());
        holder.contacts_Tel.setText(contact.getTelOne());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CardView contacts_cardView;
        TextView contacts_Name,contacts_Tel;
        ImageButton btnCancel;

        public Holder(View itemView) {
            super(itemView);

            contacts_cardView = (CardView) itemView.findViewById(R.id.contacts_cardView);
            contacts_Name = (TextView) itemView.findViewById(R.id.contacts_Name);
            contacts_Tel = (TextView) itemView.findViewById(R.id.contacts_Tel);
            btnCancel = itemView.findViewById(R.id.btnCancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });

        }
    }
    public void removeAt(int position){
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
    }
}

