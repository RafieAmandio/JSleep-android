package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Account;
import com.rafieAmandioJSleepJS.jsleep_android.model.Payment;
import com.rafieAmandioJSleepJS.jsleep_android.request.BaseApiService;
import com.rafieAmandioJSleepJS.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class CreatePaymentActivity extends AppCompatActivity{
    BaseApiService mApiService;
    Payment payment;
    Context mContext;
    Button createbutton;
    TextView createpayment_from,createpayment_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);
        createbutton = findViewById(R.id.createpayment_button);
        createpayment_from = findViewById(R.id.createpayment_from);
        createpayment_to = findViewById(R.id.createpayment_to);

        createpayment_from.setText(PaymentDetailActivity.startdate);
        createpayment_to.setText(PaymentDetailActivity.enddate);

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked");
                createPayment(LoginActivity.loggedAcc.id,
                        DetailRoomActivity.clickedRoom.accountId,
                        DetailRoomActivity.clickedRoom.id,
                        PaymentDetailActivity.startdate,
                        PaymentDetailActivity.enddate);
            }
        });



    }


    protected Payment createPayment(int buyerId,
                                    int renterId,
                                    int roomId,
                                    String from,
                                    String to){
        System.out.println("Callback");
        //print all parameter
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(from);
        System.out.println(to);
        mApiService.createPayment(buyerId, renterId, roomId, from, to).enqueue(new Callback<Payment>() {

            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    payment = response.body();
                    System.out.println(payment);
                    Intent move = new Intent(CreatePaymentActivity.this,MainActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Payment created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Create Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}