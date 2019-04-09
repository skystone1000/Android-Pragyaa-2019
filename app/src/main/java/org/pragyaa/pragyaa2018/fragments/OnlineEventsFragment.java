package org.pragyaa.pragyaa2018.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pragyaa.pragyaa2018.EventDetailsActivity;
import org.pragyaa.pragyaa2018.R;



public class OnlineEventsFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        OnlineEventsFragment.ContentAdapter adapter = new OnlineEventsFragment.ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.vsm_keyLines);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_event, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra("image",ContentAdapter.images[getAdapterPosition()]);
                    intent.putExtra("event_category","online_eve");
                    intent.putExtra(EventDetailsActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;
        private ProgressDialog mProgress;

        //4 images
        private static final String[] images = {
                "http://assets4.bigthink.com/system/idea_thumbnails/44964/original/thinking.jpg",
                "http://www.qiup.edu.my/wp-content/uploads/Biomedical-Sciences-Picture.jpg",
                "https://static.bhphotovideo.com/explora/sites/default/files/styles/top_shot/public/TS-Night-Photography.jpg?itok=VO6dxBNS",
                "https://cdn-images-1.medium.com/max/2000/1*lyQtsRrV2U5Rii583TzvHQ.jpeg",
                "https://media.licdn.com/dms/image/C510BAQFrjSwufuDv0A/company-logo_200_200/0?e=2159024400&v=beta&t=fqkcgYhXcOEVfwaEZRR8SaexYE4JMoe93AYiCM7GA3g",
        };



        private final String[] eventName;

        public ContentAdapter(Context context) {

            mContext = context;
            Resources resources = context.getResources();
            eventName = resources.getStringArray(R.array.online_eve);
            mProgress = new ProgressDialog(mContext);
            mProgress.setMessage("Loading...");


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OnlineEventsFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            mProgress.show();
            Picasso.with(mContext).load(images[position]).into(holder.picture);
            holder.name.setText(eventName[position % eventName.length]);
            mProgress.dismiss();

        }

        @Override
        public int getItemCount() {
            return images.length;
        }
    }
}