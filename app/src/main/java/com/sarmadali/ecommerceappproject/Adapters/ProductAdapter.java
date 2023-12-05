package com.sarmadali.ecommerceappproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<ProductDetails> plist;
    Context context;
    private OnItemClickListener listener;
    public ProductAdapter(ArrayList<ProductDetails> plist, Context context, OnItemClickListener listener) {
        this.plist = plist;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_dashboard_products, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ProductDetails productModel = plist.get(position);
        //setting data
        Picasso.get().load(productModel.getProductImageUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.pImage);
        holder.pName.setText(productModel.getProductName());
        holder.buybtn.setText("Rs " + productModel.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pImage;
        TextView pName;
        Button buybtn;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pImage = itemView.findViewById(R.id.productimg);
            pName = itemView.findViewById(R.id.productname);
            buybtn = itemView.findViewById(R.id.buttonbuy);

            //on item clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            ProductDetails product = plist.get(position);
                            listener.onItemClick(product);
                        }
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(ProductDetails product);
    }

}

