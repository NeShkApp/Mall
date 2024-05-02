package com.example.mall.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mall.R;
import com.example.mall.databasefiles.Review;
import com.example.mall.Utils;
import com.example.mall.databasefiles.GroceryItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewDialog extends DialogFragment {
    private static final String TAG = "AddReviewDialog";

    public interface AddReview {
        void OnAddReviewResult(Review review);
    }

    static final String GROCERY_ITEM_KEY = "incoming_item";
    private AddReview addReview;
    private TextView txtItemName, txtWarning;
    private EditText edtUsername, edtUserReview;
    private Button btnAddReview;
    private SharedPreferences preferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        initViews(view);
        preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (null != bundle) {
            final GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != item) {
                txtItemName.setText(item.getName());
                edtUsername.setText(preferences.getString("user_name", ""));
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userName = edtUsername.getText().toString();
                        String userReview = edtUserReview.getText().toString();
                        String date = Utils.getCurrentNumericDate();
                        if (containsProfanity(userName) || containsProfanity(userReview)) {
                            Toast.makeText(getActivity(), "Please, don't say bad words, honey \uD83D\uDE01", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (userName.equals("") || userReview.equals("")) {
                            txtWarning.setVisibility(View.VISIBLE);
                            txtWarning.setText("Fill all the blanks");
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                //when we are getting reviews from firebase, we don't need the callback implementation
//                                addReview = (AddReview) getActivity();
//                                addReview.OnAddReviewResult(new Review(item.getId(), userName, userReview, date));

                                Review review = new Review(item.getId(), userName, userReview, date);
                                DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference("Reviews");
                                String reviewId = reviewsRef.push().getKey();
                                reviewsRef.child(reviewId).setValue(review)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "Review added successfully to Firebase");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e(TAG, "Error adding review to Firebase", e);
                                            }
                                        });
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private void initViews(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);
        edtUsername = view.findViewById(R.id.edtYourName);
        edtUserReview = view.findViewById(R.id.edtYourReview);
        btnAddReview = view.findViewById(R.id.btnAddRev);
    }
    private boolean containsProfanity(String text) {
        String[] profanityList = {"badword", "погане слово", "поганеслово", "блін"};

        for (String profanity : profanityList) {
            if (text.toLowerCase().contains(profanity)) {
                return true;
            }
        }
        return false;
    }

}
