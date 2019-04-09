package org.pragyaa.pragyaa2018.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pragyaa.pragyaa2018.R;



public class CommitteesFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameOfComit;
        String main = null, joint1 = null,joint2 = null,joint3 = null,joint4 = null,joint5 = null, chiefMain = null, chiefJoint = null;

        public ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_contact, parent, false));
            nameOfComit = (TextView) itemView.findViewById(R.id.name_comit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    Resources contacts = context.getResources();

                    String[] mainCordiNames = contacts.getStringArray(R.array.mainCoordis);
                    String[] jointOne = contacts.getStringArray(R.array.jointOne);
                    String[] jointTwo = contacts.getStringArray(R.array.jointTwo);
                    String[] jointThree = contacts.getStringArray(R.array.jointThree);
                    String[] jointFour = contacts.getStringArray(R.array.jointFour);
                    String[] jointFive= contacts.getStringArray(R.array.jointFive);

                    final String[] chiefCordiMain = contacts.getStringArray(R.array.chiefCordisMain);
                    final String[] chiefCordisJoint = contacts.getStringArray(R.array.chiefCordisJoint);

                    if (getAdapterPosition() > 1) {
                        main = mainCordiNames[getAdapterPosition() - 2];
                        joint1 = jointOne[getAdapterPosition() - 2];
                        joint2 = jointTwo[getAdapterPosition() - 2];
                        joint3 = jointThree[getAdapterPosition() - 2];
                        joint4 = jointFour[getAdapterPosition() - 2];
                        joint5 = jointFive[getAdapterPosition() - 2];
                    }

                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    View content = inflater.inflate(R.layout.contact_alert, null);
                    ad.setView(content);

                    TextView mainCordi = (TextView) content.findViewById(R.id.main_cordi);
                    TextView jointCordiOne = (TextView) content.findViewById(R.id.joint_one);
                    TextView jointCordiTwo = (TextView) content.findViewById(R.id.joint_two);
                    TextView jointCordiThree = (TextView) content.findViewById(R.id.joint_three);
                    TextView jointCordiFour = (TextView) content.findViewById(R.id.joint_four);
                    TextView jointCordiFive = (TextView) content.findViewById(R.id.joint_five);
                    TextView headChief = (TextView) content.findViewById(R.id.heading_chief);
                    TextView headSub = (TextView) content.findViewById(R.id.heading_sub);


                    if (getAdapterPosition() == 0) {
                        // ad.setMessage(chief);
                        mainCordi.setVisibility(View.VISIBLE);
                        jointCordiOne.setVisibility(View.VISIBLE);
                        mainCordi.setText(chiefCordiMain[0]);
                        jointCordiOne.setText(chiefCordisJoint[0]);

                    } else if (getAdapterPosition() == 1) {
                        mainCordi.setVisibility(View.VISIBLE);
                        jointCordiOne.setVisibility(View.VISIBLE);
                        mainCordi.setText(chiefCordiMain[1]);
                        jointCordiOne.setText(chiefCordisJoint[1]);

                    } else {
                        //ad.setMessage("Main Co-Ordinator:\n"+main+"\n"+"Joint Co-Ordinators:\n"+joint);
                        headChief.setVisibility(View.VISIBLE);
                        headSub.setVisibility(View.VISIBLE);
                        mainCordi.setVisibility(View.VISIBLE);
                        jointCordiOne.setVisibility(View.VISIBLE);
                        jointCordiTwo.setVisibility(View.VISIBLE);
                        jointCordiThree.setVisibility(View.VISIBLE);
                        jointCordiFour.setVisibility(View.VISIBLE);
                        jointCordiFive.setVisibility(View.VISIBLE);
                        mainCordi.setText(main);
                        jointCordiOne.setText(joint1);
                        jointCordiTwo.setText(joint2);
                        jointCordiThree.setText(joint3);
                        jointCordiFour.setText(joint4);
                        jointCordiFive.setText(joint5);
                    }


                    mainCordi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);

                            if (getAdapterPosition() == 0) {
                                call.setData(Uri.parse("tel:" + chiefCordiMain[0]));
                            } else if (getAdapterPosition() == 1) {
                                call.setData(Uri.parse("tel:" + chiefCordiMain[1]));
                            }else if (getAdapterPosition()>1){
                                call.setData(Uri.parse("tel:"+main));
                            }
                            context.startActivity(call);

                        }
                    });


                    jointCordiOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            if (getAdapterPosition() == 0){
                                call.setData(Uri.parse("tel:"+chiefCordisJoint[0]));
                            }else if (getAdapterPosition() == 1){
                                call.setData(Uri.parse("tel:"+chiefCordisJoint[1]));
                            }else if (getAdapterPosition()>1){
                                call.setData(Uri.parse("tel:" + joint1));
                            }
                            context.startActivity(call);
                        }
                    });


                    jointCordiTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:"+joint2));
                            context.startActivity(call);
                        }
                    });

                    jointCordiThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:"+joint3));
                            context.startActivity(call);
                        }
                    });

                    jointCordiFour.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:"+joint4));
                            context.startActivity(call);
                        }
                    });

                    jointCordiFive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:"+joint5));
                            context.startActivity(call);
                        }
                    });

                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    ad.show();

                }
            });
        }
    }


    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private final String[] mComitenames;


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mComitenames = resources.getStringArray(R.array.committees);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.nameOfComit.setText(mComitenames[position % mComitenames.length]);
        }

        @Override
        public int getItemCount() {
            return mComitenames.length;
        }
    }


}
