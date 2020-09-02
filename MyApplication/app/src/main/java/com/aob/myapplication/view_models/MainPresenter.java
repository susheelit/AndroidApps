package com.aob.myapplication.view_models;

import androidx.databinding.ObservableField;

import com.aob.myapplication.interfaces.MainView;

public class MainPresenter {
    private static final int BACK_CLICK = 0;
    private static final int CLOSE_CLICK = 1;
    private static final int LOGIN_CLICK = 2;
    private static final int REFRESH_CLICK = 3;
    private MainView mViewModel;
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mSubTitle = new ObservableField<>();
    public ObservableField<String> mRighTitle = new ObservableField<>();

    public MainPresenter(MainView viewModel) {
        mViewModel = viewModel;
        initFields();
    }

    private void initFields() {
        mTitle.set("Welcome");
        mSubTitle.set("Sub");
        mRighTitle.set("Login");
    }

    public void onClick(int type) {
        switch (type) {
            case BACK_CLICK:
                mViewModel.showToast("Clicked Back Icon");
                break;
            case CLOSE_CLICK:
                mViewModel.showToast("Clicked Close Icon");
                break;
            case LOGIN_CLICK:
                mViewModel.showToast("Clicked Login Icon");
                break;
            case REFRESH_CLICK:
                mViewModel.showToast("Clicked Refresh Icon");
                break;
            default:
                break;
        }
    }
}
