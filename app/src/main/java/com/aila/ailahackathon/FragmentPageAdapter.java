package com.aila.ailahackathon;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.aila.ailahackathon.fragment.TabFour;
import com.aila.ailahackathon.fragment.TabOne;
import com.aila.ailahackathon.fragment.TabThree;
import com.aila.ailahackathon.fragment.TabTwo;

public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public FragmentPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabOne tab1 = new TabOne();
                return tab1;
            case 1:
                TabTwo tab2 = new TabTwo();
                return tab2;
            case 2:
                TabThree tab3 = new TabThree();
                return tab3;
            case 3:
                TabFour tab4 = new TabFour();
                return tab4;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
