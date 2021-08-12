package com.example.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mymodule.HelperDateUtility;
import com.example.retrofitapplication.Adapter.MyAdapter;
import com.example.retrofitapplication.Api.MyApi;
import com.example.retrofitapplication.Api.RetrofitClient;
import com.example.retrofitapplication.Model.GitResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    FloatingActionButton favoritefloat;

    private CachedItemList cachedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cachedItemList = CachedItemList.getInstance();

        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritefloat = findViewById(R.id.float_favoriteBtn);
        favoritefloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        callGitApi("kotlin");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate mydate = HelperDateUtility.getCurrentDate();
            Log.d("DATE MODULE: ", mydate.toString());
        } else {
            Date mydate = HelperDateUtility.getCurrentDateLegacy();
            Log.d("DATE MODULE: ", mydate.toString());
        }
    }

    public void callGitApi(String name) {
        MyApi api = RetrofitClient.getApi(RetrofitClient.URL);
        api.getContactResponseCall(name).enqueue(new Callback<GitResponse>() {
            @Override
            public void onResponse(Call<GitResponse> call, Response<GitResponse> response) {
                if (response.isSuccessful()) {
                    GitResponse gitResponse = response.body();

                    cachedItemList.setMainList(gitResponse.getItems());

                    myAdapter = new MyAdapter(cachedItemList.getMainList(), MainActivity.this);
                    recyclerView.setAdapter(myAdapter);
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GitResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //event fire
            @Override
            public boolean onQueryTextChange(String newText) {
                // if (newText.length()>=3)
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

   /* public void usingEditTextFilter(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //myAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>=3)
                    myAdapter.getFilter().filter(editable);
            }
        });
    }*/
}