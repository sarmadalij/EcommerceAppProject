package com.sarmadali.ecommerceappproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sarmadali.ecommerceappproject.Admin.UploadProducts;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.Models.UsersModel;
import com.sarmadali.ecommerceappproject.databinding.ActivitySignUpBinding;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_PHOTO = 1;
    Uri imageUri;
    private StorageReference fStorageRef;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabaseRef;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();
        fStorageRef = FirebaseStorage.getInstance().getReference("userProfile");
        fDatabaseRef = FirebaseDatabase.getInstance().getReference("userDetails");

        //go to login activity
        binding.loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        //sign up button
        binding.RegImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUserToFirebase();

            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    //upload image to imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(binding.profileImage);
        }
    }


    //image chose option
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_PHOTO);
    }
    //add User data to Firebase

    // Show a ProgressDialog
    ProgressDialog progressDialog;
    private void addUserToFirebase() {

        if (imageUri != null){

            StorageReference fileReference = fStorageRef.child(System.currentTimeMillis()+
                    "."+getFileExtension(imageUri));

            String name = binding.RegName.getText().toString();
            String email = binding.RegEmail.getText().toString();
            String confirmPass = binding.RegConfirmPassword.getText().toString();
            String pass = binding.RegPass.getText().toString();

            // Validate input starts

            if (TextUtils.isEmpty(name)) {
                binding.RegName.setError("Name cannot be empty");
                return; // Stop execution if name is empty
            }
            if (TextUtils.isEmpty(email)) {
                binding.RegEmail.setError("Email cannot be empty");
                return; // Stop execution if email is empty
            }

            // Check if email already exists

            fAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {

                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {


                    if (task.isSuccessful()) {

                        SignInMethodQueryResult result = task.getResult();
                        if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                            // Email is already registered
                            binding.RegEmail.setError("This email is already registered");
                            progressDialog.dismiss();
                            return; // Stop execution

                        } else {
                            // Email is not registered, continue with the registration process

                            if (TextUtils.isEmpty(pass)) {
                                binding.RegPass.setError("Password cannot be empty");
                                return; // Stop execution if password is empty
                            }

                            if (pass.length() < 6) {
                                binding.RegPass.setError("Password must be at least 6 characters long");
                                return; // Stop execution if password is less than 6 characters
                            }

                            if (TextUtils.isEmpty(confirmPass)) {
                                binding.RegConfirmPassword.setError("This field cannot be empty");
                                return; // Stop execution if password confirmation is empty
                            }

                            if (!pass.equals(confirmPass)) {
                                binding.RegConfirmPassword.setError("Passwords do not match");
                                return; // Stop execution if passwords do not match
                            }
                            progressDialog.show();
                            //image in database and storage
                            fileReference.putFile(imageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            //store image uri and other data in database
                                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    //user profile uri to string
                                                    String userProfileUri = uri.toString();

                                                    fAuth.createUserWithEmailAndPassword(
                                                            email,
                                                            confirmPass
                                                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            //user data
                                                            UsersModel userData = new UsersModel(
                                                                    userProfileUri,
                                                                    name,
                                                                    email,
                                                                    pass,
                                                                    confirmPass
                                                            );

                                                            //to get the UID of user from AuthResult
                                                            String id;
                                                            try {
                                                                id = task.getResult().getUser().getUid();
                                                                //setting values in realtime database
                                                                fDatabaseRef
                                                                        //we can use id and username or another string as we
                                                                        // want in the below child to name the user node.
                                                                        .child(id)
                                                                        .setValue(userData);

                                                                Toast.makeText(SignUpActivity.this, "User Created Successfully",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }catch (Exception e){
//                                                                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                                                binding.RegEmail.setError("Email already exists");
                                                            }
                                                            //to log out the user by default after registration
                                                            fAuth.signOut();
                                                            progressDialog.dismiss();

                                                        }
                                                    });

                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                        }
                                    });
                        }

                    } else {
                        // Handling the exception if the task is not successful
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthUserCollisionException) {
                            // Email is already in use by another account
                            binding.RegEmail.setError("This email is already registered");
                            progressDialog.dismiss();
                        } else {
                            // Handle other exceptions
                            if (exception != null) {
                                Toast.makeText(SignUpActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }
                }
            });
            //check on email ends here

            //validate input ends
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    //get file extension
    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
}