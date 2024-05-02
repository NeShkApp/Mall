package com.example.mall.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.databasefiles.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnSignIn, btnSignOut;
    private FirebaseAuth mAuth;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUi(user);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.blablabla);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignOut = findViewById(R.id.btnSignOut);

        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                );
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                launcher.launch(signInIntent);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();
                    updateUi(null);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        
        updateUi(mAuth.getCurrentUser());
    }



    private void updateUi(FirebaseUser currentUser) {
        if(null == currentUser){
            textView.setText("Please login");
            btnSignOut.setEnabled(false);
            btnSignIn.setEnabled(true);
        }else{
            btnSignOut.setEnabled(true);
            btnSignIn.setEnabled(false);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            User user = new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail());
            databaseReference.child(currentUser.getUid()).setValue(user);
            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            String displayName = currentUser.getDisplayName();
            for (UserInfo userInfo : currentUser.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                }
            }
            editor.putString("user_name", displayName);

            String userPhotoUrl = null;
            if (currentUser.getPhotoUrl() != null) {
                userPhotoUrl = currentUser.getPhotoUrl().toString();
                editor.putString("user_photo", userPhotoUrl);
            }

            editor.apply();

            String text = "Username: " + currentUser.getDisplayName() + "\n\t" +
                    "Email: " + currentUser.getEmail() + "\n\t" +
                    "Photo: " + currentUser.getPhotoUrl();
            textView.setText(text);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}