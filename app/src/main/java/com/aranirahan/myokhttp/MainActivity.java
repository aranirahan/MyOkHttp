package com.aranirahan.myokhttp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://www.gookkis.com/hello.html";

    private TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btCall = findViewById(R.id.bt_call);
        tvView = findViewById(R.id.tv_view);

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat instance baru
                OkHttpClient client = new OkHttpClient();

                //membuat cache agar hemat bandwith
                client.cache();

                //membuat request
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                //membuat async untuk membuat response
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(getApplicationContext(), "HTTP Request Failure", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        //jika Http response kode bukan 200
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        //merubah response body menjadi string
                        final String responseData = Objects.requireNonNull(response.body()).string();

                        //menampilkan string dalam textview
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvView.setText(responseData);
                            }
                        });
                    }
                });

            }
        });

    }

}
