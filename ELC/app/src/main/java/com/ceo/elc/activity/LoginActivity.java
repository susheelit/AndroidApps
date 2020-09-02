package com.ceo.elc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ceo.elc.R;
import com.ceo.elc.common.BaseActivity;
import com.ceo.elc.common.Konstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends BaseActivity {

    Context mContext;
    private EditText etInstId;
    private Button btnSubmit;
    private TextView tvno_id;
    private String webResponse,clubID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mContext = LoginActivity.this;
        etInstId = findViewById(R.id.etInstId);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvno_id = findViewById(R.id.tvno_id);

        btnSubmit.setOnClickListener(onSubmitClick);
        tvno_id.setOnClickListener(onNoIdClick);
        setClubID();
    }

    private void setClubID() {
        if(getIntent().getExtras() !=null) {
            String clubID = getIntent().getExtras().getString("clubID");
            etInstId.setText(""+clubID);
        }
    }

    protected View.OnClickListener onSubmitClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkNetwork()) {
                clubID = etInstId.getText().toString().trim();
                if (!TextUtils.isEmpty(clubID)) {
                    new loginUser().execute();
                } else {
                    Konstant.altDialog(mContext, "Please enter Club ID");
                }
            } else {
                Konstant.toastShow(mContext, getResources().getString(R.string.no_internet));
            }
        }
    };

    protected View.OnClickListener onNoIdClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    };

    public class loginUser extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            webResponse = "";
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Getting details...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_LOGIN);

                // to start service
                request.addProperty("ClubID", "" + clubID);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_LOGIN, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("result", "" + result);

            if (!result.equalsIgnoreCase("No Record found") &&
                    !result.equalsIgnoreCase("99")) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (mContext != null/* && !isFinishing()*/) {
                            progressDialog.dismiss();
                            saveData(result);
                        }
                    }
                }, 1000);
            } else {
                Konstant.toastShow(mContext, "Something went wrong!!");
                progressDialog.dismiss();
            }
        }
    }

    public void saveData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            setSetting("ClubID", jsonObject.getString("ClubID"));
            setSetting("DISTRICT_NAME",  jsonObject.getString("DISTRICT_NAME"));
            setSetting("DIST_NO",  jsonObject.getString("DIST_NO"));
            setSetting("AC_NAME",  jsonObject.getString("AC_NAME"));
            setSetting("AC_NO",  jsonObject.getString("AC_NO"));
            setSetting("PS_NO",  jsonObject.getString("PS_NO"));
            setSetting("ELCType",  jsonObject.getString("ELCType"));
            setSetting("NameOfInstitute",  jsonObject.getString("NameOfInstitute"));
            setSetting("NameOfNodalPerson",  jsonObject.getString("NameOfNodalPerson"));
            setSetting("AddressOfInstitute",  jsonObject.getString("AddressOfInstitute"));
            setSetting("Pincode",  jsonObject.getString("Pincode"));
            setSetting("ClubNo",  jsonObject.getString("ClubNo"));
            setSetting("EntryDate",  jsonObject.getString("EntryDate"));

            Intent intent = new Intent(mContext, CreateEventActivity.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
