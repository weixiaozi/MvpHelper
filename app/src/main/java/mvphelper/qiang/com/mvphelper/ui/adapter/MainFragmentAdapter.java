package mvphelper.qiang.com.mvphelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import mvphelper.qiang.com.mvphelper.base.BaseFragment;

/**
 * Created by dell on 2017/10/25.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public MainFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
