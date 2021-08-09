package com.example.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mymodule.HelperDateUtility;
import com.example.retrofitapplication.Adapter.MyAdapter;
import com.example.retrofitapplication.Api.MyApi;
import com.example.retrofitapplication.Api.RetrofitClient;
import com.example.retrofitapplication.Model.GitResponse;

import java.time.LocalDate;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        callGitApi("kotlin");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate mydate = HelperDateUtility.getCurrentDate();
            Log.d("DATE MODULE: ",mydate.toString());
        }else {
            Date mydate = HelperDateUtility.getCurrentDateLegacy();
            Log.d("DATE MODULE: ",mydate.toString());
        }
    }

    public void callGitApi(String name){
        MyApi api=RetrofitClient.getApi(RetrofitClient.URL);
        api.getContactResponseCall(name).enqueue(new Callback<GitResponse>() {
            @Override
            public void onResponse(Call<GitResponse> call, Response<GitResponse> response) {
                if (response.isSuccessful()){
                    GitResponse gitResponse =response.body();
                    MyAdapter myAdapter=new MyAdapter(gitResponse.getItems(), MainActivity.this);
                    recyclerView.setAdapter(myAdapter);
                }else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GitResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}