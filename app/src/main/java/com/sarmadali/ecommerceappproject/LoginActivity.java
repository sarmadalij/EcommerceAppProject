package com.sarmadali.ecommerceappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sarmadali.ecommerceappproject.Admin.UploadProducts;
import com.sarmadali.ecommerceappproject.databinding.ActivityLoginBinding;
import com.sarmadali.ecommerceappproject.databinding.ActivitySignUpBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       //register text to move to the the sign up
        binding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        //login button
        binding.LogImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.LogEmail.getText().toString();
                String password = binding.LogPass.getText().toString();

                //validate input
                if (TextUtils.isEmpty(email)) {
                    binding.LogEmail.setError("Email cannot be empty");
                    return; // Stop execution if email is empty
                }

                if (TextUtils.isEmpty(password)) {
                    binding.LogPass.setError("Password cannot be empty");
                    return; // Stop execution if password is empty
                }

                // Show a ProgressDialog
                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Processing...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //validate input ends
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressDialog.show();
                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("TAG", "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
////                                    updateUI(user);
                                    progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(intent);
                                finish();

                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(LoginActivity.this, "Sign In failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                    Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });

        //go to admin/upload products page
        binding.buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UploadProducts.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
//            user is signed in
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
    }
        super.onStart();
    }
}