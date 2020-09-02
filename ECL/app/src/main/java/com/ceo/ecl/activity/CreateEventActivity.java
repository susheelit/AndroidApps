package com.ceo.ecl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import com.ceo.ecl.R;
import com.ceo.ecl.databinding.ActivityCreateEventBinding;
import com.ceo.ecl.viewModel.CreatEventViewModel;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateEventBinding activityCreateEvent = DataBindingUtil.setContentView(this, R.layout.activity_create_event);
        activityCreateEvent.setViewModel(new CreatEventViewModel(CreateEventActivity.this));
        activityCreateEvent.executePendingBindings();
    }

    @BindingAdapter({"alertMessage"})
    public static void runMe(View view, String message) {
        if (message != null){
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}