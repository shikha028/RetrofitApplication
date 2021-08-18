package com.example.retrofitapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mymodule.MVPContract.Contract;
import com.example.retrofitapplication.Adapter.MyAdapter;
import com.example.retrofitapplication.Api.ApiModelInteractorImpl;
import com.example.retrofitapplication.Model.GitResponse;
import com.example.retrofitapplication.Model.Item;
import com.example.retrofitapplication.R;
import com.example.retrofitapplication.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class RepoActivity extends AppCompatActivity implements Contract.BaseView {

    RecyclerView repoRecyclerView;
    GitResponse gitResponse;
    List<Item> itemList;
    MyAdapter myAdapter;

    Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);


        Contract.ApiModelInteractor apiModelInteractor = new ApiModelInteractorImpl();
        presenter = new PresenterImpl(this, apiModelInteractor);
        presenter.requestData();

        initViews();
    }

    @Override
    public void initViews() {
        repoRecyclerView = findViewById(R.id.myRepoRecycler);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        myAdapter = new MyAdapter(itemList, this);
        repoRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDateToView(Object object) {
        gitResponse = (GitResponse) object;
        itemList = gitResponse.getItems();
        myAdapter.updateList(itemList);
    }

    @Override
    public void onResposnseFailure(Throwable t) {
        Toast.makeText(this, "Error: " + t, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}