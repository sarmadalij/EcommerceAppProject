package com.sarmadali.ecommerceappproject.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sarmadali.ecommerceappproject.Models.ProductDetails;
import com.sarmadali.ecommerceappproject.databinding.ActivityUploadProductsBinding;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadProducts extends AppCompatActivity {

    private ActivityUploadProductsBinding binding;
    private static final int PICK_PHOTO = 1;
    Uri imageUri;

    private StorageReference fStorageRef;
    private DatabaseReference fDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUploadProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fStorageRef = FirebaseStorage.getInstance().getReference("productImages");
        fDatabaseRef = FirebaseDatabase.getInstance().getReference("productDetails");

        //add data to firebase
        binding.addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });

        //upload images
        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }
    // for upload image
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_PHOTO);
    }

    //upload to imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(binding.uploadImage);
        }
    }

    //for image upload to firebase

    private void uploadToFirebase(){

        if (imageUri != null){

            StorageReference fileReference = fStorageRef.child(System.currentTimeMillis()+
                    "."+getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(UploadProducts.this, "Upload Successful", Toast.LENGTH_SHORT).show();



                            //store image uri and other data in database

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String pImageName = binding.productName.getText().toString();
                                    String pUri = uri.toString();

                                    String pName = binding.productName.getText().toString();
                                    String pCateg = binding.productCategory.getText().toString();
                                    String pDesp = binding.productDesrciption.getText().toString();
                                    String pPrice = binding.productPrice.getText().toString();

                                    ProductDetails productDetails = new ProductDetails(pImageName.trim(), pUri,
                                            pName, pCateg, pDesp, pPrice);
                                    String uploadId = fDatabaseRef.push().getKey();
                                    fDatabaseRef.child(uploadId).setValue(productDetails);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadProducts.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    //for extension of file
    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}