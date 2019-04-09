package org.pragyaa.pragyaa2018;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import static org.pragyaa.pragyaa2018.R.id.rule;


public class EventDetailsActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static boolean flag = false;
    FABRevealLayout fabRevealLayout;
    private TextView coordiOne;
    private TextView coordiTwo;
    private TextView mobiOne;
    private TextView mobiTwo;
    private TextView closeBtn;
    private HTextView registerBtn, heading;
    private FloatingActionButton fab;
    private NestedScrollView scrolling;

    private TextView infoEvent;

    private static String eventCategory;
    private static int position;
    private String imageLink;
    private View view;


    private Toolbar mToolbar;
    private FloatingActionButton invisibleFab;

    private ImageView eventImage;
    private CoordinatorLayout layout;

    public static final String EXTRA_POSITION = "position";
    private BottomBar bottomBar;
    private ProgressDialog mProgress;

    private DatabaseReference mEventDatabase;

    private String rules, desc, play, specs, coordinatorOne, coordinatorTwo, mobileone, mobiletwo, eventName, imgUrl;
    private DatabaseReference urlData;
    private String url;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, EventDetailsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
        mProgress.show();

        try {
            imageLink = getIntent().getExtras().getString("image");
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            eventCategory = getIntent().getExtras().getString("event_category");
        } catch (Exception e) {

            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
        }

        layout = (CoordinatorLayout) findViewById(R.id.activity_event_details);
        fabRevealLayout = (FABRevealLayout) findViewById(R.id.fab_reveal_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab_btn);
        invisibleFab = (FloatingActionButton) findViewById(R.id.invisible_fab);
        scrolling = (NestedScrollView) findViewById(R.id.scroll);
        coordiOne = (TextView) findViewById(R.id.coordi_one);
        coordiTwo = (TextView) findViewById(R.id.coordi_two);
        mobiOne = (TextView) findViewById(R.id.mobi_one);
        mobiTwo = (TextView) findViewById(R.id.mobi_two);
        registerBtn = (HTextView) findViewById(R.id.register);
        eventImage = (ImageView) findViewById(R.id.event_image);

        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        infoEvent = (TextView) findViewById(R.id.info);
        heading = (HTextView) findViewById(R.id.heading);

        final CollapsingToolbarLayout collapse = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        //getting url data for registration
        urlData = FirebaseDatabase.getInstance().getReference().child("registrationlink");
        urlData.keepSynced(true);
        urlData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //getting event details
        mEventDatabase = FirebaseDatabase.getInstance().getReference().child("event_details");
        mEventDatabase.keepSynced(true);
        mEventDatabase.child(eventCategory).child(String.valueOf(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventName = (String) dataSnapshot.child("event_name").getValue(String.class);
                rules = (String) dataSnapshot.child("rules").getValue(String.class);
                desc = (String) dataSnapshot.child("desc").getValue(String.class);
                play = (String) dataSnapshot.child("play").getValue(String.class);
                specs = (String) dataSnapshot.child("specs").getValue(String.class);
                coordinatorOne = (String) dataSnapshot.child("coordi_one").getValue(String.class);
                mobileone = (String) dataSnapshot.child("mobile_one").getValue(String.class);
                coordinatorTwo = (String) dataSnapshot.child("coordi_two").getValue(String.class);
                mobiletwo = (String) dataSnapshot.child("mobile_two").getValue(String.class);
                imgUrl = (String) dataSnapshot.child("link").getValue(String.class);

                collapse.setTitle(eventName);
                if (nullDatabase(desc)) {
                    infoEvent.setText(desc);
                } else {
                    infoEvent.setText("Coming soon...");
                }
                mProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //end getting event details

        //Reveal layout
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabRevealLayout.revealSecondaryView();
            }
        });

        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(final FABRevealLayout fabRevealLayout, View mainView) {

                scrolling.setVisibility(View.VISIBLE);
                fab.setImageResource(android.R.drawable.ic_menu_call);

                invisibleFab.setVisibility(View.INVISIBLE);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fabRevealLayout.revealSecondaryView();
                    }
                });

            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {

                if (nullDatabase(coordinatorOne)) {
                    coordiOne.setText(coordinatorOne);
                } else {
                    coordiOne.setText("Coming soon...");
                }

                if (nullDatabase(coordinatorTwo)) {
                    coordiTwo.setText(coordinatorTwo);
                } else {
                    coordiTwo.setText("Coming soon...");
                }

                if (nullDatabase(mobileone)) {
                    mobiOne.setText(mobileone);

                } else {
                    mobiOne.setText("Coming soon...");

                }
                if (nullDatabase(mobiletwo)) {
                    mobiTwo.setText(mobiletwo);
                } else {
                    mobiTwo.setText("Coming soon...");
                }

                mobiOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mobileone));
                        startActivity(intent);
                    }
                });

                mobiTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mobiletwo));
                        startActivity(intent);
                    }
                });
                registerBtn.animateText("REGISTER");
                registerBtn.setAnimateType(HTextViewType.TYPER);
                registerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (url != null) {
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(EventDetailsActivity.this, Uri.parse(url));
                        } else {
                            Toast.makeText(EventDetailsActivity.this, "Coming soon...", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                scrolling.setVisibility(View.INVISIBLE);
                invisibleFab.setVisibility(View.INVISIBLE);

                fab.setImageResource(R.drawable.ic_clear);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fabRevealLayout.revealMainView();
                    }
                });

            }
        });

        //end Reveal layout

        //showing arena if any
        findViewById(R.id.specs_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(EventDetailsActivity.this);
                ImageView iv = new ImageView(EventDetailsActivity.this);
                Picasso.with(EventDetailsActivity.this).load(imgUrl).into(iv);
                iv.requestLayout();
                ad.setView(iv);
                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ad.show();
            }
        });

        Picasso.with(EventDetailsActivity.this).load(imageLink).into(eventImage);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.desc) {
                    heading.animateText("Description");
                    heading.setAnimateType(HTextViewType.TYPER);
                    findViewById(R.id.specs_img).setVisibility(View.INVISIBLE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        infoEvent.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    if (nullDatabase(desc)) {
                        infoEvent.setText(desc);
                    } else {
                        infoEvent.setText("Coming soon...");
                    }
                } else if (tabId == rule) {
                    heading.animateText("Rules");
                    heading.setAnimateType(HTextViewType.TYPER);
                    findViewById(R.id.specs_img).setVisibility(View.INVISIBLE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        infoEvent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    }

                    String rule1 = rules.replace("..","\n");
                    String rule = rule1.replace(":-","\n");

                    if (nullDatabase(rules)) {

                        infoEvent.setText(rule);

                    } else {
                        infoEvent.setText("Coming soon...");
                    }
                } else if (tabId == R.id.play) {
                    heading.animateText("Game Play");
                    heading.setAnimateType(HTextViewType.TYPER);
                    findViewById(R.id.specs_img).setVisibility(View.INVISIBLE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        infoEvent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    }

                    String playActual1 = play.replace(":-","\n");
                    String playActual = playActual1.replace("..","\n");

                    if (nullDatabase(play)) {
                        infoEvent.setText(playActual);
                    } else {
                        infoEvent.setText("Coming soon...");
                    }
                } else if (tabId == R.id.specs) {
                    if ((imgUrl.equals("")) || (imgUrl == null)) {
                        findViewById(R.id.specs_img).setVisibility(View.INVISIBLE);
                    } else {
                        findViewById(R.id.specs_img).setVisibility(View.VISIBLE);
                    }
                    heading.animateText("Specifications");
                    heading.setAnimateType(HTextViewType.TYPER);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        infoEvent.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    }

                    String specsText = specs.replace("..","\n");

                    if (nullDatabase(specs)) {
                        infoEvent.setText(specsText);
                    } else {
                        infoEvent.setText("Coming soon...");
                    }
                }

            }
        });


        if (!haveNetworkConnection()) {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show();
            infoEvent.setText("Please Check Your Network Connection!");
        }
    }

    public boolean nullDatabase(String info) {
        if (info == null) {
            return false;
        } else {
            return true;
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

    @Override
    protected void onStart() {
        super.onStart();

        if (flag == false) {
            //first time calling activity
            new SpotlightView.Builder(this)
                    .introAnimationDuration(400)
                    .enableRevalAnimation(true)
                    .performClick(true)
                    .fadeinTextDuration(400)
                    .headingTvColor(Color.parseColor("#eb273f"))
                    .headingTvSize(32)
                    .headingTvText("Contact")
                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                    .subHeadingTvSize(16)
                    .subHeadingTvText("Contacts of respective Event-Coordinators")
                    .maskColor(Color.parseColor("#dc000000"))
                    .target(fab)
                    .lineAnimDuration(400)
                    .lineAndArcColor(Color.parseColor("#e67e22"))
                    .dismissOnTouch(true)
                    .dismissOnBackPress(true)
                    .enableDismissAfterShown(true)
                    .usageId("call_fab")
                    .setListener(new SpotlightListener() {
                        @Override
                        public void onUserClicked(String s) {
                            new SpotlightView.Builder(EventDetailsActivity.this)
                                    .introAnimationDuration(400)
                                    .enableRevalAnimation(true)
                                    .performClick(true)
                                    .fadeinTextDuration(400)
                                    .headingTvColor(Color.parseColor("#eb273f"))
                                    .headingTvSize(32)
                                    .headingTvText("Menu")
                                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                                    .subHeadingTvSize(16)
                                    .subHeadingTvText("Information of an event")
                                    .maskColor(Color.parseColor("#dc000000"))
                                    .target(bottomBar)
                                    .lineAnimDuration(400)
                                    .lineAndArcColor(Color.parseColor("#e67e22"))
                                    .dismissOnTouch(true)
                                    .dismissOnBackPress(true)
                                    .enableDismissAfterShown(true)
                                    .usageId("bottom_bar")
                                    .show();
                        }
                    })
                    .show();
            flag = true;
        }

    }
}
