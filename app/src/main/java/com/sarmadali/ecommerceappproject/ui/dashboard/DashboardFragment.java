package com.sarmadali.ecommerceappproject.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Adapters.CategoryImagesAdapter;
import com.sarmadali.ecommerceappproject.Adapters.ProductAdapter;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.CategoryModel;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DatabaseReference refDatabase;
    private ArrayList<ProductDetails> plist;
    private ProductAdapter pAdapter;

    public DashboardFragment() {
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        //slideshow images start
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://img.freepik.com/free-psd/special-sales-banner-template_23-2148975924.jpg", null));
        slideModels.add(new SlideModel("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/clothing-store-banner-design-template-e7332aaf6402c88cb4623bf8eb6f97e2_screen.jpg?ts=1620867237", null));
        slideModels.add(new SlideModel("https://t3.ftcdn.net/jpg/04/65/46/52/360_F_465465254_1pN9MGrA831idD6zIBL7q8rnZZpUCQTy.jpg", null));
        slideModels.add(new SlideModel("https://img.freepik.com/premium-vector/fashion-sale-social-media-facebook-cover-banner-template_123633-573.jpg", null));

        binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        //slideshow images end

        //category images recyclerview starts
        ArrayList<CategoryModel> cList = new ArrayList<>();

        cList.add(new CategoryModel(R.drawable.categorya, "Men's \nFormal"));
        cList.add(new CategoryModel(R.drawable.categoryb, "Women's \nFormal"));
        cList.add(new CategoryModel(R.drawable.categoryc, "Baby \nSpecial"));
        cList.add(new CategoryModel(R.drawable.categoryd, "Casual \nShoe"));
        cList.add(new CategoryModel(R.drawable.categorye, "Sports \nWear"));
        cList.add(new CategoryModel(R.drawable.categoryf, "Men's \nKurta"));
        cList.add(new CategoryModel(R.drawable.categorya, "Men's \nFormal"));
        cList.add(new CategoryModel(R.drawable.categoryb, "Women's \nFormal"));
        cList.add(new CategoryModel(R.drawable.categoryc, "Baby \nSpecial"));
        cList.add(new CategoryModel(R.drawable.categoryd, "Casual \nShoe"));
        cList.add(new CategoryModel(R.drawable.categorye, "Sports \nWear"));
        cList.add(new CategoryModel(R.drawable.categoryf, "Men's \nKurta"));


        CategoryImagesAdapter adapter = new CategoryImagesAdapter(cList, getContext());

        if (adapter != null && binding != null) {
            binding.recyclerviewCategory.setAdapter(adapter);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerviewCategory.setLayoutManager(linearLayoutManager);

        //auto slide category starts
        final int time = 2000; // it's the delay time for sliding between items in recyclerview
        //The LinearSnapHelper will snap the center of the target child view to the center of the
        // attached RecyclerView
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(binding.recyclerviewCategory);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(binding.recyclerviewCategory, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(binding.recyclerviewCategory, new RecyclerView.State(), 0);
                }
            }
        }, 0, time);

        //auto slide category ends
        //category images recyclerview ends

        //product recyclerview starts
        plist = new ArrayList<>();
        refDatabase = FirebaseDatabase.getInstance().getReference("productDetails");

        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductDetails productDetails = postSnapshot.getValue(ProductDetails.class);
                    plist.add(productDetails);
                }
                pAdapter = new ProductAdapter(plist, getContext(), new ProductAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ProductDetails product) {

                        if (listener != null) {
                            listener.onItemClick(product);
                        }
                    }
                });

                if (pAdapter != null && binding != null) {

                    binding.recyclerviewProducts.setAdapter(pAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerviewProducts.setLayoutManager(staggeredGridLayoutManager);
        //product recyclerview ends

        return binding.getRoot();
    }

    private ProductAdapter.OnItemClickListener listener;

    //to attach product detail page
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //to perform fragment transactions

        listener = (Dashboard) getActivity();

    }

}