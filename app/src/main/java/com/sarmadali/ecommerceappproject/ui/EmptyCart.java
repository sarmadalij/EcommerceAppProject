package com.sarmadali.ecommerceappproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.LoginActivity;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentAccountUserBinding;
import com.sarmadali.ecommerceappproject.databinding.FragmentEmptyCartBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;


public class EmptyCart extends Fragment
        implements Dashboard.IOnBackPressed
{


    public EmptyCart() {
        // Required empty public constructor
    }


    FragmentEmptyCartBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentEmptyCartBinding.inflate(inflater, container, false);

        //go to login page
        binding.buttonGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return binding.getRoot();
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