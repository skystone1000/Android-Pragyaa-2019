package org.pragyaa.pragyaa2018;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoDeptActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseDept;
    private RecyclerView mDeptView;
    private CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dept);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = (CoordinatorLayout)findViewById(R.id.info_dept_layout);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDatabaseDept = FirebaseDatabase.getInstance().getReference().child("dept_info");
        mDatabaseDept.keepSynced(true);
        mDeptView = (RecyclerView)findViewById(R.id.event_recycler_view);


        FirebaseRecyclerAdapter<InfoDept,DeptHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<InfoDept, DeptHolder>(
                InfoDept.class,
                R.layout.item_dept_main,
                DeptHolder.class,
                mDatabaseDept
        )

        {
            @Override
            protected void populateViewHolder(DeptHolder viewHolder, InfoDept model, int position) {
                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setMain(model.getMain());
                viewHolder.setJoint(model.getJoint());

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDeptView.setHasFixedSize(true);
        mDeptView.setLayoutManager(layoutManager);
        mDeptView.setAdapter(firebaseRecyclerAdapter);


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
    }


    public static class DeptHolder extends RecyclerView.ViewHolder{
        TextView titleEvent;
        TextView mainCordi;
        TextView jointCordi;
        View mView;

        public DeptHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            titleEvent = (TextView)mView.findViewById(R.id.event_name);
            titleEvent.setText(title);
        }
        public void  setMain(String main){
            mainCordi = (TextView)mView.findViewById(R.id.main_dept_cordi);
            mainCordi.setText(main);
        }
        public void setJoint(String joint){
            jointCordi = (TextView)mView.findViewById(R.id.joint_dept_cordi);
            jointCordi.setText(joint);
        }
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

}
