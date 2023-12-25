package com.sarmadali.ecommerceappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sarmadali.ecommerceappproject.Models.OrderProducts;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersAdapterOne extends RecyclerView.Adapter<MyOrdersAdapterOne.ViewHolder> {

    Context context;
    ArrayList<OrderProducts> plist;
    ArrayList<ProductDetails> pplist;

    //constructor
    public MyOrdersAdapterOne(Context context, ArrayList<OrderProducts> plist) {
        this.context = context;
        this.plist = plist;
    }

    public void setOrderList(ArrayList<ProductDetails> orderList) {
        this.pplist = orderList;
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_my_orders, parent, false);
        return new MyOrdersAdapterOne.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderProducts orderProducts = plist.get(position);

        // Bind order details to the views in your order item layout
        holder.bindOrderDetails(orderProducts);

        // Bind product details to the RecyclerView within the order item layout
        holder.bindProductList(orderProducts.getProductList());

    }

    @Override
    public int getItemCount() {

        return plist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewOrderName;
        private TextView textViewOrderEmail;
        private TextView textViewOrderPhone;
        private TextView textViewOrderAddress;
        private TextView textViewOrderTotal;

        private RecyclerView recyclerViewProducts;
        private OrderProductAdapter productAdapter;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrderName = itemView.findViewById(R.id.textViewOrderName);
            textViewOrderEmail = itemView.findViewById(R.id.textViewOrderEmail);
            textViewOrderPhone = itemView.findViewById(R.id.textViewOrderPhone);
            textViewOrderAddress = itemView.findViewById(R.id.textViewOrderAddress);
            textViewOrderTotal = itemView.findViewById(R.id.textViewTotal);

            recyclerViewProducts = itemView.findViewById(R.id.recyclerViewProducts);
            productAdapter = new OrderProductAdapter(context);  // Create a separate adapter for products

            // Set up RecyclerView for products
            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewProducts.setAdapter(productAdapter);

        }

        public void bindOrderDetails(OrderProducts order) {
            textViewOrderName.setText("Order Name: " + order.getOrderUName());
            textViewOrderEmail.setText("Order Email: " + order.getOrderUEmail());
            textViewOrderPhone.setText("Order Phone: " + order.getOrderUPhone());
            textViewOrderAddress.setText("Order Address: " + order.getOrderUAddress());
            textViewOrderTotal.setText("Order Total: " + order.getOrderTotalPrice());
        }

        public void bindProductList(ArrayList<ProductDetails> productList) {
            productAdapter.setDataList(productList);
        }

    }

}
