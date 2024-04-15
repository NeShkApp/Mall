package com.example.mall.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.databasefiles.ShopModel;
import com.example.mall.dialogues.ShopInfoDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private GoogleMap myMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderCLient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private int numOfStores = 20;
    private HashMap<Marker, Integer> markerShopIdMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationProviderCLient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderCLient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);

        LatLng myHnidava = new LatLng(50.726725, 25.302582);
//        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(myHnidava)
                .title("My location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        myMap.addMarker(markerOptions);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myHnidava, 16.5f));

//        markShopsOnTheMap();
        markShopsFromDb();

        //get the street name
        try {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            List<Address> myAddress = geocoder.getFromLocation(myHnidava.latitude, myHnidava.longitude, 1);
//            String address = myAddress.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
//            public boolean onMarkerClick(Marker marker) {
//                ShopInfoDialog dialog = new ShopInfoDialog();
//                Bundle bundle = new Bundle();
//                bundle.putString("street_name", marker.getTitle());
//                bundle.putString("mon_fri_time", shop.getMonFriTime());
//
//                dialog.setArguments(bundle);
//                dialog.show(getSupportFragmentManager(), "shop dialog");
//
//                LatLng markerPosition = marker.getPosition();
//                Log.d(TAG, "Marker position: " + markerPosition.latitude + ", " + markerPosition.longitude);
//                return false;
//            }

            public boolean onMarkerClick(Marker marker) {
                Integer shopId = markerShopIdMap.get(marker);
                if (shopId != null) {
                    ShopModel shop = Utils.getShopById(MapActivity.this, shopId);

                    // Підготувати бандл для передачі даних у діалогове вікно
                    Bundle bundle = new Bundle();
                    bundle.putString("street_name", marker.getTitle());
                    bundle.putString("mon_fri_time", shop.getWorkingHoursFromMonToFri());
                    bundle.putString("saturday_time", shop.getWorkingHoursSat());
                    bundle.putString("sunday_time", shop.getWorkingHoursSun());

                    // Створити діалогове вікно та передати бандл інформації
                    ShopInfoDialog dialog = new ShopInfoDialog();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "shop dialog");

                    LatLng markerPosition = marker.getPosition();
                    Log.d(TAG, "Marker position: " + markerPosition.latitude + ", " + markerPosition.longitude);
                }
                return false;
            }
        });

    }

    private void markShopsFromDb() {
        ArrayList<ShopModel> stores = Utils.getAllShopItems(this);
        for (ShopModel s : stores) {
            LatLng latlng = new LatLng(s.getLatitude(), s.getLongitude());
            String address = getAddressFromCoordinates(latlng);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latlng)
                    .title(address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            Marker marker = myMap.addMarker(markerOptions);
            markerShopIdMap.put(marker, s.getId());
        }
    }

//    private void markShopsOnTheMap() {
//        LatLng[] storeLocations = new LatLng[numOfStores];
//
//        // Генеруємо випадкові координати для магазинів у межах міста Луцька
//        for (int i = 0; i < numOfStores; i++) {
//            double lat = 50.718 + (Math.random() * 0.015); // Випадкова широта в межах 50.72 і 50.73
//            double lng = 25.29 + (Math.random() * 0.03); // Випадкова довгота в межах 25.29 і 25.32
//            storeLocations[i] = new LatLng(lat, lng);
//        }
//
//
//        // Створюємо маркери для магазинів та додаємо їх на карту
//        for (int i = 0; i < numOfStores; i++) {
//            String address = getAddressFromCoordinates(storeLocations[i]);
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .position(storeLocations[i])
//                    .title(address)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//            myMap.addMarker(markerOptions);
//        }
//    }

    private String getAddressFromCoordinates(LatLng latLng) {
        String address = "";
        try {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address firstAddress = addresses.get(0);
                String street = firstAddress.getThoroughfare();
                String houseNumber = firstAddress.getSubThoroughfare();
                address = street;
                if (houseNumber != null && !houseNumber.isEmpty()) {
                    address += ", " + houseNumber;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }else{
                Toast.makeText(this, "You have no permissions for that", Toast.LENGTH_LONG).show();
            }
        }
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}