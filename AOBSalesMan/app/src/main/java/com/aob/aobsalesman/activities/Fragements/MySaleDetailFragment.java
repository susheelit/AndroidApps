package com.aob.aobsalesman.activities.Fragements;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.model.LeadModal;
import com.aob.aobsalesman.activities.model.SalesModal;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySaleDetailFragment extends Fragment {
    private View view;
    SalesModal leadModal;

    LinearLayout gst_linear,pan_linear;

    public MySaleDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sale_detail, container, false);

        gst_linear = view.findViewById(R.id.gst_linear);
        pan_linear = view.findViewById(R.id.pan_linear);

        getObj();
        return view;
    }

    private void getObj() {


    Bundle bundle = getArguments();
    if (bundle != null) {
        leadModal = (SalesModal) bundle.getSerializable("MyClass");
        ((TextView) view.findViewById(R.id.email_id)).setText(leadModal.getEmail_id());
        //((TextView) view.findViewById(R.id.project)).setText(leadModal.getProject_id());
        ((TextView) view.findViewById(R.id.company_name)).setText(leadModal.getCompany_name());
        ((TextView)getActivity().findViewById(R.id.main_name)).setText(leadModal.getProject_id());
        ((TextView) view.findViewById(R.id.contact_person)).setText(leadModal.getContact_person());
        ((TextView) view.findViewById(R.id.designation)).setText(leadModal.getDesignation());
        ((TextView) view.findViewById(R.id.contact_no)).setText(leadModal.getContact_no());
        ((TextView) view.findViewById(R.id.address)).setText(leadModal.getAddress());
        ((TextView) view.findViewById(R.id.city)).setText(leadModal.getCity());
        ((TextView) view.findViewById(R.id.state)).setText(leadModal.getState());
        ((TextView) view.findViewById(R.id.pincode)).setText(leadModal.getPincode());
        ((TextView) view.findViewById(R.id.order_details)).setText(leadModal.getOrder_detail());
        ((TextView) view.findViewById(R.id.date)).setText(leadModal.getDate() + " & " + leadModal.getTime());
        ((TextView) view.findViewById(R.id.lead_status)).setText(leadModal.getLead_status());
        ((TextView) view.findViewById(R.id.payment_details)).setText(leadModal.getPayment_detail());

        if (!TextUtils.isEmpty(leadModal.getGst_no())
                && !leadModal.getGst_no().equalsIgnoreCase("null")
                && leadModal.getGst_no() != null
        ) {

            ((TextView) view.findViewById(R.id.gst_no)).setText(leadModal.getGst_no());
            gst_linear.setVisibility(View.VISIBLE);

        }else
        {
            gst_linear.setVisibility(View.GONE);

        }

        if (!TextUtils.isEmpty(leadModal.getPan_no())
                && !leadModal.getPan_no().equalsIgnoreCase("null")
                && leadModal.getPan_no() != null
        ) {

            ((TextView) view.findViewById(R.id.pan_no)).setText(leadModal.getPan_no());
            pan_linear.setVisibility(View.VISIBLE);

        }else
        {
            pan_linear.setVisibility(View.GONE);

        }



    } else {
        Log.e("aob", bundle.toString());
    }



/*}catch (Exception e){
    Log.e("AOBSalesman",e.toString());
}*/


    }

}