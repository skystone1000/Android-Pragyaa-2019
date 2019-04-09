package org.pragyaa.pragyaa2018;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wooplr.spotlight.SpotlightView;

public class ScheduleActivity extends AppCompatActivity {

    private static boolean flag=false;
    private DatabaseReference mDatabase;
    private RecyclerView eventslistView;
    private SwipeRefreshLayout swipeLayout;
    private TextView activityFeed;

    private CoordinatorLayout layout;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        eventslistView = (RecyclerView) findViewById(R.id.event_schedules);
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);

        activityFeed = (TextView)findViewById(R.id.activity_feed);
        layout = (CoordinatorLayout)findViewById(R.id.schedule_events);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("event_uploads");
        mDatabase.keepSynced(true);


        if (!haveNetworkConnection()){
            final Snackbar snackbar = Snackbar
                    .make(layout, "No internet connection!", 6000)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }


        swipeLayout.setColorSchemeResources(R.color.blue,R.color.red,R.color.green,R.color.yellow,R.color.orange);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });


        FirebaseRecyclerAdapter<ScheduledEvents, EventHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ScheduledEvents, EventHolder>(
                ScheduledEvents.class,
                R.layout.item_upcoming_event,
                EventHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(EventHolder viewHolder, ScheduledEvents model, int position) {

                final String post_key = getRef(position).getKey();

                mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        url = (String) dataSnapshot.child("url").getValue();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (url!=null){
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            CustomTabsIntent customTabsIntent = builder.build();
                            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                            customTabsIntent.launchUrl(ScheduleActivity.this, Uri.parse(url));
                        }

                    }
                });


            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        eventslistView.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        eventslistView.setAdapter(firebaseRecyclerAdapter);
        eventslistView.setLayoutManager(layoutManager);

    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static class EventHolder extends RecyclerView.ViewHolder {

        TextView eventDesc;
        TextView eventTitle;
        View mView;

        public EventHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDesc(String desc) {

            eventDesc = (TextView) mView.findViewById(R.id.card_text);
            eventDesc.setText(desc);
        }

        public void setTitle(String title) {
            eventTitle = (TextView) mView.findViewById(R.id.card_title);
            eventTitle.setText(title);

        }

        public void setImage(final Context ctx, final String image) {
            final ImageView eventImage = (ImageView) mView.findViewById(R.id.card_image);

            if (image != null) {
                Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(eventImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(ctx).load(image).into(eventImage);
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!flag){
            new SpotlightView.Builder(this)
                    .introAnimationDuration(400)
                    .enableRevalAnimation(true)
                    .performClick(true)
                    .fadeinTextDuration(400)
                    .headingTvColor(Color.parseColor("#eb273f"))
                    .headingTvSize(32)
                    .headingTvText("TIMELINE")
                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                    .subHeadingTvSize(16)
                    .subHeadingTvText("Schedule & upcoming events will be posted &\n You will be notified")
                    .maskColor(Color.parseColor("#dc000000"))
                    .target(activityFeed)
                    .lineAnimDuration(400)
                    .lineAndArcColor(Color.parseColor("#e67e22"))
                    .dismissOnTouch(true)
                    .dismissOnBackPress(true)
                    .enableDismissAfterShown(true)
                    .usageId("activity_feed")
                    .show();

            flag = true;
        }
    }
}
