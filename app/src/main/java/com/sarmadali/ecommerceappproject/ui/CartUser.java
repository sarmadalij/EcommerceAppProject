package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentCartUserBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;

public class CartUser extends Fragment
        implements Dashboard.IOnBackPressed
{


    public CartUser() {
        // Required empty public constructor
    }

    FragmentCartUserBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartUserBinding.inflate(inflater, container, false);



        //SEARCH ICON VISIBILITY GONE
        Dashboard activity = (Dashboard) getActivity();

        //change title
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("My Cart");
        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {

        // Handle back button press in your fragment
        // Go back to the default fragment
        DashboardFragment defaultFragment = new DashboardFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();

        return true;
    }
}