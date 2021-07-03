package com.kgec.collegeadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kgec.collegeadminapp.Fragments.AboutFragment;
import com.kgec.collegeadminapp.Fragments.FacultyFragment;
import com.kgec.collegeadminapp.Fragments.GalleryFragment;
import com.kgec.collegeadminapp.Fragments.HomeFragment;
import com.kgec.collegeadminapp.Fragments.NoticeFragment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class UserMainActivity extends AppCompatActivity {

    private  BottomNavigationView navView;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

       navView = findViewById(R.id.nav_view);
     navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

       navigationView=findViewById(R.id.navigation_view);
       drawerLayout=findViewById(R.id.drawer_layout);

        View nav_header_view=navigationView.inflateHeaderView(R.layout.navigation_header);
        View header_view=navigationView.getHeaderView(0);

        actionBarDrawerToggle=new ActionBarDrawerToggle(UserMainActivity.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container,
//                    new FacultyFragment()).commit();
//        }

        getSupportFragmentManager().beginTransaction().replace(R.id.users_frame,new HomeFragment()).commit();





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                userMenuSelector(item);

                return false;
            }
        });


    }

    private void userMenuSelector(MenuItem item) {


        switch (item.getItemId()){

            case R.id.navigation_video_lecture:
                Toast.makeText(this, "Video Lecture", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_Ebooks:
                Toast.makeText(this, "EBooks", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_Themes:
                Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_Website:
                Toast.makeText(this, "Websites", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_rate_us:
                Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_developers:
                Toast.makeText(this, "Developers", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            Fragment selectedFragment=null;

            switch (item.getItemId()){

              case  R.id.navigation_home:
                selectedFragment=new HomeFragment();
                break;

                case  R.id.navigation_notice:

                   selectedFragment=new NoticeFragment();
                   break;

                case  R.id.navigation_gallery:

                    selectedFragment=new GalleryFragment();
                    break;

                case  R.id.navigation_faculty:


                    selectedFragment=new FacultyFragment();
                    break;

                case  R.id.navigation_about:


                    selectedFragment=new AboutFragment();
                    break;


            }

           getSupportFragmentManager().beginTransaction().replace(R.id.users_frame,selectedFragment).commit();
            return true;
        }

    };


    }
