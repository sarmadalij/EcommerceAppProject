package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentOrderConfrimBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;

public class FragmentOrderConfrim extends Fragment implements Dashboard.IOnBackPressed {

    public FragmentOrderConfrim() {
        // Required empty public constructor
    }

    FragmentOrderConfrimBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderConfrimBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {

        DashboardFragment defaultFragment = new DashboardFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();
        return true;
    }
}