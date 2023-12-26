package com.sarmadali.ecommerceappproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Adapters.ProductAdapter;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements Dashboard.IOnBackPressed {

    private FragmentSearchBinding binding;

    private DatabaseReference refDatabase;
    private ArrayList<ProductDetails> plist;
    private ProductAdapter pAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        //product recyclerview starts
        plist = new ArrayList<>();
        //adapter
        pAdapter = new ProductAdapter(plist, getContext(), new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductDetails product) {

                if (listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
        // Set up RecyclerView
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.searchCycle.setLayoutManager(staggeredGridLayoutManager);
        if (binding.searchCycle != null && pAdapter != null && binding != null) {
            binding.searchCycle.setAdapter(pAdapter);
        }

        // Set up Firebase
        refDatabase = FirebaseDatabase.getInstance().getReference("productDetails");
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductDetails productDetails = postSnapshot.getValue(ProductDetails.class);
                    plist.add(productDetails);
                }
                // Notify the adapter that the data set has changed
                pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //product recyclerview ends
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Filter the data based on newText and update the RecyclerView
                ArrayList<ProductDetails> filteredList = filterData(plist, newText);
                pAdapter.setDataList(filteredList);
                return true;
            }
        });

        setRetainInstance(true);

        //change title
        Dashboard activity = (Dashboard) getActivity();
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("Search");

        return binding.getRoot();
    }

    // Method to filter data based on the search query
    private ArrayList<ProductDetails> filterData(ArrayList<ProductDetails> dataList, String query) {
        ArrayList<ProductDetails> filteredList = new ArrayList<>();
        for (ProductDetails productDetails : dataList) {

            if (productDetails.getProductName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(productDetails);
            }
        }
        return filteredList;
    }

    private ProductAdapter.OnItemClickListener listener;

    //to attach product detail page
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Fragment transaction or access the FragmentManager here
        listener = (Dashboard) getActivity();

        if (context instanceof Dashboard) {
            listener = (Dashboard) getActivity();
        } else {
            throw new ClassCastException(context.toString() + " must implement Dashboard interface");

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Updated data when the fragment is resumed
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProductDetails productDetails = postSnapshot.getValue(ProductDetails.class);
                    plist.add(productDetails);
                }
                pAdapter.setDataList(plist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        clearSearchViewText();

    }

    private void clearSearchViewText() {

        if (binding.searchView != null) {
            binding.searchView.setQuery("", false);
            binding.searchView.clearFocus();
        }
    }


    @Override
    public boolean onBackPressed() {

        Intent intent = new Intent(getContext(), Dashboard.class);
        startActivity(intent);

        return true;
    }

}