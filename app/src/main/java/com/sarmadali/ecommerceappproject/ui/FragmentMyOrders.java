package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Adapters.CheckOutAdapter;
import com.sarmadali.ecommerceappproject.Adapters.MyOrdersAdapterOne;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.OrderProducts;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentMyOrdersBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class FragmentMyOrders extends Fragment  implements Dashboard.IOnBackPressed{

    public FragmentMyOrders() {
        // Required empty public constructor
    }

    FragmentMyOrdersBinding myOrdersBinding;
    private ArrayList<OrderProducts> plist;
    ArrayList<ProductDetails> productlist;
    FirebaseUser firebaseUser;
    private MyOrdersAdapterOne pAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myOrdersBinding = FragmentMyOrdersBinding.inflate(inflater, container, false);

        //my order products starts
        plist = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = firebaseUser.getUid();

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                .child(currentId)
                .child("OrderPlaced");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plist.clear();

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {

                    OrderProducts orderProducts = orderSnapshot.getValue(OrderProducts.class);
                    plist.add(orderProducts);

                    productlist =  new ArrayList<>();
                    DataSnapshot productsSnapshot = orderSnapshot.child("Products");
                    for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                        ProductDetails product = productSnapshot.getValue(ProductDetails.class);
                        productlist.add(product);
                    }
                    orderProducts.setProductList(productlist);

                }


                pAdapter = new MyOrdersAdapterOne(getContext(), plist);
                pAdapter.setOrderList(productlist);

                if (pAdapter != null) {

                    myOrdersBinding.myOrdersRecyclerView.setAdapter(pAdapter);
                }
                pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myOrdersBinding.myOrdersRecyclerView.setLayoutManager(linearLayoutManager);
            //my order products ends

        return myOrdersBinding.getRoot();
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