package com.example.mall;

import static com.example.mall.SecondCartFragment.ORDER_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCardFragment extends Fragment {
    private static final String TAG = "ThirdCardFragment";
    private TextView txtItems, txtPhone, txtTotalPrice, txtEmail;
    private RadioGroup rgPayment;
    private Button btnBack, btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_cart_fragment, container, false);
        initViews(view);

        Bundle bundle = getArguments();
        if(null!= bundle){
            String jsonOrder = bundle.getString(ORDER_KEY);
            if(null!=jsonOrder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                final Order order = gson.fromJson(jsonOrder, type);
                if(null!=order){
                    String items = "";
                    for(GroceryItem i: order.getItems()){
                        items += "\n" + i.getName();
                    }
                    txtItems.setText(items);
                    txtPhone.setText(order.getPhoneNumber());
                    txtTotalPrice.setText(order.getTotalPrice()+" $");
                    txtEmail.setText(order.getEmail());

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY, jsonOrder);

                            SecondCartFragment secondCartFragment = new SecondCartFragment();
                            secondCartFragment.setArguments(backBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.cartContainer, secondCartFragment);
                            transaction.commit();
                        }
                    });
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (rgPayment.getCheckedRadioButtonId()){
                                case R.id.rbCreditCard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                case R.id.rbPayPal:
                                    order.setPaymentMethod("PayPal");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknown");
                                    break;

                            }
                            order.setSuccess(true);


                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint orderEndPoint = retrofit.create(OrderEndPoint.class);
                            Call<Order> call = orderEndPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Log.d(TAG, "onResponse: code: " + response.code());
                                    if(response.isSuccessful()) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString(ORDER_KEY, gson.toJson(response.body()));
                                        PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
                                        paymentResultFragment.setArguments(bundle);

                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.cartContainer, paymentResultFragment);
                                        transaction.commit();
                                    }else{
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.cartContainer, new PaymentResultFragment());
                                        transaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });

                }
            }
        }


        return view;
    }

    private void initViews(View view) {
        txtItems = view.findViewById(R.id.txtItems);
        txtPhone = view.findViewById(R.id.txtPhoneNumber);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        txtEmail = view.findViewById(R.id.txtEmail);
        rgPayment = view.findViewById(R.id.rgPayment);
        btnBack = view.findViewById(R.id.btnBackThird);
        btnNext = view.findViewById(R.id.btnNextThird);
    }
}
