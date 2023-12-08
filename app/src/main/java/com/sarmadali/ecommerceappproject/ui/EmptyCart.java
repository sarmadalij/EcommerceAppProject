package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;


public class EmptyCart extends Fragment implements Dashboard.IOnBackPressed{


    public EmptyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_cart, container, false);
    }


    @Override
    public boolean onBackPressed() {
        // Handle back button press in your fragment
        // Go back to the default fragment in your case
        DashboardFragment defaultFragment = new DashboardFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();

        return true;
    }
}