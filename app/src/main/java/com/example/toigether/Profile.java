package com.example.toigether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toigether.items.User;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseData db = new FirebaseData();
    private TextView name;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        setUser();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                finish();
            }
        });
    }

    private void setUser() {
        db.getUser(auth.getCurrentUser().getEmail(), new FirebaseData.OnGetUserListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(User user) {
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
            }
        });
    }

}