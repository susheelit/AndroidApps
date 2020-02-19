package com.e.salestracker.Adapter;

import android.content.Context;
import com.e.salestracker.R;
import com.e.salestracker.fragment.LocationFragment;
import com.e.salestracker.fragment.ProjectFragment;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] { R.string.tab_Location, R.string.tab_Project };
    private final Context mContext;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

  //  public TabAdapter() { }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LocationFragment.newInstance();
            case 1:
                return ProjectFragment.newInstance();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
