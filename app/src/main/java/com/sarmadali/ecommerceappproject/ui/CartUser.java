package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Adapters.MyCartAdapter;
import com.sarmadali.ecommerceappproject.Adapters.ProductAdapter;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentCartUserBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class CartUser extends Fragment
        implements Dashboard.IOnBackPressed
{


    public CartUser() {
        // Required empty public constructor
    }

    FragmentCartUserBinding binding;
    private ArrayList<ProductDetails> plist;
    FirebaseUser firebaseUser;
    private MyCartAdapter pAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartUserBinding.inflate(inflater, container, false);

        //change title
        Dashboard activity = (Dashboard) getActivity();
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("My Cart");

        //my cart recyclerview starts
        plist = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = firebaseUser.getUid();
        // Assuming you have a DatabaseReference reference initialized
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                .child(currentId)
                .child("cartProducts");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plist.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductDetails productDetails = postSnapshot.getValue(ProductDetails.class);
                    plist.add(productDetails);
                }

                pAdapter = new MyCartAdapter(plist, getContext());


                if (pAdapter != null){
                binding.cartRecyclerview.setAdapter(pAdapter);
                }
                pAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.cartRecyclerview.setLayoutManager(linearLayoutManager);
        //my cart recyclerview ends
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