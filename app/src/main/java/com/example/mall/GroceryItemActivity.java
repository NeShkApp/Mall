package com.example.mall;

import static com.example.mall.AddReviewDialog.GROCERY_ITEM_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview{
    private static final String TAG = "GroceryItemActivity";

    private boolean isBound;
    private TrackUserTime mService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) iBinder;
            mService = binder.getService();
            isBound = true;
            mService.setItem(incomingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };
    public static final String GROCERY_INCOMING_KEY = "incoming_key";
    private MaterialToolbar toolbar;

    private TextView txtName, txtPrice, txtDesc, txtAddReview;
    private RecyclerView recViewReviews;
    private Button btnAddToCart;
    private ImageView imgItem, firstEmptyStar, secondEmptyStar, thirdEmptyStar,
    firstFilledStar, secondFilledStar, thirdFilledStar;
    private RelativeLayout firstStarRelLay, secondStarRelLay, thirdStarRelLay;
    private GroceryItem incomingItem;
    private ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        initViews();
        setSupportActionBar(toolbar);
        adapter = new ReviewsAdapter();

        Intent intent = getIntent();
        if(null!=intent){
            incomingItem = intent.getParcelableExtra(GROCERY_INCOMING_KEY);
            if(null!=incomingItem){
                Utils.changeUserPoint(this, incomingItem, 1);
                txtName.setText(incomingItem.getName());
                txtDesc.setText(incomingItem.getDescription());
                txtPrice.setText(String.valueOf(incomingItem.getPrice())+"$");
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(imgItem);
                ArrayList<Review> reviews = Utils.getReviewsById(this, incomingItem.getId());
                recViewReviews.setAdapter(adapter);
                recViewReviews.setLayoutManager(new LinearLayoutManager(this));
                if(null!=reviews){
                    if(reviews.size()>0){
                        adapter.setReviews(reviews);
                    }
                }
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: cart items: " + Utils.getAllCartItems(GroceryItemActivity.this));
                        Utils.addItemToCart(GroceryItemActivity.this, incomingItem);
                        Intent intent = new Intent(GroceryItemActivity.this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                txtAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "add review");
                    }
                });

                handleRating();
            }
        }


    }

    private void handleRating() {
        switch(incomingItem.getRate()){
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }

        firstStarRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 1){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 1);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (1-incomingItem.getRate())*2);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });

        secondStarRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 2){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 2);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (2-incomingItem.getRate())*2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });

        thirdStarRelLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incomingItem.getRate() != 3){
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 3);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (3-incomingItem.getRate())*2);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });

    }

    private void initViews() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDescription);
        txtAddReview = findViewById(R.id.txtAddReview);
        recViewReviews = findViewById(R.id.recViewReviews);
        btnAddToCart = findViewById(R.id.btnAdd);
        imgItem = findViewById(R.id.image);
        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFilledStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFilledStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);
        firstStarRelLay = findViewById(R.id.firstStarRelLay);
        secondStarRelLay = findViewById(R.id.secondStarRelLay);
        thirdStarRelLay = findViewById(R.id.thirdStarRelLay);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void OnAddReviewResult(Review review) {
        Log.d(TAG, "OnAddReviewResult: new review: " + review);
        Utils.addReview(this, review);
        Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, 3);
        ArrayList<Review> reviews = Utils.getReviewsById(this, review.getGroceryItemId());
        if(null!=reviews){
            adapter.setReviews(reviews);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, TrackUserTime.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound){
            unbindService(connection);
        }
    }
}