package com.example.pcconfighelpercoursework.catalog.filters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.R;

import java.util.List;
import java.util.Map;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>{
    private List<ExpandableItem> filters;

    public FilterAdapter(List<ExpandableItem> filters) {
        this.filters = filters;
    }

    @NonNull
    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.ViewHolder holder, int position) {
        holder.bind(filters.get(position));
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private RecyclerView subItemsRecyclerView;
        private final LinearLayout subItemsContainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            subItemsRecyclerView = itemView.findViewById(R.id.sub_items_recycler);
            subItemsContainer = itemView.findViewById(R.id.expandable_layout);
            subItemsRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
        }

        public void bind(ExpandableItem item) {
            titleView.setText(item.getTitle());

            subItemsContainer.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);
            itemView.setOnClickListener(v -> {
                item.setExpanded(!item.isExpanded());
                toggleExpansion(item.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
            AttributesAdapter adapter = new AttributesAdapter(item.getTitle(),item.getSubItems());
            subItemsRecyclerView.setAdapter(adapter);
        }
        private void toggleExpansion(boolean isExpanded) {
            if (isExpanded) {
                subItemsContainer.setVisibility(View.VISIBLE);
            } else {
                subItemsContainer.setVisibility(View.GONE);
            }
        }
    }
}

