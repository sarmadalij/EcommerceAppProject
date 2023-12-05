package com.sarmadali.ecommerceappproject.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentProductDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;


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
}