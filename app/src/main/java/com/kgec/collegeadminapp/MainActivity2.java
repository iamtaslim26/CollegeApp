package com.kgec.collegeadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kgec.collegeadminapp.Fragments.AboutFragment;
import com.kgec.collegeadminapp.Fragments.BlankFragment;
import com.kgec.collegeadminapp.Fragments.FacultyFragment;
import com.kgec.collegeadminapp.Fragments.GalleryFragment;
import com.kgec.collegeadminapp.Fragments.HomeFragment;
import com.kgec.collegeadminapp.Fragments.NoticeFragment;

public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView nav_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nav_View=findViewById(R.id.nav_view1);
        nav_View.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectedFragment).commit();
            return true;
        }
    };
}