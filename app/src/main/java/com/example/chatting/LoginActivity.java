package com.example.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button loginbutton=findViewById(R.id.LoginButton);
        EditText passwordtext=findViewById(R.id.password);
        EditText usernametext=findViewById(R.id.username);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernametext.getText().toString().trim();
                String password=passwordtext.getText().toString().trim();

                if (password.isEmpty() && username.isEmpty()){
                    usernametext.setError("Enter your username");
                    passwordtext.setError("Enter your password");
                    Toast.makeText(getApplicationContext(), "Enter username and password",Toast.LENGTH_SHORT).show();
                    passwordtext.requestFocus();
                    usernametext.requestFocus();
                }
                else if (password.isEmpty()) {
                    passwordtext.setError("Enter your password");
                    Toast.makeText(getApplicationContext(), "Enter password",Toast.LENGTH_SHORT).show();
                    passwordtext.requestFocus();
                }
                else if(username.isEmpty()){
                    usernametext.setError("Enter your username");
                    Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                    usernametext.requestFocus();
                }
                else {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}