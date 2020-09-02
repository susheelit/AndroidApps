package com.irg.crm_admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.irg.crm_admin.R;
import com.irg.crm_admin.activity.AddServiceAreaActivity;
import com.irg.crm_admin.adapter.AdapterServiceArea;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.databinding.FragmentServiceAreaBinding;

public class ServiceAreaFragment extends Fragment {

    static FragmentServiceAreaBinding binding;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_service_area, container, false);
        View view = binding.getRoot();
        binding.setHandler(this);
        binding.setManager(getActivity().getSupportFragmentManager());
        return view;
    }
        @BindingAdapter({"bind:handler"})
        public static void bindViewPagerAdapter(final ViewPager view, final ServiceAreaFragment activity)
        {
            final AdapterServiceArea adapter = new AdapterServiceArea(view.getContext(), activity.getActivity().getSupportFragmentManager());
            view.setAdapter(adapter);
        }

        @BindingAdapter({"bind:pager"})
        public static void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView)
        {
            view.setupWithViewPager(pagerView, true);
        }

    public void onClickAddServiceArea(View view){
        OprActivity.openActivity(view.getContext(), new AddServiceAreaActivity());
    }

}