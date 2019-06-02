package com.cricscore.deepakshano.cricscore.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.fragment.BookingFragment;
import com.cricscore.deepakshano.cricscore.fragment.GroupsFragment;
import com.cricscore.deepakshano.cricscore.fragment.InternationalFragment;
import com.cricscore.deepakshano.cricscore.fragment.TournamentFragment;
import com.cricscore.deepakshano.cricscore.helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            loadFragment(new InternationalFragment());
            BottomNavigationView navigation = findViewById(R.id.navigation);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(this);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        try {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new InternationalFragment();
                    break;
                case R.id.navigation_tournament:

                    fragment = new TournamentFragment();
                    break;
                case R.id.navigation_groups:

                    fragment = new GroupsFragment();
                    break;
                case R.id.navigation_booking:

                    fragment = new BookingFragment();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        try {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }


}
