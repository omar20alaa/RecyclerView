package com.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recyclerview.Interface.itemClickListener;
import com.recyclerview.R;
import com.recyclerview.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    private Context context;
    List<ItemData> itemDataList;

    public itemAdapter(Context context, List<ItemData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(itemDataList.get(position).getName());

        Picasso
                .get()
                .load(itemDataList.get(position).getImage())
                .into(holder.itemImage);

        // implement item click
        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(context, " "+ itemDataList.get(position).getName() , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemImage)
        ImageView itemImage;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        itemClickListener itemClickListener;

        public void setItemClickListener(com.recyclerview.Interface.itemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClickListener(v , getAdapterPosition());
        }
    }
}
