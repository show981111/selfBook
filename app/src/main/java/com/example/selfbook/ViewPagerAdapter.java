package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] arrFragments;
    private String version;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Fragment[] arrFragments, String version) {
        super(fm, behavior);
        this.arrFragments = arrFragments;
        this.version = version;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrFragments[position];
    }

    @Override
    public int getCount() {
        return arrFragments.length;
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(version.equals("bookInfo")) {
            switch (position) {
                case 0:
                    return "책소개";
                case 1:
                    return "미리보기";
                default:
                    return "";
            }
        }else{
            switch (position) {
                case 0:
                    return "기본정보";
                case 1:
                    return "목차";
                default:
                    return "";
            }
        }

    }
}
