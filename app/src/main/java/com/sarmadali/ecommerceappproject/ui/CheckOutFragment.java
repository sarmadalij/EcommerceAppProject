package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Adapters.CheckOutAdapter;
import com.sarmadali.ecommerceappproject.Adapters.MyCartAdapter;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentCheckOutBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class CheckOutFragment extends Fragment implements Dashboard.IOnBackPressed {


    public CheckOutFragment() {
        // Required empty public constructor
    }

    FragmentCheckOutBinding checkOutBinding;
    private ArrayList<ProductDetails> plist;
    FirebaseUser firebaseUser;
    private CheckOutAdapter pAdapter;
    int subTotal, taxCharges, deliveryCharges, total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checkOutBinding = FragmentCheckOutBinding.inflate(inflater, container, false);

        //change title
        Dashboard activity = (Dashboard) getActivity();
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("Check Out");


        //my checkout recyclerview starts
        plist = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = firebaseUser.getUid();

        // Assuming you have a DatabaseReference reference initialized
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                .child(currentId).child("cartProducts");


        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductDetails productDetails = postSnapshot.getValue(ProductDetails.class);

                    subTotal += Integer.parseInt(String.valueOf(productDetails.getTotalProductPrice()));
                    plist.add(productDetails);
                }

                checkOutBinding.subTotalCheckOut.setText(String.valueOf(subTotal));

                taxCharges = (int) (subTotal*0.08);

                checkOutBinding.taxCheckOut.setText(String.valueOf(taxCharges));

                deliveryCharges = Integer.parseInt(checkOutBinding.deliveryCheckOut.getText().toString());
                total = subTotal+taxCharges+deliveryCharges;

                checkOutBinding.grandTotalCheckOut.setText(String.valueOf(total));


                pAdapter = new CheckOutAdapter(getContext(), plist);
                if (pAdapter != null){
                    checkOutBinding.checkOutRecyclerview.setAdapter(pAdapter);
                }
                pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        checkOutBinding.checkOutRecyclerview.setLayoutManager(linearLayoutManager);
        //my checkout recyclerview ends

        return checkOutBinding.getRoot();
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