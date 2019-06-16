package com.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.recyclerview.R;
import com.recyclerview.model.ItemData;
import com.recyclerview.model.ItemGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class itemGroupAdapter extends RecyclerView.Adapter<itemGroupAdapter.MyViewHolder> {

    private Context context;
    private List<ItemGroup> dataList;


    public itemGroupAdapter(Context context, List<ItemGroup> dataList) {
        this.context = context;
        this.dataList = dataList;
    } // constructor

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

       holder.itemTitle.setText(dataList.get(position).getHeaderTitle());

        List<ItemData> itemData = dataList.get(position).getListItem();

        final itemAdapter itemAdapter = new itemAdapter(context , itemData);
        holder.recyclerViewList.setHasFixedSize(true);
        holder.recyclerViewList.setLayoutManager(new LinearLayoutManager(context , LinearLayout.HORIZONTAL , false));
        holder.recyclerViewList.setAdapter(itemAdapter);

        holder.recyclerViewList.setNestedScrollingEnabled(false);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button more : " + holder.itemTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (dataList != null ? dataList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemTitle)
        TextView itemTitle;

        @BindView(R.id.btnMore)
        Button btnMore;

        @BindView(R.id.recyclerViewList)
        RecyclerView recyclerViewList;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            btnMore = itemView.findViewById(R.id.btnMore);
            recyclerViewList = itemView.findViewById(R.id.recyclerViewList);

        }
    }
}
