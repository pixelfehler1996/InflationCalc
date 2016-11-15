package com.example.laudien.inflationcalc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.laudien.inflationcalc.InflationFragment.TYPE_INFLATION;
import static com.example.laudien.inflationcalc.InflationFragment.TYPE_VALUE_INCREASE;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InflationFragment(TYPE_INFLATION);
                break;
            case 1:
                fragment = new InflationFragment(TYPE_VALUE_INCREASE);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = context.getString(R.string.inflation);
                break;
            case 1:
                title = context.getString(R.string.value_increase);
                break;
        }
        return title;
    }
}
