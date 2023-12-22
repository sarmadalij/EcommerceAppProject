package com.sarmadali.ecommerceappproject.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.LoginActivity;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.SignUpActivity;
import com.sarmadali.ecommerceappproject.databinding.FragmentProductDetailsBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;


public class ProductDetailsFragment extends Fragment
        implements Dashboard.IOnBackPressed {

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    FragmentProductDetailsBinding binding;
    FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);

        // Retrieve the product argument
        ProductDetails product = getArguments().getParcelable("product");


        Uri  pImage = Uri.parse(product.getProductImageUri());

        Picasso.get().load(pImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.productImg);

        String name = product.getProductName();
        String pPrice = product.getProductPrice();
        String pDetail = product.getProductDescription();

        binding.ProductName.setText(name);
        binding.productPricedetail.setText("Rs "+pPrice);
        binding.detailDescription.setText(pDetail);

        //increment button
        binding.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuantity();
            }
        });

        //decrement button
        binding.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity();
            }
        });

        //add to cart starts
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pImage = product.getProductImageUri();
                String pName = product.getProductName();
                String pCateg = product.getProductCategory();
                String pPrice = product.getProductPrice();
                String pQuantity = binding.counterTextView.getText().toString();

                int totalPrice =(Integer.parseInt(pPrice)*Integer.parseInt(pQuantity));

                ProductDetails cartProduct = new ProductDetails(pImage, pName,pCateg, pPrice, pQuantity, totalPrice);

                String currentId;
                DatabaseReference cartRef;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //checks if user is signed in or not
                if (user != null) {
                    currentId = firebaseUser.getUid();
                    // Assuming you have a DatabaseReference reference initialized
                    cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                            .child(currentId)
                            .child("cartProducts");

                    // Add the item to the cart
                    cartRef.child(pName).setValue(cartProduct)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Product Add to Cart successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed Add to Cart", Toast.LENGTH_SHORT).show();
                                }
                            });

                    CartUser cartUser = new CartUser();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_dashboard, cartUser)
                            .commit();

                } else {
                    showDialog();
                }
            }
        });
        //add to cart ends
        return binding.getRoot();
    }

    private int quantity = 1;

    public void incrementQuantity() {
        quantity += 1; // Increase the quantity by 1

        TextView quantityTextView = binding.counterTextView;
        quantityTextView.setText(String.valueOf(quantity));
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity -= 1; // Decrease the quantity by 1

            TextView quantityTextView = binding.counterTextView;
            quantityTextView.setText(String.valueOf(quantity));
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("Login to account");

        // Setting Dialog Message
        alertDialog.setMessage("Login to add products to your cart");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.user);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public boolean onBackPressed() {

        // Go back to the default fragment
        DashboardFragment defaultFragment = new DashboardFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();

        return true;
    }
}