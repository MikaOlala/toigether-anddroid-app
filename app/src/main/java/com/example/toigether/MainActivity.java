package com.example.toigether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        DB db = new DB();
        Log.e("CHECKING DB CONNECTION", db.open() + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getBoolean("firstRun", true)) {
//            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("name", "1");
//            editor.apply();
            Log.e("Checking firstRun", " is working");
            prefs.edit().putBoolean("firstRun", false).apply();
            openActivityWelcome();
        }
    }

    public void openActivityWelcome() {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
}