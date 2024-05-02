package com.example.mall.activities;
import static com.example.mall.activities.UserProfileActivity.USER_PHOTO_URI_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mall.fragments.MainFragment;
import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.dialogues.AllCategoriesDialog;
import com.example.mall.dialogues.LicencesDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView userName;
    private ImageView userPhoto;
    private SharedPreferences preferences;
    static final String ALL_CATEGORIES = "categories";
    static final String CALLING_ACTIVITY = "calling_activity";
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
//        } else {
//            currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        FirebaseUser updatedUser = mAuth.getCurrentUser();
//                        if (updatedUser != null && !updatedUser.isEmailVerified()) {
//                            Toast.makeText(getApplicationContext(), "Your account has been deleted. Please sign in again.", Toast.LENGTH_SHORT).show();
//                            mAuth.signOut();
//                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                            finish();
//                        }
//                    } else {
//
//                    }
//                }
//            });
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        initViews();
        preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String userNameValue = preferences.getString("user_name", "");
        if (userNameValue.isEmpty()) {
            userName.setText("New User");
        } else {
            userName.setText(userNameValue);
        }
        String userPhotoUrl = preferences.getString("user_photo", "");
        if(!userPhotoUrl.isEmpty()){
            Glide.with(this).load(userPhotoUrl).into(userPhoto);
        }

        String savedImageUriString = preferences.getString(USER_PHOTO_URI_KEY, null);
        if (savedImageUriString != null) {
            Uri savedImageUri = Uri.parse(savedImageUriString);
            Glide.with(this).load(savedImageUri).into(userPhoto);
        }


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                userPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(profileIntent);
                    }
                });
                userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(profileIntent);
                    }
                });
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                userPhoto.setOnClickListener(null);
                userName.setOnClickListener(null);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.ic_search:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.ic_home:
                        break;
                    case R.id.categories:
                        AllCategoriesDialog dialog = new AllCategoriesDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategories(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY, "main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getFragmentManager(), "all categories dialog");
                        break;
                    case R.id.about:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("About us")
                                .setMessage("Developed by S.E.M. Visit for more.")
                                .setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create().show();
                        break;

                    case R.id.terms:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Terms")
                                .setMessage("There are no terms")
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create().show();
                        break;

                    case R.id.licences:
                        LicencesDialog licencesDialog = new LicencesDialog();
                        licencesDialog.show(getSupportFragmentManager(), "licences dialog started");
                        break;

                    case R.id.settings:
                        Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        settingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(settingIntent);
                        break;
                    case R.id.orders:
                        Intent ordersIntent = new Intent(MainActivity.this, OrdersActivity.class);
                        ordersIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ordersIntent);
                        break;
                    case R.id.map:
                        Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mapIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();

    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);
        userPhoto = headerView.findViewById(R.id.userPhoto);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}