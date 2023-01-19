package com.example.apicallingmyself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    tvResult = findViewById(R.id.tvResult);

    button = findViewById(R.id.button);


//1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//2
        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
//3
       Call<Post> call = jsonPlaceholderApi.getPost();
//4

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               tvResult.getText();
            call.clone().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
//this doesnt mean that our request was sucessful
                if (!response.isSuccessful()) {
                    tvResult.setText("Code" + response.code());//jo error a rha woh btaye
                    return;
                }

                Post post = response.body();

                String content = "";
                content += "fact: " + post.getFact() + "\n";
                content += "length: " + post.getLength() + "\n\n";

                tvResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                tvResult.setText(t.getMessage());
            }
        });
            }
        });
    }
}