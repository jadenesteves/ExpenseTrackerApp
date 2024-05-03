/**
 * Name: MainActivity.java
 * Last Updated: 5/3/2024
 * Description: Class for main activity of programs, hold frame layout for fragments and bottom navigation
 */
package com.example.expensetrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.expensetrackerapp.Fragments.Dashboard;
import com.example.expensetrackerapp.Fragments.Overview;
import com.example.expensetrackerapp.Fragments.Transactions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Create objects for each fragment
    BottomNavigationView view;
    Dashboard dashboardFragment = new Dashboard();
    Overview overviewFragment = new Overview();
    Transactions transactionFragment = new Transactions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        // Set view to overview activity
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, overviewFragment).commit();

        // When navigation view item is selected, swap between fragments
       view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item){
               int menuItemId = item.getItemId();

               if(menuItemId == R.id.dashboard){
                   addFragment(dashboardFragment);
                   return true;
               } else if (menuItemId == R.id.transaction){
                  addFragment(transactionFragment);
                   return true;
               } else if (menuItemId == R.id.overview){
                   addFragment(overviewFragment);
                   return true;
               }

               return false;
           }
       });

    }

    // Add fragment method
    public void addFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
