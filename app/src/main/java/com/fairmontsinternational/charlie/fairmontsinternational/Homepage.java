package com.fairmontsinternational.charlie.fairmontsinternational;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import io.paperdb.Paper;

public class Homepage extends AppCompatActivity {

    String admission_no;

    boolean doubleBackToExitPressedOnce = false;
    AppCompatImageView Notification;
    ConstraintLayout Profiler,Fee,Coursework,Timetables,info,Diary,logout,attendance;

//    private SectionsPagerAdapter mSectionsPagerAdapter;

//    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Paper.init(this);
        Intent intent=getIntent();
        admission_no=intent.getStringExtra("admission_no");
        Paper.book().write("admission_no",admission_no);

        Profiler= findViewById(R.id.Btn_Home_tab_Profile);
        Fee= findViewById(R.id.Btn_Home_tab_Fees);
        Coursework= findViewById(R.id.Btn_Home_tab_coursework);
        Timetables= findViewById(R.id.Btn_Home_tab_Timetable);
        Diary= findViewById(R.id.Btn_Home_tab_diary);
        logout= findViewById(R.id.Btn_Home_tab_Logout);
        info=findViewById(R.id.Btn_Info);
        attendance=findViewById(R.id.Btn_Home_tab_Attendance);
        Notification = findViewById(R.id.notification);

        Notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(Homepage.this, android.app.Notification.class));
            }
        });


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,Attendance.class));
            }
        });

        Coursework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,Coursework.class));
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(Homepage.this);
                builder.setMessage("Fairmonts International School \n\n Version 1.1.0 " +
                        "\n");
                builder.setIcon(R.drawable.fairmontslogo);
                builder.setTitle("Application Information");
                builder.setCancelable(true);
                android.support.v7.app.AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });

        Diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,Reports.class));
            }
        });

        Timetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, timetables.class));
            }
        });

        Profiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,StudentProfile.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(Homepage.this);
                builder.setMessage("Are you sure you want to Logout?");
                builder.setCancelable(true);
                builder.setIcon(R.drawable.fairmontslogo);
                builder.setTitle("Logout");
                builder.setNegativeButton("No, Stay!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes, Logout!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String empty="";
                        Paper.book().write("Phone_number",empty);
                        startActivity(new Intent(Homepage.this, LoginActivity.class));
                        finish();
                    }
                });
                android.support.v7.app.AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });

        Fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,fees.class));
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_switch:
                String empty="";
                Paper.book().write("admission_no",empty);
                startActivity(new Intent(Homepage.this,children_profiles.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
////        public PlaceholderFragment() {
////        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
////        public static PlaceholderFragment newInstance(int sectionNumber) {
////            PlaceholderFragment fragment = new PlaceholderFragment();
////            Bundle args = new Bundle();
////            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
////            fragment.setArguments(args);
////            return fragment;
////        }
//
////        @Override
////        public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                                 Bundle savedInstanceState) {
////
////            final ConstraintLayout Profiler,Fee,Coursework,Timetables,info,Diary,logout,attendance;
////            View rootView=null;
////            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
////                case 1:
////                     rootView= inflater.inflate(R.layout.tab1, container, false);
////
////                    Profiler= rootView.findViewById(R.id.Btn_Home_tab_Profile);
////                    Fee= rootView.findViewById(R.id.Btn_Home_tab_Fees);
////                    Coursework= rootView.findViewById(R.id.Btn_Home_tab_coursework);
////                    Timetables= rootView.findViewById(R.id.Btn_Home_tab_Timetable);
////                    Diary= rootView.findViewById(R.id.Btn_Home_tab_diary);
////                    logout= rootView.findViewById(R.id.Btn_Home_tab_Logout);
////                    info=rootView.findViewById(R.id.Btn_Info);
////                    attendance=rootView.findViewById(R.id.Btn_Home_tab_Attendance);
////
////
////                    attendance.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(),Attendance.class));
////                        }
////                    });
////
////                    Coursework.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(),Coursework.class));
////                        }
////                    });
////
////                    info.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(getContext());
////                            builder.setMessage("Fairmonts International School \n\n Version 1.0.0 " +
////                                    "\n");
////                            builder.setIcon(R.drawable.fairmontslogo);
////                            builder.setTitle("Application Information");
////                            builder.setCancelable(true);
////                            android.support.v7.app.AlertDialog alertDialog= builder.create();
////                            alertDialog.show();
////                        }
////                    });
////
////                    Diary.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(),Reports.class));
////                        }
////                    });
////
////                    Timetables.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(), timetables.class));
////                        }
////                    });
////
////                    Profiler.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(),StudentProfile.class));
////                        }
////                    });
////                    logout.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(final View v) {
////                            final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(getContext());
////                            builder.setMessage("Are you sure you want to Logout?");
////                            builder.setCancelable(true);
////                            builder.setIcon(R.drawable.fairmontslogo);
////                            builder.setTitle("Logout");
////                            builder.setNegativeButton("No, Stay!", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    dialog.cancel();
////                                }
////                            });
////                            builder.setPositiveButton("Yes, Logout!", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    String empty="";
////                                    Paper.book().write("Parent_id",empty);
////                                    startActivity(new Intent(getContext(), LoginActivity.class));
////                                    getActivity().finish();
////                                }
////                            });
////                            android.support.v7.app.AlertDialog alertDialog= builder.create();
////                            alertDialog.show();
////                        }
////                    });
////
////                    Fee.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(getContext(),fees.class));
////                        }
////                    });
////
////                    break;
////                case 2:
////                     rootView= inflater.inflate(R.layout.tab2, container, false);
////                    break;
////            }
////            return rootView;
////        }
//
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 2;
//        }
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
