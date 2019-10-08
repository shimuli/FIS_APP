package com.fairmontsinternational.charlie.fairmontsinternational;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BaseUrl;

import io.paperdb.Paper;

public class MainHomepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String admission_no,parent_phone;
    private static String URL;
    public TextView Pname,Phone;

    boolean doubleBackToExitPressedOnce = false;
    ConstraintLayout Profiler,Fee,Coursework,Timetables,Diary,attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        Pname=headerView.findViewById(R.id.Parent_name_profile);
        Phone=headerView.findViewById(R.id.Parent_phone_number);

        Paper.init(this);
        Intent intent=getIntent();
        admission_no=intent.getStringExtra("admission_no");
        parent_phone=Paper.book().read("Phone_number").toString();
        Paper.book().write("admission_no",admission_no);
        fetchparentname(parent_phone);

        Profiler= findViewById(R.id.Btn_Home_tab_StudentProfile);
        Fee= findViewById(R.id.Btn_Home_tab_StudentFees);
        Coursework= findViewById(R.id.Btn_Home_tab_Studentcoursework);
        Timetables= findViewById(R.id.Btn_Home_tab_StudentTimetable);
        Diary= findViewById(R.id.Btn_Home_tab_Studentdiary);
        attendance=findViewById(R.id.Btn_Home_tab_StudentAttendance);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHomepage.this,Attendance.class));
            }
        });

        Coursework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHomepage.this,Coursework.class));
            }
        });

        Diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainHomepage.this,Reports.class));
               // startActivity(new Intent(MainHomepage.this,Diary.class));
            }
        });

        Timetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHomepage.this, timetables.class));
            }
        });

        Profiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHomepage.this,StudentProfile.class));
            }
        });

        Fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainHomepage.this,fees.class));
                //startActivity(new Intent(MainHomepage.this,FeeInvoice.class));
            }
        });
    }

    private void fetchparentname(String phone) {
        Phone.setText(phone);
//        URL="http://fairmontsinternationalschool.co.ke/fairmontsAPI/fetchname.php?parent_phone="+phone;
        URL= BaseUrl.fetchname(phone);
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String Parentname = response.replaceAll("^\"|\"$", "");
                Pname.setText(Parentname.toLowerCase());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }else{
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_homepage, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent=new Intent(MainHomepage.this,UserProfile.class);
            intent.putExtra("parent_name",Pname.getText().toString());
            intent.putExtra("phoneNo",Phone.getText().toString());
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(MainHomepage.this);
            builder.setMessage("Are you sure you want to logout?");
            builder.setCancelable(true);
            builder.setIcon(R.drawable.fairmontslogo);
            builder.setTitle("Confirm Logout");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String empty="";
                    Paper.book().write("Phone_number",empty);
                    startActivity(new Intent(MainHomepage.this, LoginActivity.class));
                    finish();
                }
            });
            android.support.v7.app.AlertDialog alertDialog= builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_switch) {
            String empty="";
            Paper.book().write("admission_no",empty);
            startActivity(new Intent(MainHomepage.this,children_profiles.class));
            finish();
        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(MainHomepage.this,Feedback.class);
            intent.putExtra("parent_name",Pname.getText().toString());
            intent.putExtra("phoneNo",Phone.getText().toString());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
