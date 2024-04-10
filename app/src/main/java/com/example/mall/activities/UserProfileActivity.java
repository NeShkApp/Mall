package com.example.mall.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mall.MainActivity;
import com.example.mall.R;
import com.example.mall.Utils;

public class UserProfileActivity extends AppCompatActivity {
    private EditText edtUserName, edtUserEmail, edtUserAddress, edtUserPhone;
    private TextView txtUploadPhoto;
    private ImageView imgUserPhoto;
    private Spinner spinner;
    private Button btnSaveChanges;
    private SharedPreferences preferences;

    private ActivityResultLauncher<Intent> launcher;
    public static final String USER_PHOTO_URI_KEY = "user_photo_uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        preferences = getSharedPreferences("user_data", MODE_PRIVATE);

        initViews();

        fillEditTextsFromPreferences();
        fillSpinnerAndSelectValue();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        Glide.with(this).load(selectedImageUri).into(imgUserPhoto);
                    } else {
                        Toast.makeText(UserProfileActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        txtUploadPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(intent);
        });

        btnSaveChanges.setOnClickListener(v -> {
            saveUserDataToPreferences();
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Перевірка, чи є зображення в SharedPreferences
        String savedImageUriString = preferences.getString(USER_PHOTO_URI_KEY, null);
        if (savedImageUriString != null) {
            // Якщо URI існує, завантажте його до ImageView
            Uri savedImageUri = Uri.parse(savedImageUriString);
            Glide.with(this).load(savedImageUri).into(imgUserPhoto);
        }

    }

    private void initViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtUserAddress = findViewById(R.id.edtUserAddress);
        edtUserPhone = findViewById(R.id.edtUserPhone);
        spinner = findViewById(R.id.spinner);
        btnSaveChanges = findViewById(R.id.btnUserSaveChanges);
        imgUserPhoto = findViewById(R.id.imgUserPhoto);
        txtUploadPhoto = findViewById(R.id.txtUploadPhoto);
    }

    private void fillEditTextsFromPreferences() {
        edtUserName.setText(preferences.getString("user_name", ""));
        edtUserEmail.setText(preferences.getString("user_email", ""));
        edtUserAddress.setText(preferences.getString("user_address", ""));
        edtUserPhone.setText(preferences.getString("user_phone_number", ""));
    }

    private void fillSpinnerAndSelectValue() {
        String[] genderArray = getResources().getStringArray(R.array.gender_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String defaultGender = preferences.getString("user_gender", "");
        int position = adapter.getPosition(defaultGender);
        spinner.setSelection(position);
    }

    private void saveUserDataToPreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", edtUserName.getText().toString());
        editor.putString("user_email", edtUserEmail.getText().toString());
        editor.putString("user_address", edtUserAddress.getText().toString());
        editor.putString("user_phone_number", edtUserPhone.getText().toString());
        editor.putString("user_gender", spinner.getSelectedItem().toString());
        editor.apply();

        // Збереження URI обраного зображення в SharedPreferences
        Uri imageUri = Utils.getImageUriFromImageView(imgUserPhoto);
        if (imageUri != null) {
            editor.putString(USER_PHOTO_URI_KEY, imageUri.toString());
            editor.apply();
        }

        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }
}
