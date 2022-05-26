package com.example.toigether;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toigether.items.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Profile extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseData db = new FirebaseData();
    private TextView name;
    private TextView phone;
    private ImageView avatar;
    private Uri imageUri;
    private User userObject;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        TextView logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        avatar = findViewById(R.id.avatar);

        setUser();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                finish();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void setUser() {
        db.getUser(auth.getCurrentUser().getEmail(), new FirebaseData.OnGetUserListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(User user) {
                userObject = user;

                String number = "";
                int index = 1;
                if (user.getPhone().startsWith("+"))
                    index++;

                number = user.getPhone().substring(0, index) + " ";
                number = number + user.getPhone().substring(index, index+3) + " ";
                number = number + user.getPhone().substring(index+3, index+5) + " ";
                number = number + user.getPhone().substring(index+5, index+7) + " ";
                number = number + user.getPhone().substring(index+7, index+10);

                name.setText(user.getName());
                phone.setText(number);
                if (user.getAvatar()!=null) {
                    if(user.getAvatar().length()!=0)
                        Picasso.get().load(Uri.parse(user.getAvatar())).into(avatar);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();
            avatar.setImageURI(imageUri);

            uploadPicture();
        }
    }
    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        
        StorageReference riversRef = storageReference.child("avatars/" + UUID.randomUUID().toString());

        riversRef.putFile(imageUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();

                    if(taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                userObject.setAvatar(uri.toString());
                                db.editAvatar(userObject);
                            }
                        });
                    }
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
                }
            });
    }

}