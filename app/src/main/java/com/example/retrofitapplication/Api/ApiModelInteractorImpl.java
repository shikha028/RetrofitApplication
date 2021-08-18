package com.example.retrofitapplication.Api;

import com.example.mymodule.MVPContract.Contract;
import com.example.retrofitapplication.Model.GitResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiModelInteractorImpl implements Contract.ApiModelInteractor {
    //here making an api call to get data
    @Override
    public void getDataFromApi(NetworkListener listener) {

        MyApi api = RetrofitClient.getApi(RetrofitClient.URL);
        api.getContactResponseCall("kotlin").
                enqueue(new Callback<GitResponse>() {
                    @Override
                    public void onResponse(Call<GitResponse> call, Response<GitResponse> response) {
                        if (response.isSuccessful()) {
                            listener.onSuccess(response.body());
                        } else {
                            listener.onFailure(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<GitResponse> call, Throwable t) {
                        listener.onFailure(t.getMessage());
                    }
                });
    }
}
