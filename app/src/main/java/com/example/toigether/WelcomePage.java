package com.example.toigether;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class WelcomePage extends AppCompatActivity {
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        ImageView img = findViewById(R.id.welcomeImage);
        TextView title = findViewById(R.id.welcomeTitle);
        TextView text = findViewById(R.id.welcomeText);
        Button button = findViewById(R.id.nextButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (page) {
                    case 1: {
                        img.setImageResource(R.drawable.about);
                        title.setText("Здесь есть всё!");
                        text.setText("Свадьбы, Дни рождения, корпоративы и многое другое в одном приложении!");
                        break;
                    }
                    case 2: {
                        img.setImageResource(R.drawable.simple);
                        title.setText("Всё очень просто :)");
                        text.setText("Выбирайте нужное вам событие, организаторскую программу, цены и назначайте встречу с менеджерами в пару кликов!");
                        break;
                    }
                    case 3: {
                        openActivityMain();
                        break;
                    }
                }
                page++;
            }
        });
    }

    public void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
