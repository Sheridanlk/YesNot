package com.example.yesnot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.yesnot.sampledata.Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    ViewPager2 myViewPager2;
    Adapter myAdapter;
    private static final String PREFS_FILE = "Clicks";
    static SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        myViewPager2 = findViewById(R.id.viewPager2);
        myAdapter = new Adapter(getSupportFragmentManager(), getLifecycle());
        TabLayout tabLayout = findViewById(R.id.tabDots);

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
