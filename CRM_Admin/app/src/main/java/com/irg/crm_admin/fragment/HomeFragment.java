package com.irg.crm_admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.irg.crm_admin.R;
import com.irg.crm_admin.databinding.FragmentHomeBinding;
import com.irg.crm_admin.viewModel.ClickEvent;

public class HomeFragment extends Fragment {

    static FragmentHomeBinding binding;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        ClickEvent handlers = new ClickEvent(getContext());
        binding.setHandlers(handlers);
        //here data must be an instance of the class MarsDataProvider
       // getProductList(view);
        return view;
    }
}