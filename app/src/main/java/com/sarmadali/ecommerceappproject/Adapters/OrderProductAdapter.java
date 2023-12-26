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

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {

    private ArrayList<ProductDetails> productList;
    private Context context;

    public OrderProductAdapter(ArrayList<ProductDetails> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public OrderProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_myorders_two, parent, false);
        return new OrderProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDetails productModel = productList.get(position);

        //setting data
        Picasso.get().load(productModel.getProductImageUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.pImage);
        holder.pName.setText(productModel.getProductName());
        holder.pQuantity.setText(productModel.getProductQuantity());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pImage;
        TextView pName;
        TextView pQuantity;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pImage = itemView.findViewById(R.id.checkoutImageo);
            pName = itemView.findViewById(R.id.checkoutPNameo);
            pQuantity = itemView.findViewById(R.id.chechoutPQuantityo);
        }
    }

    public void setDataList(ArrayList<ProductDetails> newDataList) {
        this.productList = newDataList;
        notifyDataSetChanged();
    }
}
