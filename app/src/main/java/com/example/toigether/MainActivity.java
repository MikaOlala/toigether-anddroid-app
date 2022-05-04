package com.example.toigether;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.toigether", MODE_PRIVATE);

//        check();

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

//вывод всех причастных участников
//    private void check () {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("team_members").whereEqualTo("org_id", "6zp4DBeuPNI6PR7S1DWI")
//            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        TeamMember teamMember = document.toObject(TeamMember.class);
//                        Log.e("MainActivity check", teamMember.toString());
//                    }
//                }
//        });
//    }

}