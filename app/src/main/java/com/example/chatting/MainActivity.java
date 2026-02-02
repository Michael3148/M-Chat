package com.example.chatting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomnav = findViewById(R.id.bottomNavigationView);

        bottomnav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainerView, HomeFragment.class, null)
                        /*.setReorderingAllowed(true)
                        .addToBackStack("name")*/ //TODO : use this 2
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.group) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainerView, GroupFragment.class, null)
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.channel) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainerView, ChannelFragment.class, null)
                        .commit();
                return true;
            }
            return false;
        });


        // Add this: Load HomeFragment by default on first launch
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, HomeFragment.class, null)
                    .commit();
            bottomnav.setSelectedItemId(R.id.home);
        }
        NavigationView navigationView = findViewById(R.id.navigationView);

        View footerView = getLayoutInflater().inflate(R.layout.drawer_footer, navigationView, false);

        navigationView.addView(footerView);

        String username = getIntent().getStringExtra("USERNAME_KEY");

        Switch toggleSwitch = footerView.findViewById(R.id.switch_passcode);


        TextView longusername = footerView.findViewById(R.id.usernamelong);
        longusername.setText(username);

        ActivityResultLauncher<Intent> passCodeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // This is called when PassCode activity finishes
                    toggleSwitch.setChecked(false);
                }
        );

        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent intent = new Intent(this, PassCode.class);
                passCodeLauncher.launch(intent);
            }
        });

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        ConnectivityManager.NetworkCallback networkCallback =
                new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(@NonNull Network network) {
                        runOnUiThread(() -> showBackOnline());
                    }

                    @Override
                    public void onLost(@NonNull Network network) {
                        runOnUiThread(() -> showNoConnection());
                    }
                };

        NetworkRequest request = new NetworkRequest.Builder().build();
        connectivityManager.registerNetworkCallback(request, networkCallback);

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

    private void showNoConnection() {
        TextView banner = findViewById(R.id.networkStatusBanner);
        banner.setText("No connection");
        banner.setBackground(getResources().getDrawable(R.drawable.no_connection_layout));
        banner.setVisibility(View.VISIBLE);
    }

    private void showBackOnline() {
        TextView banner = findViewById(R.id.networkStatusBanner);
        banner.setText("Online");
        banner.setBackground(getResources().getDrawable(R.drawable.back_online_layout));
        banner.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            banner.setVisibility(View.GONE);
        }, 3000);
    }
}