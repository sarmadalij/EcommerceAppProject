package com.sarmadali.ecommerceappproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.navigation.NavigationBarView;
import com.sarmadali.ecommerceappproject.Adapters.ProductAdapter;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.databinding.ActivityDashboardBinding;
import com.sarmadali.ecommerceappproject.ui.AccountUser;
import com.sarmadali.ecommerceappproject.ui.ProductDetailsFragment;
import com.sarmadali.ecommerceappproject.ui.dashboard.DashboardFragment;
import com.sarmadali.ecommerceappproject.ui.mycart.MyCartsFragment;

public class Dashboard extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private ActivityDashboardBinding binding;
    androidx.appcompat.widget.Toolbar toolbar;

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



                if (item.getItemId() == R.id.navigation_dashboard)
                {
                    DashboardFragment dashboardFragment = new DashboardFragment();
//                    getSupportFragmentManager().beginTransaction().remove(homeFragment).commitAllowingStateLoss();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_dashboard , dashboardFragment);
                    transaction.commit();
                    Toast.makeText(Dashboard.this, "Welcome to Dashboard", Toast.LENGTH_SHORT).show();
//                    this.getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                }
                else if (item.getItemId() == R.id.navigation_mycarts)
                {
                    MyCartsFragment myCartsFragment = new MyCartsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_dashboard , myCartsFragment);
                    transaction.commit();

                    Toast.makeText(Dashboard.this, "Welcome to My carts", Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId() == R.id.navigation_profile)
                {
                    AccountUser accountUser = new AccountUser();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_activity_dashboard , accountUser);
                    transaction.commit();

                    Toast.makeText(Dashboard.this, "Welcome to Your Account", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

//        navView.setItemIconTintList(null);
        //default nav ends

        //toolbar
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

    }


    @Override
    public void onItemClick(ProductDetails product) {

        // Open ProductDetailFragment and pass the product as an argument
        ProductDetailsFragment fragment = new ProductDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("product", product);
        fragment.setArguments(args);

        // Use FragmentManager to replace the current fragment with the ProductDetailFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_dashboard, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    //for fragment back pressed
    public interface IOnBackPressed {
        boolean onBackPressed();
    }

    //for fragment back pressed
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_dashboard);

        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}