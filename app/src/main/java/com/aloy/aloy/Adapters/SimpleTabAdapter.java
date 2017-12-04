package com.aloy.aloy.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madmax on 08/11/2017.
 * Adapter that manages simple View Pager, navigating through tabs.
 */

public class SimpleTabAdapter extends SmartFragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentsTitle = new ArrayList<>();

    public SimpleTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragments(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentsTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {return fragments.get(position);}

    @Override
    public int getCount() {return fragments.size();}

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentsTitle.get(position);
    }
}
