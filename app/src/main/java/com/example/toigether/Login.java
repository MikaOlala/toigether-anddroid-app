package com.example.toigether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toigether.organizations.OrganizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private final FirebaseData db = new FirebaseData();
    private EditText login, password;
    private TextView warning;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        warning = findViewById(R.id.warning);
        TextView registration = findViewById(R.id.registration);
        TextView restoreText = findViewById(R.id.restoreText);
        View back = findViewById(R.id.back);
        Button enter = findViewById(R.id.enter);

        db.makeTextUnderlined(restoreText);

        if (getIntent().getExtras()!=null)
            orgId = getIntent().getExtras().getString("orgId");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity();
            }
        });
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    private void signIn() {
        String email = login.getText().toString();
        String password = this.password.getText().toString();

        if (email.equals("") || password.equals("")) {
            warning.setText(getResources().getString(R.string.login_null));
            warning.setVisibility(View.VISIBLE);
        }
        else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        if (orgId != null)
                            openActivityOrganization(orgId);
                        finish();
                    }
                    else {
                        warning.setText(getResources().getString(R.string.password_error));
                        warning.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(this, OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}