package com.example.retrofitapplication.presenter;

import com.example.mymodule.MVPContract.Contract;

public class PresenterImpl implements Contract.Presenter, Contract.ApiModelInteractor.NetworkListener {

    private Contract.BaseView baseView;
    private Contract.ApiModelInteractor apiModelInteractor;

    public PresenterImpl(Contract.BaseView baseView, Contract.ApiModelInteractor apiModelInteractor) {
        this.baseView = baseView;
        this.apiModelInteractor = apiModelInteractor;
    }

    @Override
    public void onDestroy() {
        //will relaese view memory when no longer in use
        baseView = null;
    }

    @Override
    public void onRefresh() {
        // will fetch fresh/updated data from server by calling api.
        apiModelInteractor.getDataFromApi(this);
    }

    @Override
    public void requestData() {
        // will fetch new data from server by calling api.
        apiModelInteractor.getDataFromApi(this);
    }

    @Override
    public void onSuccess(Object object) {
        // will set data to the view after successful api call
        if (baseView != null) {
            baseView.setDateToView(object);
        }
    }

    @Override
    public void onFailure(Object object) {
        // will throw an error message if data fetching is failed
        if (baseView != null) {
            baseView.onResposnseFailure((Throwable) object);
        }
    }
}
