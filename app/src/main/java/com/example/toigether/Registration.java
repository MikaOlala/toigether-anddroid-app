package com.example.toigether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    FirebaseData db = new FirebaseData();
    EditText login, phone, password, passwordConfirm;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login = findViewById(R.id.login);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        warning = findViewById(R.id.warning);
        TextView termsConditions = findViewById(R.id.termsConditions);
        View back = findViewById(R.id.back);
        Button create = findViewById(R.id.create);

        db.makeTextUnderlined(termsConditions);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser () {
        String email = login.getText().toString();
        String phone = this.phone.getText().toString();
        String password = this.password.getText().toString();
        String passwordConfirm = this.passwordConfirm.getText().toString();

        if(email.equals("") || phone.equals("") || password.equals("") || passwordConfirm.equals("")) {
            warning.setText(getResources().getString(R.string.login_null));
            warning.setVisibility(View.VISIBLE);
        }
        else if (!password.equals(passwordConfirm)) {
            warning.setText(getResources().getString(R.string.password_equal));
            warning.setVisibility(View.VISIBLE);
        }
        else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        finish();
                    else {
                        warning.setText(getResources().getString(R.string.user_exist));
                        warning.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }
}