package com.example.myapplication4.Menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication4.Menu.ui.OrdeamientoFragment;
import com.example.myapplication4.Menu.ui.breakFragment;
import com.example.myapplication4.Menu.ui.gatoFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new gatoFragment();
            case 1:
                return new OrdeamientoFragment();
            case 2:
                return new breakFragment();
            default:
                return new gatoFragment();


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
