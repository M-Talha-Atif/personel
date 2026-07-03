package com.example.smartpetfeeder;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.Locale;

public class MonthlyTrendFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trend, container, false);

        TextView title = view.findViewById(R.id.trendTitle);
        TextView summary = view.findViewById(R.id.trendSummary);
        FeedingDbHelper dbHelper = new FeedingDbHelper(getContext());

        title.setText(getString(R.string.monthly_trends));

        Calendar cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, -1);
        long startTime = cal.getTimeInMillis();

        Cursor cursor = dbHelper.getFeedingData(startTime, endTime);
        float totalAmount = 0;
        int feedingCount = 0;

        while (cursor.moveToNext()) {
            totalAmount += cursor.getFloat(cursor.getColumnIndexOrThrow(FeedingDbHelper.COLUMN_AMOUNT));
            feedingCount++;
        }
        cursor.close();

        String summaryText = String.format(Locale.getDefault(),"Total Feedings: %d\nTotal Amount: %.1f grams\nDaily Average: %.1f grams", feedingCount, totalAmount, feedingCount > 0 ? totalAmount/30 : 0);
        summary.setText(summaryText);

        return view;
    }
}
