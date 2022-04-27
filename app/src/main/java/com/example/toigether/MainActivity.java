package com.example.toigether;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.toigether.items.Org;
import com.example.toigether.items.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.toigether", MODE_PRIVATE);

        check();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.tabLayoutFragment)
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                else
                    bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getBoolean("firstRun", true)) {
            Log.e("Checking firstRun", " is working");
            prefs.edit().putBoolean("firstRun", false).apply();
            openActivityWelcome();
        }
    }

    public void openActivityWelcome() {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    private void check () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("organizations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Org organization = document.toObject(Org.class);

                                Log.e("AJAJAJA", organization.toString());
                            }
                        } else {
                            Log.e("AJAJAJA", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}