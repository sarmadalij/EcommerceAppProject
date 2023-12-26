package com.sarmadali.ecommerceappproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.SignUpActivity;
import com.sarmadali.ecommerceappproject.databinding.FragmentNoUserAccountBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;


public class NoUserAccount extends Fragment
        implements Dashboard.IOnBackPressed {


    public NoUserAccount() {
        // Required empty public constructor
    }

    FragmentNoUserAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoUserAccountBinding.inflate(inflater, container, false);

        binding.noUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //change title
        Dashboard activity = (Dashboard) getActivity();
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("My Profile");

        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        // Handle back button press in fragment
        // Go back to the default fragment
        DashboardFragment defaultFragment = new DashboardFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();
        return true;
    }
}