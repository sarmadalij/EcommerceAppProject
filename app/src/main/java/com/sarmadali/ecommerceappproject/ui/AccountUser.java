package com.sarmadali.ecommerceappproject.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.ecommerceappproject.Dashboard;
import com.sarmadali.ecommerceappproject.Models.UsersModel;
import com.sarmadali.ecommerceappproject.R;
import com.sarmadali.ecommerceappproject.Seller.UploadProducts;
import com.sarmadali.ecommerceappproject.databinding.FragmentAccountUserBinding;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountUser extends Fragment implements Dashboard.IOnBackPressed {

    public AccountUser() {
        // Required empty public constructor
    }

    private FirebaseUser fUser;
    FragmentAccountUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentAccountUserBinding.inflate(inflater, container, false);

        //ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);


        //sign out button starts
        binding.buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread() {
                    public void run() {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.show();
                                }
                            });
                            sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getContext(), Dashboard.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                };
                thread.start();

            }
        });
        //sign out button ends

        //change title
        Dashboard activity = (Dashboard) getActivity();
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar1);
        toolbar.setTitle("My Profile");

        //get data from firebase database
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null) {
            // Assuming you have a reference to your Firebase database
            DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference()
                    .child("userDetails").child(fUser.getUid());

            currentUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    UsersModel currentUserModel = snapshot.getValue(UsersModel.class);
                    if (currentUserModel != null) {

                        Uri profileImage = Uri.parse(currentUserModel.getProfilePic());
                        Picasso.get().load(profileImage).placeholder(R.drawable.user)
                                .into(binding.profileAccount);

                        String profileName = currentUserModel.getUserName();
                        binding.profileName.setText(profileName);

                        String profileEmail = currentUserModel.geteMail();
                        binding.profileEmail.setText(profileEmail);
//                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        final ArrayList<UsersModel> userList = new ArrayList<>();

        binding.accountMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUser cartUser = new CartUser();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_dashboard, cartUser);
                transaction.commit();
            }
        });

        binding.profileMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMyOrders myOrders = new FragmentMyOrders();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_dashboard, myOrders);
                transaction.commit();
            }
        });


        binding.becomeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadProducts.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        DashboardFragment defaultFragment = new DashboardFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_dashboard, defaultFragment)
                .commit();
        return true;
    }
}