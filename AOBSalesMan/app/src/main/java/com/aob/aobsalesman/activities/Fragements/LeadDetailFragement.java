package com.aob.aobsalesman.activities.Fragements;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.model.LeadModal;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeadDetailFragement extends Fragment {
    private View view;
    LeadModal leadModal;
    public LeadDetailFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_lead_detail_fragement, container, false);
        getObj();
        return view;
    }

    private void getObj() {

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                leadModal = (LeadModal) bundle.getSerializable("MyClass");
                ((TextView) view.findViewById(R.id.email_id)).setText(leadModal.getEmail_id());
                //((TextView) view.findViewById(R.id.project)).setText(leadModal.getProject_id());
                ((TextView)getActivity().findViewById(R.id.main_name)).setText(leadModal.getProject_id());
                ((TextView) view.findViewById(R.id.company_name)).setText(leadModal.getCompany_name());
                ((TextView) view.findViewById(R.id.contact_person)).setText(leadModal.getContact_person());
                ((TextView) view.findViewById(R.id.designation)).setText(leadModal.getDesignation());
                ((TextView) view.findViewById(R.id.contact_no)).setText(leadModal.getContact_no());
                ((TextView) view.findViewById(R.id.address)).setText(leadModal.getAddress());
                ((TextView) view.findViewById(R.id.city)).setText(leadModal.getCity());
                ((TextView) view.findViewById(R.id.state)).setText(leadModal.getState());
                ((TextView) view.findViewById(R.id.pincode)).setText(leadModal.getPincode());
                ((TextView) view.findViewById(R.id.instruction)).setText(leadModal.getInstruction());


                ((TextView) view.findViewById(R.id.date)).setText(leadModal.getDate() + " & " + leadModal.getTime());
                ((TextView) view.findViewById(R.id.lead_status)).setText(leadModal.getLead_status());
            } else {
                Log.e("aob", bundle.toString());
            }


        }catch (Exception e){}

    }

}