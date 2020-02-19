package com.aob.aobsalesman.activities.utility;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aob.aobsalesman.R;

public class MyDialogFragment extends DialogFragment {
    private ListView listView;
    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);


        return v;
    }
}
