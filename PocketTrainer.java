package com.pockettrainer;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PocketTrainer extends AppCompatActivity {
    private TabLayout tl_jour;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket_trainer);
        db = new DataBaseHelper(getApplicationContext());
        //this.deleteDatabase("Historique.db");

        tl_jour = findViewById(R.id.tl_jour);
        viewPager = findViewById(R.id.vp_contenu);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragment(new LundiFragment(), "Scéance");
        viewPagerAdapter.AddFragment(new HistoriqueFragment(), "Historique");
        viewPager.setAdapter(viewPagerAdapter);
        tl_jour.setupWithViewPager(viewPager);


    }
}
