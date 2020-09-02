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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ceo.elc.R;
import com.ceo.elc.adapter.PSAdapter;
import com.ceo.elc.common.BaseActivity;
import com.ceo.elc.common.Konstant;
import com.ceo.elc.model.ModelPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends BaseActivity {

    private Context mContext;
    private Spinner spDist, spAC, spPS, spType;
    private EditText etMob, etOTP, etInstNam, etNodalPerson, etDesignation, etAddress, etPincode;
    private Button btnGetOtp, btnSubmit;
    private String mobileNo, distID, acID, psID, distName, acName, psName, typeName,
            instName, nodalPersonName, designation, address, pincode, webResponse;
    private LinearLayout llOtp, llRegform;
    ArrayAdapter<String> dataAdapter;
    PSAdapter psAdapter;
    private List<ModelPS> psList;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    private void initView() {
        mContext = RegistrationActivity.this;
        etMob = findViewById(R.id.etMob);
        etOTP = findViewById(R.id.etOTP);
        spDist = findViewById(R.id.spDist);
        spAC = findViewById(R.id.spAC);
        spPS = findViewById(R.id.spPS);
        spType = findViewById(R.id.spType);
        etInstNam = findViewById(R.id.etInstNam);
        etNodalPerson = findViewById(R.id.etNodalPerson);
        etDesignation = findViewById(R.id.etDesignation);
        etAddress = findViewById(R.id.etAddress);
        etPincode = findViewById(R.id.etPincode);
        llOtp = findViewById(R.id.llOtp);
        llRegform = findViewById(R.id.llRegform);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetOtp = findViewById(R.id.btnGetOtp);
        btnGetOtp.setOnClickListener(onValidteOTP);
        btnSubmit.setOnClickListener(onSubmit);
        bindDistSpinner();
        getACName();
        bindTypeSp();
        getPSID();
    }

    private void getPSID() {
        spPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                psID = "";
                psName = "";
                if (psAdapter != null) {
                    ModelPS mModelPS = (ModelPS) parent.getItemAtPosition(position);
                    psID = mModelPS.getPSNO();
                    psName = mModelPS.getPSNAME();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void bindTypeSp() {
        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.reg_type));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAC.setAdapter(dataAdapter);
    }

    private void bindDistSpinner() {
        spDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selItem = spDist.getSelectedItem().toString();
                if (!selItem.equalsIgnoreCase("---SELECT---")) {
                    // getACName_ID(selItem);
                    if (selItem.equalsIgnoreCase("1-North-West")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.northwest));
                    } else if (selItem.equalsIgnoreCase("2-North-East")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.northeast));
                    } else if (selItem.equalsIgnoreCase("3-South")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.south));
                    } else if (selItem.equalsIgnoreCase("4-Central")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.central));
                    } else if (selItem.equalsIgnoreCase("5-South-West")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.southwest));
                    } else if (selItem.equalsIgnoreCase("6-East")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.east));
                    } else if (selItem.equalsIgnoreCase("7-West")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.west));
                    } else if (selItem.equalsIgnoreCase("8-North")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.north));
                    } else if (selItem.equalsIgnoreCase("9-New Delhi")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.newdelhi));
                    } else if (selItem.equalsIgnoreCase("10-Shahdara")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.shahdara));
                    } else if (selItem.equalsIgnoreCase("11-South-East")) {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.southeast));
                    }
                } else {
                    dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.select));
                }

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAC.setAdapter(dataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getACName() {
        if (checkNetwork()) {
            spAC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selItem = spAC.getSelectedItem().toString();
                    if (!selItem.equalsIgnoreCase("---SELECT---")) {
                        //get PS List
                        acID = getID(selItem);
                        if (!acID.isEmpty()) {
                            new getPSList().execute();
                        }

                    } else {
                        dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.select));
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spPS.setAdapter(dataAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            spAC.setSelection(0);//reset spAC
            Konstant.toastShow(mContext, getResources().getString(R.string.no_internet));
        }

    }

    /*GET PS LIST*/
    private class getPSList extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Getting details...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_PSLIST);
                // to start service
                System.out.println("ac no : " + acID);
                request.addProperty("AC_NO", acID);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);
                androidHttpTransport.call(Konstant.SOAP_ACTION_PSLIST, envelope);
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
            if (result != null) {
                if (!result.equalsIgnoreCase("No Record found") &&
                        !result.equalsIgnoreCase("99")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (this != null && !isFinishing()) {
                                bindPSSpinner(result);
                                progressDialog.dismiss();
                            }
                        }
                    }, 500);
                } else {
                    spAC.setSelection(0);//reset spAC
                    Konstant.toastShow(mContext, "Something went wrong!!");
                    progressDialog.dismiss();
                }
            } else {
                spAC.setSelection(0);//reset spAC
                Konstant.toastShow(mContext, "Something went wrong!!");
                progressDialog.dismiss();
            }
        }
    }

    private void bindPSSpinner(String result) {

        if (!result.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    //  String[] psList = new String[jsonArray.length()];
                    psList = new ArrayList<ModelPS>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        // psList[i] = jsonObject.getString("PSNAME");
                        ModelPS modelPS = new ModelPS(jsonObject.getString("PSNO"), jsonObject.getString("PSNAME"));
                        psList.add(modelPS);
                    }

                    psAdapter = new PSAdapter(psList, mContext);
                    spPS.setAdapter(psAdapter);
                    //getPSID();
                } else {
                    dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.select));
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPS.setAdapter(dataAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected View.OnClickListener onValidteOTP = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Konstant.hideKeyboard(mContext);
            if (flag == 0) {
                // get OTP
                mobileNo = etMob.getText().toString().trim();
                if (!TextUtils.isEmpty(mobileNo) && mobileNo.length() == 10) {
                    new verifyMobile().execute();
                   /* flag = 1;
                    etOTP.setVisibility(View.VISIBLE);
                    etMob.setEnabled(false);*/
                } else {
                    Konstant.altDialog(mContext, "Please enter valid mobile no.");
                }

            } else {
                // validate OTP
                String otp = etOTP.getText().toString().trim();
                if (!TextUtils.isEmpty(otp)) {
                    int sendOtp = Konstant.otpMessage;
                    Log.e("sendOtp: ", "" + sendOtp);
                    if (sendOtp == Integer.parseInt(otp)) {
                        // success
                        llOtp.setVisibility(View.GONE);
                        llRegform.setVisibility(View.VISIBLE);
                        // Konstant.altDialog(mContext, "Success");

                    } else {
                        Konstant.altDialog(mContext, "Please enter valid OTP");
                    }
                } else {
                    Konstant.altDialog(mContext, "Please enter received OTP");
                }


            }
        }
    };

    protected View.OnClickListener onSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Konstant.hideKeyboard(mContext);
            if (checkNetwork()) {
                distID = getID(spDist.getSelectedItem().toString());
                acID = getID(spAC.getSelectedItem().toString());
                // psID = getID(spPS.getSelectedItem().toString());
                //typeID = getID(spType.getSelectedItem().toString());
                distName = spDist.getSelectedItem().toString();
                acName = spAC.getSelectedItem().toString();
                //  psName = spPS.getSelectedItem().toString();
                typeName = spType.getSelectedItem().toString();
                instName = etInstNam.getText().toString().trim();
                nodalPersonName = etNodalPerson.getText().toString().trim();
                designation = etDesignation.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                pincode = etPincode.getText().toString().trim();
                validate();
            } else {
                Konstant.toastShow(mContext, getResources().getString(R.string.no_internet));
            }


        }
    };

    private void validate() {

        if (!distID.isEmpty() && !distName.isEmpty()) {
            if (!acID.isEmpty() && !acName.isEmpty()) {
                if (!psID.isEmpty() && !psName.isEmpty()) {
                    //if (!typeID.isEmpty() && !typeName.isEmpty()) {
                    if (!typeName.isEmpty() && !typeName.equalsIgnoreCase("---SELECT---")) {
                        if (!instName.isEmpty()) {
                            if (!nodalPersonName.isEmpty()) {
                                if (!designation.isEmpty()) {
                                    if (!address.isEmpty()) {
                                        if (!pincode.isEmpty()) {
                                            if (pincode.length() == 6) {
                                                // call method here
                                                new InsertData().execute();
                                            } else {
                                                Konstant.altDialog(mContext, "Please enter 6 digit pincode.");
                                            }
                                        } else {
                                            Konstant.altDialog(mContext, "Please enter pincode.");
                                        }
                                    } else {
                                        Konstant.altDialog(mContext, "Please enter institute address.");
                                    }
                                } else {
                                    Konstant.altDialog(mContext, "Please enter your designation.");
                                }
                            } else {
                                Konstant.altDialog(mContext, "Please enter nodal person name.");
                            }
                        } else {
                            Konstant.altDialog(mContext, "Please enter institute name.");
                        }
                    } else {
                        Konstant.altDialog(mContext, "Please select ELC type.");
                    }
                } else {
                    Konstant.altDialog(mContext, "Please select your polling station.");
                }
            } else {
                Konstant.altDialog(mContext, "Please select your assembly constituency.");
            }
        } else {
            Konstant.altDialog(mContext, "Please select your disitrict.");
        }

    }

    private String getID(String selItem) {

        String[] separated = selItem.split("-");
        return separated[0];
        // new getPSList().execute();
        // String name = separated[1];
        //  Log.e("getACName ID ", acID);
        // Log.e("getACName NAME ", name);
    }

    private class InsertData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            btnSubmit.setClickable(false);
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
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_REGISTRATION);

                // to start service
                request.addProperty("MobileNo", "" + mobileNo);
                request.addProperty("DISTRICT_NAME", "" + distName);
                request.addProperty("DIST_NO", Integer.parseInt(distID));
                request.addProperty("AC_NAME", "" + acName);
                request.addProperty("AC_NO", Integer.parseInt(acID));
                request.addProperty("PS_NO", Integer.parseInt(psID));
                request.addProperty("ELCType", "" + typeName);//typeID
                request.addProperty("NameOfInstitute", "" + instName);
                request.addProperty("NameOfNodalPerson", "" + nodalPersonName);
                request.addProperty("DesignationOfNodal", "" + designation);
                request.addProperty("AddressOfInstitute", "" + address);
                request.addProperty("Pincode", "" + pincode);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_REGISTRATION, envelope);
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
                    !result.contains("99")) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (this != null && !isFinishing()) {
                            progressDialog.dismiss();
                            saveData(result);
                        }
                    }
                }, 1000);
            } else {
                btnSubmit.setClickable(true);
                Konstant.toastShow(mContext, "Something went wrong, try again");
                progressDialog.dismiss();
            }
        }
    }

    private void saveData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            btnSubmit.setClickable(true);
            Konstant.altRegDialog(mContext, jsonObject.getString("ClubID"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class verifyMobile extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            webResponse = "";
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Verifying...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_GETMOBILECHECK);

                // to start service
                request.addProperty("MobileNo", "" + mobileNo);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_GETMOBILECHECK, envelope);
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
                    !result.contains("99")) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (this != null && !isFinishing()) {
                            progressDialog.dismiss();

                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String flag = jsonObject.getString("ReturnValue");
                                if (flag.equalsIgnoreCase("0")) {
                                    new setOTP().execute();
                                } else {
                                    Konstant.altDialog(mContext, "Mobile no is already exists.");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // saveData(result);
                        }
                    }
                }, 1000);
            } else {
                Konstant.toastShow(mContext, "Something went wrong!!");
                progressDialog.dismiss();
            }
        }
    }

    private class setOTP extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            webResponse = "";
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Sending OTP...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_FNCSMS_MULTIPLE);

                // to start service
                request.addProperty("strMobileNos", "" + mobileNo);
                request.addProperty("strMessage", "" + Konstant.messageBody);
                request.addProperty("strSenderID", "" + Konstant.OTPSENDER);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_FNCSMS_MULTIPLE, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("result otp", "" + result);

            if (!result.equalsIgnoreCase("No Record found") &&
                    !result.contains("99")) {

                if (result.equalsIgnoreCase("OK")) {
                    flag = 1;
                    etOTP.setVisibility(View.VISIBLE);
                    etMob.setEnabled(false);
                    Konstant.toastShow(mContext, "OTP sent successfully.");
                }
                progressDialog.dismiss();
            } else {
                Konstant.toastShow(mContext, "Something went wrong!!");
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}


