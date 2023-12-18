package com.sarmadali.ecommerceappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{

    ArrayList<ProductDetails> plist;
    Context context;
    private FirebaseUser firebaseUser;

    //constructor
    public MyCartAdapter(ArrayList<ProductDetails> plist, Context context) {
        this.plist = plist;
        this.context = context;
    }

    //we will inflate the layout in this method
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_cart_item, parent, false);
        return new MyCartAdapter.ViewHolder(view);
    }

    // we will set the values of image and text here, in this method
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDetails productModel = plist.get(position);

        final int[] quant = {Integer.parseInt(productModel.getProductQuantity())};

        holder.pQuantity.setText(productModel.getProductQuantity());
        int productPrice = Integer.parseInt(productModel.getProductPrice());
        int productQuantity = Integer.parseInt(productModel.getProductQuantity());

        int total = (productPrice*productQuantity);

        String totalPrice = String.valueOf(total);

        holder.pTotalPrice.setText(totalPrice);

        //for add quantity
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quant[0] += 1;

                TextView quantityTextView = holder.pQuantity;
                quantityTextView.setText(String.valueOf(quant[0]));

                //
                holder.pQuantity.setText(String.valueOf(quant[0]));

                int productPrice = Integer.parseInt(productModel.getProductPrice());
                int productQuantity = quant[0];

                int total = (productPrice*productQuantity);

                String totalPrice = String.valueOf(total);

                holder.pTotalPrice.setText(totalPrice);
            }
        });

        //for subtract quantity
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quant[0] > 1) {

                    quant[0] -= 1;

                    TextView quantityTextView = holder.pQuantity;
                    quantityTextView.setText(String.valueOf(quant[0]));

                    //
                    holder.pQuantity.setText(String.valueOf(quant[0]));

                    int productPrice = Integer.parseInt(productModel.getProductPrice());
                    int productQuantity = quant[0];

                    int total = (productPrice*productQuantity);

                    String totalPrice = String.valueOf(total);

                    holder.pTotalPrice.setText(totalPrice);
                }
            }
        });

        //setting data
        Picasso.get().load(productModel.getProductImageUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.pImage);

        holder.pName.setText(productModel.getProductName());
        holder.pPrice.setText(productModel.getProductPrice());

        //delete product

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                plist.clear();
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Assuming your data list is of type List<YourDataType>
                    ProductDetails selectedItem = plist.get(position);

                    // Assuming you have a unique document ID for each item
                    String documentId = selectedItem.getProductName(); // Replace with your actual document ID retrieval logic

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String currentId = firebaseUser.getUid();
                    // Assuming you have a DatabaseReference reference initialized
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("userDetails")
                            .child(currentId)
                            .child("cartProducts");

                    // Delete the item from firebase
                    cartRef.child(productModel.getProductName()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(v.getContext(), "Product Removed Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView pImage;
        TextView pName;
        TextView pPrice;
        TextView pQuantity;
        TextView pTotalPrice;

        androidx.appcompat.widget.AppCompatButton btnAdd, btnSub;
        ImageView delete;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pImage = itemView.findViewById(R.id.productCartImage);
            pName = itemView.findViewById(R.id.productCartName);
            pPrice = itemView.findViewById(R.id.productCartUnitPrice);
            pQuantity = itemView.findViewById(R.id.counterTextViewcart);
            pTotalPrice = itemView.findViewById(R.id.cartTotalProductPrice);

            btnAdd = itemView.findViewById(R.id.incrementButtoncart);
            btnSub = itemView.findViewById(R.id.decrementButtoncart);

            delete = itemView.findViewById(R.id.cartProductDelete);

        }
    }
}
