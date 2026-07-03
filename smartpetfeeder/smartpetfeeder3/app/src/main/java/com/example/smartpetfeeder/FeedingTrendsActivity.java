package com.example.smartpetfeeder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Calendar;

public class FeedingTrendsActivity extends AppCompatActivity {
    private static final int[] TAB_TITLES = new int[]{R.string.tab_daily, R.string.tab_weekly, R.string.tab_monthly};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_trends);

        // Initialize database with sample data
        try (FeedingDbHelper dbHelper = new FeedingDbHelper(this)) {
            Calendar cal = Calendar.getInstance();
            dbHelper.addFeeding(cal.getTimeInMillis(), 50.0f);
            cal.add(Calendar.HOUR, -6);
            dbHelper.addFeeding(cal.getTimeInMillis(), 45.0f);
            cal.add(Calendar.DAY_OF_YEAR, -2);
            dbHelper.addFeeding(cal.getTimeInMillis(), 60.0f);
        }

        // Setup ViewPager and Tabs
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TrendPagerAdapter adapter = new TrendPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getString(TAB_TITLES[position]))
        ).attach();
    }
}
