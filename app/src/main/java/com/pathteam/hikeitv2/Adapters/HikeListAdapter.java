package com.pathteam.hikeitv2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Stages.HikeDetailStage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

import static com.pathteam.hikeitv2.MainActivity.position;

/**
 * Created by JoshuaMabry on 11/17/16.
 */

public class HikeListAdapter extends RecyclerView.Adapter<HikeListAdapter.HikeHolder> {

    public List<HikeList> hikelist;
    private Context context;

    public HikeListAdapter(List<HikeList> hikelist, Context context) {
        this.hikelist = hikelist;
        this.context = context;
    }

    @Override
    public HikeListAdapter.HikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.hike_list_item_view, parent, false);
        return new HikeListAdapter.HikeHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(HikeListAdapter.HikeHolder holder, int position) {

        HikeList hikes = hikelist.get(position);

        if (position % 2 == 0 || position == 0){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else{
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }

        holder.bindHikes(hikes);
    }

    @Override
    public int getItemCount() {
        return hikelist.size();
    }
// Class binds the information to the hike item in the list view
    class HikeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.hike_notes)
        TextView hikeNotes;

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.date)
        TextView date;

        public HikeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        //        Lets put our data in our UI
        public void bindHikes(final HikeList hikes) {
            hikeNotes.setText(hikes.getHikeNotes());
            title.setText(hikes.getTitle());
            date.setText(hikes.hmarker.get(0).getDate().toString());
        }

        //On Click for List Hike View items pushed the view over to the detail view
        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            Flow flow = HikeApplication.getMainFlow();
            History newHistory = flow.getHistory().buildUpon()
//                        gets the information injected
                    .push(new HikeDetailStage())
                    .build();
            flow.setHistory(newHistory, Flow.Direction.FORWARD);
        }
    }
}