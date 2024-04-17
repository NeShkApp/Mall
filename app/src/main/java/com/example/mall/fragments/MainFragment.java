package com.example.mall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mall.R;
import com.example.mall.activities.MapActivity;
import com.example.mall.activities.SearchActivity;
import com.example.mall.Utils;
import com.example.mall.activities.CartActivity;
import com.example.mall.activities.SettingsActivity;
//import com.example.mall.activities.StoresMapActivity;
import com.example.mall.adapters.BannerAdapter;
import com.example.mall.adapters.GroceryItemAdapter;
import com.example.mall.databasefiles.Banner;
import com.example.mall.databasefiles.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainFragment extends Fragment {
    //new
    private ViewPager2 viewPager;
    private BannerAdapter bannerAdapter;
    private List<Banner> bannerList;
    private ProgressBar progressBar;
    //new
    private BottomNavigationView bottomNavigationView;
    private RecyclerView allItemsRecView, popularItemsRecView, suggestedItemsRecView;
    private GroceryItemAdapter allItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavBar();

        return view;
    }

    private void initRecViews() {
        //new
        bannerList = new ArrayList<>();
        bannerAdapter = new BannerAdapter(getActivity(), bannerList);
        viewPager.setAdapter(bannerAdapter);


        progressBar.setVisibility(View.VISIBLE);
        Query query = FirebaseDatabase.getInstance().getReference().child("Banner");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Banner banner = snapshot.getValue(Banner.class);
                    bannerList.add(banner);
                }
                bannerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
        //new

        allItemsAdapter = new GroceryItemAdapter(getActivity());
        allItemsRecView.setAdapter(allItemsAdapter);
        allItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemsAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<GroceryItem> allItems = Utils.getAllItems(getActivity());
        if (null != allItems) {
            Comparator<GroceryItem> allItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() - o2.getId();
                }
            };
            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(allItemsComparator);
            Collections.sort(allItems, reverseComparator);
            allItemsAdapter.setItems(allItems);
        }

        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (null != popularItems) {
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularItemsAdapter.setItems(popularItems);
        }

        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        if (null != suggestedItems) {
            Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsComparator));
            suggestedItemsAdapter.setItems(suggestedItems);
        }
    }


    private void initBottomNavBar() {
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_cart:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.ic_search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.ic_home:
                        break;
                    case R.id.ic_settings:
                        Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(settingsIntent);
                        break;
                    case R.id.ic_map:
                        Intent mapIntent = new Intent(getActivity(), MapActivity.class);
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

    private void initViews(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomNavBar);
        allItemsRecView = view.findViewById(R.id.allItemsRecyclerView);
        popularItemsRecView = view.findViewById(R.id.popularItemsRecyclerView);
        suggestedItemsRecView = view.findViewById(R.id.suggestedItemsRecyclerView);

        //new
        viewPager = view.findViewById(R.id.viewPager);
        progressBar= view.findViewById(R.id.progressBar);

    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
    }

}
