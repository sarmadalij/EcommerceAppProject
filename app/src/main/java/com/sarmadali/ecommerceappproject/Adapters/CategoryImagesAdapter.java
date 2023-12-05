package com.sarmadali.ecommerceappproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarmadali.ecommerceappproject.Models.CategoryModel;
import com.sarmadali.ecommerceappproject.R;

import java.util.ArrayList;

public class CategoryImagesAdapter extends RecyclerView.Adapter<CategoryImagesAdapter.ViewHolder> {

    ArrayList<CategoryModel> list;
    Context context;

    public CategoryImagesAdapter(ArrayList<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //we will inflate the layout in this method
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_category, parent, false);
        return new ViewHolder(view);
    }

    // we will set the values of image and text here, in this method
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel categoryModel = list.get(position);

        //setting data
        holder.cImage.setImageResource(categoryModel.getCatgimage());
        holder.cName.setText(categoryModel.getCatgText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cImage;
        TextView cName;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cImage = itemView.findViewById(R.id.categoryimg);
            cName = itemView.findViewById(R.id.categoryname);

        }
    }
}
