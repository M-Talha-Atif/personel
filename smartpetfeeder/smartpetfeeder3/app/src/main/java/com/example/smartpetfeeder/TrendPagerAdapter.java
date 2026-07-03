package com.example.smartpetfeeder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class TrendPagerAdapter extends FragmentStateAdapter {
    public TrendPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    @NonNull
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DailyTrendFragment();
            case 1:
                return new WeeklyTrendFragment();
            case 2:
                return new MonthlyTrendFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs: Daily, Weekly, Monthly
    }
}
