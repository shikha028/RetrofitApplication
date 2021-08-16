package com.example.retrofitapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.retrofitapplication.Model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    FloatingActionButton favoritefloat;

    private CachedItemList cachedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cachedItemList = CachedItemList.getInstance();

        //making an api call only after saving in db
        if(!readPref()) {
            callGitApi("kotlin");
        }

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
                    populateDBWithListFromAPI(gitResponse.getItems());
                    savePref();
                    /*myAdapter = new MyAdapter(cachedItemList.getMainList(), MainActivity.this);
                    recyclerView.setAdapter(myAdapter);*/
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
    protected void onStart() {
        super.onStart();
        //loading the fragment after making api call in oncreate
        useFragment(new RepoListFragment(MainActivity.this));
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
    public void useFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_repoListContainer, fragment);
        fragmentTransaction.addToBackStack("Frag");
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentLoad() {
        useFragment(new FavoriteFragment(MainActivity.this));
    }

    @Override
    public void getAdapterFromFragment(RecyclerView.Adapter adapter) {
        //adapter.notifyDataSetChanged();
        myAdapter = (MyAdapter) adapter;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }

    public void populateDBWithListFromAPI(List<Item> myList) {
        FavDataBase favDataBase = new FavDataBase(this);
        for (int i = 0; i < myList.size(); i++) {
            favDataBase.saveItem(myList.get(i));
        }
    }

    private void savePref() {
        SharedPreferences prefs = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isApiCalled", true);
        editor.commit();
    }

    private boolean readPref() {
        SharedPreferences prefs = getSharedPreferences("app_pref", MODE_PRIVATE);
        return prefs.getBoolean("isApiCalled", false);
    }
}