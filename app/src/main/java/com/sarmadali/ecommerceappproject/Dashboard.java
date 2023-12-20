package com.sarmadali.ecommerceappproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sarmadali.ecommerceappproject.Adapters.ProductAdapter;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.databinding.ActivityDashboardBinding;
import com.sarmadali.ecommerceappproject.ui.AccountUser;
import com.sarmadali.ecommerceappproject.ui.CartUser;
import com.sarmadali.ecommerceappproject.ui.EmptyCart;
import com.sarmadali.ecommerceappproject.ui.NoUserAccount;
import com.sarmadali.ecommerceappproject.ui.ProductDetailsFragment;
import com.sarmadali.ecommerceappproject.ui.SearchFragment;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;


public class Dashboard extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private ActivityDashboardBinding binding;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         //default nav starts
        BottomNavigationView navView = findViewById(R.id.bottomnav);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //dashboard
                if (item.getItemId() == R.id.navigation_dashboard)
                {
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_dashboard , dashboardFragment);
                    transaction.commit();

                    resetToolbar();
                }
                //search products
                else if (item.getItemId() == R.id.navigation_search) {

                    SearchFragment searchFragment = new SearchFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_dashboard , searchFragment);
                    transaction.commit();
                }
                //my carts
                else if (item.getItemId() == R.id.navigation_mycarts)
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        CartUser myCartsFragment = new CartUser();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_activity_dashboard , myCartsFragment);
                        transaction.commit();

                    } else {
                        // No user is signed in
                        EmptyCart myCartsFragment = new EmptyCart();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_activity_dashboard , myCartsFragment);
                        transaction.commit();

                    }


                }
                //my profile
                else if (item.getItemId() == R.id.navigation_profile) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        AccountUser accountUser = new AccountUser();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_activity_dashboard , accountUser);
                        transaction.commit();

                    } else {
                        // No user is signed in
                        NoUserAccount accountUser = new NoUserAccount();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_activity_dashboard , accountUser);
                        transaction.commit();
                    }
                }

                return true;
            }
        });

        setSupportActionBar(binding.toolbar1);

        bottomNavigationView = binding.bottomnav;
    }

    // Reset the toolbar to its original state
    private void resetToolbar() {
        getSupportActionBar().setTitle("Buzz Sell");
    }

    //for product details
    @Override
    public void onItemClick(ProductDetails product) {

        // Open ProductDetailFragment and pass the product as an argument
        ProductDetailsFragment fragment = new ProductDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("product", product);
        fragment.setArguments(args);

        // Using FragmentManager to replace the current fragment with the ProductDetailFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_dashboard, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    //Interface to implement for fragments back pressed
    public interface IOnBackPressed {
        boolean onBackPressed();
    }

    //for fragment back pressed
    private BottomNavigationView bottomNavigationView;
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_dashboard);

        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }

        // If user is in the Profile fragment and press back,
        // user should behave as if the Dashboard is selected in the bottom navigation
        if (bottomNavigationView.getSelectedItemId() == R.id.navigation_profile) {

            bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_mycarts) {

            bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);

        }  else {
            super.onBackPressed();
        }

    }

}