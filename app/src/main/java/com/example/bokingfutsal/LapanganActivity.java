package com.example.bokingfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class LapanganActivity extends AppCompatActivity {
private Button galery, upload;
final private String databasePath = "Lappangan";
private EditText judul, fasilatas, deskrips, harga;
final private int imageRequetsCode = 7;
private Uri filePathUri;
    ProgressDialog progressDialog;
    ImageView imageView;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapangan);
        galery = findViewById(R.id.btngal);
        imageView = findViewById(R.id.imglap);
        judul = findViewById(R.id.judul);
        fasilatas = findViewById(R.id.fasilitas);
        deskrips = findViewById(R.id.des);
        harga = findViewById(R.id.harga);
        upload = findViewById(R.id.btnupload);
        progressDialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference(databasePath);
        storageReference = FirebaseStorage.getInstance().getReference();
        galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, imageRequetsCode);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageFileToFirebaseStorage();
            }
        });

    }
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == imageRequetsCode && i2 == Activity.RESULT_OK && intent != null && intent.getData() != null) {
            filePathUri = intent.getData();
            try {
                imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), filePathUri));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }
    public String GetFileExtension(Uri uri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(uri));
    }

    public void UploadImageFileToFirebaseStorage() {
        if (filePathUri == null){
            Toast.makeText(getApplicationContext(),"Photo Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle(" Data is Uploading...");
        progressDialog.setMessage(" Transfer Data Ke Server");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String Storage_Path = "FotoLapangan/";
        StorageReference storageReference2 = this.storageReference;
        storageReference2.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(filePathUri)).putFile(filePathUri).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() {

            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String key = databaseReference.push().getKey();
                progressDialog.dismiss();
                Toast.makeText(LapanganActivity.this.getApplicationContext(), "Data Berhasil di Upload", Toast.LENGTH_SHORT).show();
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                do {
                } while (!downloadUrl.isSuccessful());
                String data1 = judul.getText().toString().trim();
                String data2 = fasilatas.getText().toString().trim();
                String data3 = deskrips.getText().toString().trim();
                String data4 = harga.getText().toString().trim();
                Lapangan imageUploadInfo = new Lapangan(key,data1,data3,"",data2,data4, Objects.requireNonNull(String.valueOf(downloadUrl.getResult())));
                DatabaseReference databaseReference = LapanganActivity.this.databaseReference;
                databaseReference.child(key).setValue(imageUploadInfo);
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            @Override
            public void onFailure(Exception exc) {
                progressDialog.dismiss();
                Toast.makeText(LapanganActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener((OnProgressListener) new OnProgressListener<UploadTask.TaskSnapshot>() {

            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setTitle("Data is Uploading...");
            }
        });
}
}