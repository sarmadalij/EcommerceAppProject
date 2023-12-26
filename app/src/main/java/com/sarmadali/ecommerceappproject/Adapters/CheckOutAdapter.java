package com.sarmadali.ecommerceappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductDetails> plist;

    //constructor
    public CheckOutAdapter(Context context, ArrayList<ProductDetails> plist) {
        this.context = context;
        this.plist = plist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_checkout, parent, false);
        return new CheckOutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDetails productModel = plist.get(position);

        //setting data
        Picasso.get().load(productModel.getProductImageUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.pImage);

        holder.pName.setText(productModel.getProductName());
        holder.pQuantity.setText(productModel.getProductQuantity());

    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pImage;
        TextView pName;
        TextView pQuantity;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pImage = itemView.findViewById(R.id.checkoutImage);
            pName = itemView.findViewById(R.id.checkoutPName);
            pQuantity = itemView.findViewById(R.id.chechoutPQuantity);
        }
    }
}
