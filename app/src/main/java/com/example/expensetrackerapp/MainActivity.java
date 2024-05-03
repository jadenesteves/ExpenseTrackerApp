package com.example.expensetrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
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

    public void addFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
