package com.example.hoctienganh.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hoctienganh.fragment.CollectionFragment;
import com.example.hoctienganh.fragment.StoreFragment;

public class ViewPagerStoreAdapter extends FragmentStateAdapter {

    public ViewPagerStoreAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new StoreFragment();
        }
        else if (position ==1 ){
            return new CollectionFragment();
        }
        return new StoreFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
