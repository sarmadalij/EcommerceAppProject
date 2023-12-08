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

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.SignUpActivity;
import com.sarmadali.ecommerceappproject.databinding.FragmentProductDetailsBinding;
import com.squareup.picasso.Picasso;


public class ProductDetailsFragment extends Fragment {

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    FragmentProductDetailsBinding binding;

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

        binding.buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getContext(), "User is Signed In", Toast.LENGTH_SHORT).show();
                } else {
                    // No user is signed in
                    showDialog();
                    Toast.makeText(getContext(), "No Account Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    private int quantity = 1;

    public void incrementQuantity() {
        quantity += 1; // Increase the quantity by 1
        // Update the UI with the new quantity
        // For example, you can display the quantity in a TextView

        TextView quantityTextView = binding.counterTextView;
        quantityTextView.setText(String.valueOf(quantity));
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity -= 1; // Decrease the quantity by 1
            // Update the UI with the new quantity
            // For example, you can display the quantity in a TextView
            TextView quantityTextView = binding.counterTextView;
            quantityTextView.setText(String.valueOf(quantity));
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("Create Account");

        // Setting Dialog Message
        alertDialog.setMessage("Create Account to add product");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.user);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
//                Toast.makeText(getContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}