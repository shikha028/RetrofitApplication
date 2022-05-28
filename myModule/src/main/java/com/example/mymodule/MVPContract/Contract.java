package com.example.mymodule.MVPContract;

public interface Contract {

    // View
    interface BaseView {

        void initViews();

        void showProgress();

        void hideProgress();

        void setDateToView(Object object);

        void onResposnseFailure(Throwable t);

    }

    //Presenter
    interface Presenter {
        void onDestroy();

        void onRefresh();

        void requestData();
    }

    //Model
    interface ApiModelInteractor {
        interface NetworkListener {
            void onSuccess(Object object);

            void onFailure(Object object);
        }

        void getDataFromApi(NetworkListener listener);
    }
}
