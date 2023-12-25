package com.sarmadali.ecommerceappproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.sarmadali.ecommerceappproject.Models.OrderProducts;
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

        //upload order in database
        checkOutBinding.confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userOrderName = checkOutBinding.orderName.getText().toString();
                String userOrderEmail = checkOutBinding.orderEmail.getText().toString();
                String userOrderPhone = checkOutBinding.orderNumber.getText().toString();
                String userOrderBillingAddress = checkOutBinding.orderBillingAddress.getText().toString();

                ProductDetails productDetails = new ProductDetails();

                String productOrderImage = productDetails.getProductImageUri();
                String productOrderName = productDetails.getProductName();
                String productOrderQuantity = productDetails.getProductQuantity();

                String totalOrderPrice = checkOutBinding.grandTotalCheckOut.getText().toString();

                //for order user info and price
                OrderProducts orderProducts = new OrderProducts(userOrderName, userOrderEmail, userOrderPhone,
                        userOrderBillingAddress, totalOrderPrice);

                //for order products info
                OrderProducts orderProductsDetails = new OrderProducts(productOrderImage, productOrderName,
                        productOrderQuantity);

                String currentId;
                DatabaseReference cartRef;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //validate input
                if (TextUtils.isEmpty(userOrderName)) {
                    checkOutBinding.orderName.setError("Name cannot be empty");
                    return; // Stop execution if email is empty
                }

                if (TextUtils.isEmpty(userOrderEmail)) {
                    checkOutBinding.orderEmail.setError("Email cannot be empty");
                    return; // Stop execution if password is empty
                }
                if (TextUtils.isEmpty(userOrderPhone)) {
                    checkOutBinding.orderNumber.setError("Phone Number cannot be empty");
                    return; // Stop execution if password is empty
                }
                if (TextUtils.isEmpty(userOrderBillingAddress)) {
                    checkOutBinding.orderBillingAddress.setError("Address cannot be empty");
                    return; // Stop execution if password is empty
                }


                //checks if user is signed in or not
                if (user != null) {

                    currentId = firebaseUser.getUid();
                    // Assuming you have a DatabaseReference reference initialized
                    cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                            .child(currentId)
                            .child("OrderPlaced");
                    String uploadId = cartRef.push().getKey();

                    cartRef.child(uploadId).setValue(orderProducts)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Order Placed successfully", Toast.LENGTH_SHORT).show();

                                    for (ProductDetails products : plist){
                                        cartRef.child(uploadId).child("Products").child(products.getProductName())
                                                .setValue(products);
                                    }


                                    DatabaseReference cartReff = FirebaseDatabase.getInstance().getReference("userDetails")
                                            .child(currentId)
                                            .child("cartProducts");


                                    cartReff.removeValue();

                                    FragmentOrderConfrim orderConfrim = new FragmentOrderConfrim();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_host_fragment_activity_dashboard, orderConfrim)
                                            .commit();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed (: try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

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