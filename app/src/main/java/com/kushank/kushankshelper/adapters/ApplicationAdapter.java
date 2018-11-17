package com.kushank.kushankshelper.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kushank.kushankshelper.R;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Pair<String, Drawable>> applicationItems;

    public ApplicationAdapter(
            List<Pair<String, Drawable>> applicationItems, OnItemClickListener onItemClickListener
    ) {
        this.applicationItems = applicationItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View appView = inflater.inflate(R.layout.app_list_item, parent, false);
        return new ApplicationViewHolder(appView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ApplicationViewHolder viewHolder = (ApplicationViewHolder) holder;
        viewHolder.appName.setText(applicationItems.get(position).first);
        viewHolder.appIcon.setImageDrawable(applicationItems.get(position).second);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return (null != applicationItems ? applicationItems.size() : 0);
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        ImageView appIcon;

        public ApplicationViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            this.appName = itemView.findViewById(R.id.tvAppName);
            this.appIcon = itemView.findViewById(R.id.ivIconApp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(appName.getText().toString());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String appName);
    }
}
