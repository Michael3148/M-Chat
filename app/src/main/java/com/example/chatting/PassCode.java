package com.example.chatting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PassCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_pass_code);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText passcodetext=findViewById(R.id.passcode);
        Button passcodebutton=findViewById(R.id.LoginButton);

        passcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcode=passcodetext.getText().toString().trim();

                if(passcode.isEmpty()){
                    passcodetext.setError("Enter passcode");
                    Toast.makeText(getApplicationContext(),"Enter passcode",Toast.LENGTH_SHORT).show();
                }
                else {
                    finish();
                }
            }
        });
    }
    private boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed(); // Exit app
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        // Reset after 2 seconds
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000); // 2 seconds
    }
}