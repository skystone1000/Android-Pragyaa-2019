package org.pragyaa.pragyaa2018;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.pragyaa.pragyaa2018.fragments.Coding;
import org.pragyaa.pragyaa2018.fragments.CommitteesFragment;
import org.pragyaa.pragyaa2018.fragments.Exhibition;
import org.pragyaa.pragyaa2018.fragments.GamingZoneFragment;
import org.pragyaa.pragyaa2018.fragments.OnlineEventsFragment;

import org.pragyaa.pragyaa2018.fragments.Paperpresentation;
import org.pragyaa.pragyaa2018.fragments.RoboticsFragment;
import org.pragyaa.pragyaa2018.fragments.SpecialEventsFragment;
import org.pragyaa.pragyaa2018.fragments.Tasking;
import org.pragyaa.pragyaa2018.fragments.Quiz;
import org.pragyaa.pragyaa2018.fragments.Cenfest;
import org.pragyaa.pragyaa2018.fragments.Desigining;
import org.pragyaa.pragyaa2018.fragments.Textplorer;


public class ShowEventsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private int position;
    private CoordinatorLayout layout;
    private FloatingActionButton fab;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.dept_fab);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        layout = (CoordinatorLayout)findViewById(R.id.activity_show_events);

        position = getIntent().getIntExtra("event", 0);


        switch (position) {

            case 1:
                toolbar.setTitle("ROBOTICS");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new RoboticsFragment())
                        .commit();
                break;

            case 2:
                toolbar.setTitle("CREATIVE EVENTS");          // SPECIAL EVENTS
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SpecialEventsFragment())
                        .commit();
                break;

            case 3:
                toolbar.setTitle("GAMING ZONE");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new GamingZoneFragment())
                        .commit();
                break;

            case 4:
                toolbar.setTitle("ONLINE EVENTS");                //ONLINE EVENTS & EXHIBITION
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new OnlineEventsFragment())
                        .commit();
                break;

            case 5:
                toolbar.setTitle("CODESTER");    ///CODING EVENT
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Coding())
                        .commit();
                break;

            case 6:
                toolbar.setTitle("CHEMTREK");             //TASKING EVENT
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Tasking())
                        .commit();
                break;

            case 7:
                toolbar.setTitle("CONFERENCE");          //QUIZ EVENT
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Quiz())
                        .commit();
                break;

            case 8:
                toolbar.setTitle("MECHATRON");            //DESIGNING EVENT
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Desigining())
                        .commit();
                break;

            case 9:
                toolbar.setTitle("CENFEST");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Cenfest())
                        .commit();
                break;

            case 10:
                toolbar.setTitle("TEXTPLORER");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Textplorer())
                        .commit();
                break;

            case 11:
                toolbar.setTitle("ELECHROMA");       //PAPER PRESENTATION
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Paperpresentation())
                        .commit();
                break;

            case 12:
                toolbar.setTitle("COMMITTEES");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new CommitteesFragment())
                        .commit();
                break;

            case 13:
                toolbar.setTitle("EXHIBITION");
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new Exhibition())
                        .commit();
                break;


        }



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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowEventsActivity.this,InfoDeptActivity.class));
            }
        });

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
