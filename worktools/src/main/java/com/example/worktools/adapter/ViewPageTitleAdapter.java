package com.example.worktools.adapter;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * Created by xuhao on 2016/5/27.
 */
public class ViewPageTitleAdapter extends FragmentPagerAdapter{
    private List<String>list_Title;
    private List<Fragment>fragments;
    private FragmentManager fragmentManager;
    @SuppressLint("WrongConstant")
    public ViewPageTitleAdapter(FragmentManager fm, List<String>list_Title, List<Fragment>fragments) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentManager=fm;
        this.list_Title=list_Title;
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        int hashCode = fragments.get(position).hashCode();
        return hashCode;
    }

    //    此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        if(list_Title==null||list_Title.size()==0){
            return null;
        }
        return list_Title.get(position % list_Title.size());
    }
    public void setFragments(List<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fragmentManager.executePendingTransactions();
        }
        this.fragments = fragments;
    }
}
