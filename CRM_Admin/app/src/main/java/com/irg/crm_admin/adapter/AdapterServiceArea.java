package com.irg.crm_admin.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.irg.crm_admin.R;
import com.irg.crm_admin.fragment.AreaFragment;
import com.irg.crm_admin.fragment.CityFragment;
import com.irg.crm_admin.fragment.DistrictFragment;
import com.irg.crm_admin.fragment.StateFragment;

public class AdapterServiceArea  extends FragmentPagerAdapter
{
    private static final int STATE = 0;
    private static final int DISTRICT = 1;
    private static final int CITY = 2;
    private static final int AREA = 3;

    private static final int[] TABS = new int[]{STATE, DISTRICT, CITY, AREA};

    private Context mContext;

    public AdapterServiceArea(final Context context, final FragmentManager fm)
    {
        super(fm);
        mContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (TABS[position])
        {
            case STATE:
                return new StateFragment();
            case DISTRICT:
                return new DistrictFragment();
            case CITY:
                return new CityFragment();
            case AREA:
                return new AreaFragment();
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return TABS.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (TABS[position])
        {
            case STATE:
                return mContext.getResources().getString(R.string.state);
            case DISTRICT:
                return mContext.getResources().getString(R.string.district);
            case CITY:
                return mContext.getResources().getString(R.string.city);
            case AREA:
                return mContext.getResources().getString(R.string.area);
        }
        return null;
    }
}