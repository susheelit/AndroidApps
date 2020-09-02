package com.ceodelhi.filesystemapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.OfficerAdapter;
import com.ceodelhi.filesystemapp.model.ModelOfficer;
import com.ceodelhi.filesystemapp.utility.ShowDialog;

import java.util.ArrayList;

public class ActivityMarkOfficer extends AppCompatActivity implements OfficerAdapter.MyCallBack {

    private RecyclerView recyclerViewOfficers;
    private ModelOfficer modelOfficer;
    private OfficerAdapter officerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ModelOfficer> officers;
    private ImageView search;
    private TextView submitbtn;
    private ArrayList<ModelOfficer> selectedOfficers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_officer);
        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);
        submitbtn = findViewById(R.id.submitbtn);
        selectedOfficers = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewOfficers = findViewById(R.id.recyclerViewOfficers);
        officerAdapter = new OfficerAdapter(this, this);
        recyclerViewOfficers.setAdapter(officerAdapter);
        recyclerViewOfficers.setLayoutManager(mLayoutManager);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOfficers.size() == 0) {
                    ShowDialog.showAlertDialog(ActivityMarkOfficer.this, "Please mark atleast one officer!", false);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("officers", selectedOfficers);
                    setResult(RESULT_OK, intent);
                    finish();
                    }
            }
        });
    }

    @Override
    public void ListenerMethod(ModelOfficer officer) {
        if (officer.isSelected()) {
            selectedOfficers.add(officer);
        } else {
            selectedOfficers.remove(officer);
        }
    }
}