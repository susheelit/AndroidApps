package com.aob.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.aob.myapplication.R;
import com.aob.myapplication.databinding.ActivityMvvmUniversalToolbarBinding;
import com.aob.myapplication.interfaces.MainView;
import com.aob.myapplication.view_models.MainPresenter;

public class MvvmUniversalToolbarActivity extends AppCompatActivity implements MainView {
    private MainPresenter mPresenter;
    private ActivityMvvmUniversalToolbarBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_universal_toolbar);
        mPresenter = new MainPresenter(this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
