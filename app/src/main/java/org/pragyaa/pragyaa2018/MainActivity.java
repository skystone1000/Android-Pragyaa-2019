package org.pragyaa.pragyaa2018;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import org.pragyaa.pragyaa2018.notifications.Config;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.contants.Align;


public class MainActivity extends AppCompatActivity {

    private static boolean flag=false;

    private AppBarLayout appBar;

    private Toolbar toolbar;
    private ShimmerTextView robotics,special, gaming, online,codingEvent,taskingEve,texplorer,cenfest,paperpresentation,quiz,desigining , exhibition;
    private Typeface fireFont;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Fab fab;
    private View sheetView, overlay;
    private MaterialSheetFab materialSheetFab;
    private DatabaseReference urlData;
    private String url;
    private View v;

    private ImageView roboticsImg,specialImg,onlineImg,gamingImg,taskingImg,codingImg,desiginingImg,quizImg,cenfestImg,textplorerImg,paperImg , exhibitionImg;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        //binding id's
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appbar);

        CollapsingToolbarLayout collapse = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);

        Typeface batman = Typeface.createFromAsset(getAssets(), "fonts/batmanforeveralternate.ttf");
        collapse.setExpandedTitleColor(Color.TRANSPARENT);
        collapse.setCollapsedTitleTextColor(Color.WHITE);
        collapse.setCollapsedTitleTypeface(batman);


        robotics = (ShimmerTextView) findViewById(R.id.robotics);
        special = (ShimmerTextView) findViewById(R.id.special_event);
        online = (ShimmerTextView) findViewById(R.id.online_eve);
        gaming = (ShimmerTextView) findViewById(R.id.gaming);
        codingEvent = (ShimmerTextView)findViewById(R.id.coding_eve);
        taskingEve = (ShimmerTextView)findViewById(R.id.tasking_eve);
        texplorer = (ShimmerTextView)findViewById(R.id.textplorer_eve);
        cenfest = (ShimmerTextView)findViewById(R.id.cenfest_eve);
        paperpresentation = (ShimmerTextView)findViewById(R.id.paperpresentation_eve);
        quiz = (ShimmerTextView)findViewById(R.id.quiz_eve);
        desigining = (ShimmerTextView)findViewById(R.id.designing_eve);
        exhibition = (ShimmerTextView)findViewById(R.id.exhibition_eve);


        roboticsImg = (ImageView)findViewById(R.id.robotics_img);
        specialImg = (ImageView)findViewById(R.id.special_img);
        gamingImg = (ImageView)findViewById(R.id.gaming_img);
        onlineImg = (ImageView)findViewById(R.id.online_img);
        codingImg = (ImageView)findViewById(R.id.coding_img);
        taskingImg = (ImageView)findViewById(R.id.tasking_img);
        quizImg = (ImageView)findViewById(R.id.quiz_img);
        desiginingImg = (ImageView)findViewById(R.id.designing_img);
        cenfestImg = (ImageView)findViewById(R.id.cenfest_img);
        textplorerImg = (ImageView)findViewById(R.id.textplorer_img);
        paperImg = (ImageView)findViewById(R.id.paper_img);
        exhibitionImg = (ImageView)findViewById(R.id.exhibition_img);


        fab = (Fab) findViewById(R.id.fab);
        sheetView = findViewById(R.id.fab_sheet);
        overlay = findViewById(R.id.overlay);

        //end binding id's
        //http://bestanimations.com/Earth&Space/Sun/sunset-animation-2.gif
        //http://68.media.tumblr.com/ca513146790e0e22a555ef11d9c40be1/tumblr_mpagdiWFpo1sr77jco1_500.gif

        //https://media.giphy.com/media/3KqZp8MBaf1Ty/giphy.gif
        //"http://bestanimations.com/Nature/Fire/fire-animated-gif-10.gif"


        loadImages();

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



        //header texSurfaceView
        final Typeface batmanForever = Typeface.createFromAsset(getAssets(), "fonts/batmanforeveralternate.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(batmanForever);

        Text textMain = TextBuilder
                .create("SGGSIE&T,Nanded ")
                .setPaint(paint)
                .setSize(25)
                .setAlpha(0)
                .setColor(Color.parseColor("#9C27B0"))
                .setPosition(Align.SURFACE_CENTER).build();

        Text textSub = TextBuilder
                .create("Presents...")
                .setPaint(paint)
                .setSize(15)
                .setAlpha(0)
                .setColor(Color.YELLOW)
                .setPosition(Align.BOTTOM_OF, textMain).build();

        Text textDesc = TextBuilder
                .create("National-level Technical Fiesta")
                .setPaint(paint)
                .setSize(13)
                .setAlpha(0)
                .setColor(Color.MAGENTA)
                .setPosition(Align.RIGHT_OF, textSub).build();

        Text textP = TextBuilder
                .create("PRAGYAA 2019")
                .setPaint(paint)
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.parseColor("#d50000"))
                .setPadding(0, 0, 0, 12)
                .setPosition(Align.BOTTOM_OF, textDesc).build();




        //animating below layout with Shimmer
        Shimmer shimmer = new Shimmer();
        shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.setDuration(2000);
        Shimmer shimmer1 = new Shimmer();
        shimmer1.setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer1.setDuration(3000);
        fireFont = Typeface.createFromAsset(getAssets(), "fonts/batmanforeveralternate.ttf");

        robotics.setTypeface(fireFont);
        shimmer.start(robotics);

        special.setTypeface(fireFont);
        shimmer1.start(special);

        online.setTypeface(fireFont);
        shimmer.start(online);

        gaming.setTypeface(fireFont);
        shimmer1.start(gaming);


        codingEvent.setTypeface(fireFont);
        shimmer.start(codingEvent);

        taskingEve.setTypeface(fireFont);
        shimmer1.start(taskingEve);

        quiz.setTypeface(fireFont);
        shimmer.start(quiz);

        desigining.setTypeface(fireFont);
        shimmer1.start(desigining);

        cenfest.setTypeface(fireFont);
        shimmer1.start(cenfest);

        texplorer.setTypeface(fireFont);
        shimmer1.start(texplorer);

        paperpresentation.setTypeface(fireFont);
        shimmer1.start(paperpresentation);

        exhibition.setTypeface(fireFont);
        shimmer1.start(exhibition);


        //setting click listeners to textViews

        //robotics
        robotics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deptEvent = new Intent(MainActivity.this, ShowEventsActivity.class);
                deptEvent.putExtra("event", 1);
                startActivity(deptEvent);
            }
        });

        //special events
        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specialEvent = new Intent(MainActivity.this, ShowEventsActivity.class);
                specialEvent.putExtra("event", 3);
                startActivity(specialEvent);
            }
        });

        //gaming zone
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gaming = new Intent(MainActivity.this, ShowEventsActivity.class);
                gaming.putExtra("event", 4);
                startActivity(gaming);
            }
        });

        //online events
        codingEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onlineEve = new Intent(MainActivity.this, ShowEventsActivity.class);
                onlineEve.putExtra("event", 5);
                startActivity(onlineEve);
            }
        });

        //Coding event (check)
        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roboEvent = new Intent(MainActivity.this, ShowEventsActivity.class);
                roboEvent.putExtra("event", 2);
                startActivity(roboEvent);
            }
        });

        //tasking events
        taskingEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tasking = new Intent(MainActivity.this,ShowEventsActivity.class);
                tasking.putExtra("event",6);
                startActivity(tasking);
            }
        });

        //quiz events
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quiz = new Intent(MainActivity.this,ShowEventsActivity.class);
                quiz.putExtra("event",7);
                startActivity(quiz);
            }
        });

        //designing
        desigining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent design = new Intent(MainActivity.this,ShowEventsActivity.class);
                design.putExtra("event",8);
                startActivity(design);
            }
        });

        //cenfest
        cenfest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cenfest = new Intent(MainActivity.this,ShowEventsActivity.class);
                cenfest.putExtra("event",9);
                startActivity(cenfest);
            }
        });

        //textplorer
        texplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textplorer = new Intent(MainActivity.this,ShowEventsActivity.class);
                textplorer.putExtra("event",10);
                startActivity(textplorer);
            }
        });

        //paperpresentation
        paperpresentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paperpresentation = new Intent(MainActivity.this,ShowEventsActivity.class);
                paperpresentation.putExtra("event",11);
                startActivity(paperpresentation);
            }
        });

        //Exhibition
        exhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exhibition = new Intent(MainActivity.this,ShowEventsActivity.class);
                exhibition.putExtra("event",13);
                startActivity(exhibition);
            }
        });

        //bottom fab sheet
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.colorAccent);
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onHideSheet() {
                //statusBarColor = getStatusBarColor();
                //setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
            }

            @Override
            public void onShowSheet() {
                //setStatusBarColor(statusBarColor);
            }
        });



        //setting the listeners in sheet
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                }else {
                    Snackbar.make(v,"Coming Soon...",Snackbar.LENGTH_LONG);
                }
            }
        });

        findViewById(R.id.schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutUs.class));
            }
        });

        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contact = new Intent(MainActivity.this, ShowEventsActivity.class);
                contact.putExtra("event", 12);
                startActivity(contact);
            }
        });

        findViewById(R.id.hospitality).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Hospitality.class));
            }
        });


        //end bottom fab sheet

        //all about notification receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                }
            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!flag){

            new SpotlightView.Builder(MainActivity.this)
                    .introAnimationDuration(400)
                    .enableRevalAnimation(true)
                    .performClick(true)
                    .fadeinTextDuration(400)
                    .headingTvColor(Color.parseColor("#eb273f"))
                    .headingTvSize(32)
                    .headingTvText("Events")
                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                    .subHeadingTvSize(16)
                    .subHeadingTvText("Click for more!")
                    .maskColor(Color.parseColor("#dc000000"))
                    .target(robotics)
                    .lineAnimDuration(400)
                    .lineAndArcColor(Color.parseColor("#e67e22"))
                    .dismissOnTouch(true)
                    .dismissOnBackPress(true)
                    .enableDismissAfterShown(true)
                    .usageId("category_text")
                    .setListener(new SpotlightListener() {
                        @Override
                        public void onUserClicked(String s) {
                            new SpotlightView.Builder(MainActivity.this)
                                    .introAnimationDuration(400)
                                    .enableRevalAnimation(true)
                                    .performClick(true)
                                    .fadeinTextDuration(400)
                                    .headingTvColor(Color.parseColor("#eb273f"))
                                    .headingTvSize(32)
                                    .headingTvText("MORE")
                                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                                    .subHeadingTvSize(16)
                                    .subHeadingTvText("Click here!")
                                    .maskColor(Color.parseColor("#dc000000"))
                                    .target(fab)
                                    .lineAnimDuration(400)
                                    .lineAndArcColor(Color.parseColor("#e67e22"))
                                    .dismissOnTouch(true)
                                    .dismissOnBackPress(true)
                                    .enableDismissAfterShown(true)
                                    .usageId("bottom_fab")
                                    .show();
                        }
                    })
                    .show();
            flag = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        // NotificationUtils.clearNotifications(getApplicationContext());


    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        }else {
            super.onBackPressed();
        }
    }


    public void loadImages(){
        String robotics_url = "https://www.wadsworthmagnetpta.com/wp-content/uploads/2017/09/619048_orig.jpg";
        String special_url = "http://cdn.clubzone.com/content/uploads/2014/11/Special.jpg";
        String gaming_url = "http://tubularinsights.com/wp-content/uploads/2015/11/gaming-on-youtube.jpg";
        String online_url = "http://www.shubhambhardwaj.com/wp-content/uploads/2016/08/Social-Media-For-Your-Business.jpg";
        String coding_url = "https://think360studio.com/wp-content/uploads/2017/12/Learn-coding-online.jpeg";
        String tasking_url = "https://d1nzpkv5wwh1xf.cloudfront.net/640/k-57b67684047c99584fc4a66e/20170817-/linhnd402.png";
        String quiz_url = "http://home.bt.com/images/test-your-knowledge-of-2016-with-our-jumbo-trivia-quiz-136412309722203901-161222142527.jpg ";
        String desigining_url = "http://www.ward-howell.com/assets/Uploads/IndustrialHeader.jpg";
        String cenfest_url = "http://civilengineeringbasic.com/wp-content/uploads/2014/07/1-1728x800_c.jpg";
        String textplorer_url = "http://ofsbrandssitesbucket.s3.amazonaws.com/s3fs-public/textile_sample_01.jpg";
        String paperpresentation_url = "http://www.pinnacleceg.org/assets/thumbnail/event-detail/event_1486818952.jpg";
        String exhibition_url ="https://img.etimg.com/thumb/msid-66083314,width-300,imgsize-654183,resizemode-4/amu-site.jpg";
        Picasso.with(this).load(robotics_url).into(roboticsImg);
        Picasso.with(this).load(special_url).into(specialImg);
        Picasso.with(this).load(gaming_url).into(gamingImg);
        Picasso.with(this).load(online_url).into(onlineImg);
        Picasso.with(this).load(coding_url).into(codingImg);
        Picasso.with(this).load(tasking_url).into(taskingImg);
        Picasso.with(this).load(quiz_url).into(quizImg);
        Picasso.with(this).load(desigining_url).into(desiginingImg);
        Picasso.with(this).load(cenfest_url).into(cenfestImg);
        Picasso.with(this).load(textplorer_url).into(textplorerImg);
        Picasso.with(this).load(paperpresentation_url).into(paperImg);
        Picasso.with(this).load(exhibition_url).into(exhibitionImg);

    }



    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
