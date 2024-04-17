package com.example.mall.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.adapters.GroceryItemAdapter;
import com.example.mall.databasefiles.GroceryItem;
import com.example.mall.dialogues.AllCategoriesDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    private static final String TAG = "SearchActivity";
    static final String ALL_CATEGORIES = "categories";
    static final String CALLING_ACTIVITY = "calling_activity";
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView txtFirstCategory, txtSecondCategory, txtThirdCategory, txtAllCategories;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        initBottomNavBar();
        setSupportActionBar(toolbar);

        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<GroceryItem> allItems = Utils.getAllItems(this);
        if(null!= allItems){
            adapter.setItems(allItems);
        }

        Intent intent = getIntent();
        if(null!=intent){
            String category = intent.getStringExtra("category");
            if(null!=category){
                ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
                if(null!=items){
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            }
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearch();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ArrayList<String> categories = Utils.getCategories(this);
        if(null!=categories){
            if(categories.size()>0){
                if(categories.size()==1){
                    showCategories(categories, 1);
                }else if(categories.size()==2){
                    showCategories(categories, 2);
                }else{
                    showCategories(categories, 3);
                }
            }
        }
        txtAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategories(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY, "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "all categories dialog");
            }
        });

    }

    private void showCategories(final ArrayList<String> categories, int i) {
        switch(i){
            case 1:
                txtFirstCategory.setVisibility(View.VISIBLE);
                txtFirstCategory.setText(categories.get(0));
                txtSecondCategory.setVisibility(View.GONE);
                txtThirdCategory.setVisibility(View.GONE);
                txtFirstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                break;
            case 2:
                txtFirstCategory.setVisibility(View.VISIBLE);
                txtFirstCategory.setText(categories.get(0));
                txtSecondCategory.setVisibility(View.VISIBLE);
                txtSecondCategory.setText(categories.get(1));
                txtThirdCategory.setVisibility(View.GONE);
                txtFirstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                txtSecondCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                break;
            case 3:
                txtFirstCategory.setVisibility(View.VISIBLE);
                txtFirstCategory.setText(categories.get(0));
                txtSecondCategory.setVisibility(View.VISIBLE);
                txtSecondCategory.setText(categories.get(1));
                txtThirdCategory.setVisibility(View.VISIBLE);
                txtThirdCategory.setText(categories.get(2));
                txtFirstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                txtSecondCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                txtThirdCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(2));
                        if(null!=items){
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private void initSearch() {
        if(!searchBox.getText().toString().equals("")){
            String name = searchBox.getText().toString();
            ArrayList<GroceryItem> foundedItems = Utils.searchItemsByName(this, name);
            if(null!=foundedItems){
                adapter.setItems(foundedItems);
//                increaseUserPoint(foundedItems);
            }
        }else{
            ArrayList<GroceryItem> allItems = Utils.getAllItems(this);
            if(null!= allItems){
                adapter.setItems(allItems);
            }
        }
    }


    private void initBottomNavBar() {
        bottomNavigationView.setSelectedItemId(R.id.ic_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_cart:
                        Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.ic_search:
                        break;
                    case R.id.ic_home:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.ic_settings:
                        Intent settingsIntent = new Intent(SearchActivity.this, SettingsActivity.class);
                        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(settingsIntent);
                        break;
                    case R.id.ic_map:
                        Intent mapIntent = new Intent(SearchActivity.this, MapActivity.class);
                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mapIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.edtSearchBox);
        btnSearch = findViewById(R.id.btnSearch);
        txtFirstCategory = findViewById(R.id.txtFirstCat);
        txtSecondCategory = findViewById(R.id.txtSecondCat);
        txtThirdCategory = findViewById(R.id.txtThirdCat);
        txtAllCategories = findViewById(R.id.txtAllCategories);
        recyclerView = findViewById(R.id.recView);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
    }

    @Override
    public void onGetCategoryResult(String category) {
        Log.d(TAG, "onGetCategoryResult: category opened: "+ category);
        ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
        if(null!=items){
            adapter.setItems(items);
        }
    }

    private void increaseUserPoint(ArrayList<GroceryItem> items){
        for(GroceryItem i:items){
            Utils.changeUserPoint(SearchActivity.this, i, 1);
        }
    }
}