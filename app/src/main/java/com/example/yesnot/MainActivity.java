package com.example.yesnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.example.yesnot.databinding.ActivityMainBinding;
import com.example.yesnot.sampledata.Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    ViewPager2 myViewPager2;
    Adapter myAdapter;
    private static final String PREFS_FILE = "Clicks";
    private ActivityMainBinding binding;

    private Toolbar toolbar;
    static SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        myViewPager2 = binding.viewPager2;
        myAdapter = new Adapter(getSupportFragmentManager(), getLifecycle());
        TabLayout tabLayout = binding.tabDots;

        myAdapter.addFragment(new BlankFragment2());
        myAdapter.addFragment(new BlankFragment1());
        myAdapter.addFragment(new BlankFragment3());

        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(myAdapter);
        myViewPager2.setCurrentItem(1,false);


        new TabLayoutMediator(tabLayout, myViewPager2,
                (tab, position) -> tab.setIcon(R.drawable.default_dot)

        ).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.color_menu, menu);
        return true;
    }

    public static class PreferenceManager{
        public static void saveClicks(int clicks){
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putInt("YesNoClicks", clicks);
            prefEditor.apply();
        }
        public static int getClicks(){
            return settings.getInt("YesNoClicks", 0);
        }
    }
}
