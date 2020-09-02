package com.ceodelhi.filesystemapp.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.LetterAdapter;
import com.ceodelhi.filesystemapp.model.ModelLetters;
import com.ceodelhi.filesystemapp.model.ModelOfficer;
import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.utility.ShowDialog;
import com.ceodelhi.filesystemapp.utility.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static com.ceodelhi.filesystemapp.utility.MyConstants.LETTER_DISPOSE;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;

//https://www.youtube.com/watch?v=2-vZ1g_G1Zo
public class LetterDisposeActivity extends AppCompatActivity {


    Context mContext;
    private RecyclerView recyclerView;
    private LetterAdapter letterAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String webResponse;
    private ArrayList<ModelLetters> letters;
    private ArrayList<ModelOfficer> officers;
    private TextView totalFilesTv, todayFilesTv, totalDisposedFilesTv,
            todayDisposedFilesTv, totalPendingFilesTv, todayPendingFilesTv;
    private String totalFiles, todayFiles, totalDisposed, todayDisposed, totalPending, todayPending;
    private ImageView search;
    private LinearLayout counterLayout;
    private UserPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_dispose);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mContext = LetterDisposeActivity.this;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        counterLayout = findViewById(R.id.counterLayout);
        totalFilesTv = findViewById(R.id.totalFilesTv);
        todayFilesTv = findViewById(R.id.todayFilesTv);
        totalDisposedFilesTv = findViewById(R.id.totalDisposedFilesTv);
        todayDisposedFilesTv = findViewById(R.id.todayDisposedFilesTv);
        totalPendingFilesTv = findViewById(R.id.totalPendingFilesTv);
        todayPendingFilesTv = findViewById(R.id.todayPendingFilesTv);
        counterLayout.setVisibility(View.GONE);
        userPreferences = new UserPreferences(LetterDisposeActivity.this);
        search = findViewById(R.id.search);
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LetterDisposeActivity.this, SearchLetterActivity.class));
            }
        });
        getLetterList();
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getLetterList();
//            }
//        });
    }


    private void getLetterList() {
        if (ConnectionDetector.isConnectedToInernet(mContext)) {
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else {
            showAlertDialog(LetterDisposeActivity.this, "Please check internet service", false);
        }
    }

    private void getOfficerList() {
        if (ConnectionDetector.isConnectedToInernet(mContext)) {
            AsyncCallOfficers task = new AsyncCallOfficers();
            task.execute();
        } else {
            showAlertDialog(LetterDisposeActivity.this, "Please check internet service", false);
        }
    }


    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LetterDisposeActivity.this);
            dialog.setMessage("Loading Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
            letters = new ArrayList<>();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_GetLetterDisposal);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL, 10000);
                System.out.println("Call Time ");
                androidHttpTransport.call(MyConstants.SOAP_ACTION_GetLetterDisposal, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
                System.out.println("response time" + webResponse);

            } catch (Exception e) {
                webResponse = e.getMessage();
                System.out.println("response err " + e.getMessage());
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            if (result != null) {
                if (result.contains("Timeout") || result.contains("timeout")) {
//                    getLetterList();
                    showDialogRetry(result, "letter");
                    dialog.dismiss();
                    return;
                }
                if (!result.equalsIgnoreCase("99")) {
                    if (!result.equalsIgnoreCase("No Record found")) {
                        counterLayout.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (mContext != null/* && !isFinishing()*/) {
                                    dialog.dismiss();
                                    try {
                                        JSONArray jsonarray = new JSONArray(result);
                                        if (jsonarray.length() > 0) {

                                            for (int i = 0; i < jsonarray.length(); i++) {
                                                JSONObject obj = jsonarray.getJSONObject(i);
                                                ModelLetters letter = new ModelLetters(
                                                        obj.getInt("ID"),
                                                        obj.getString("LetterNo"),
                                                        obj.getString("LetterSubject"),
                                                        obj.getString("Photo"),
                                                        obj.getString("Photo1"),
                                                        obj.getString("Photo2"),
                                                        obj.getString("Photo3"),
                                                        obj.getString("Photo4"),
                                                        obj.getString("Photo5"),
                                                        obj.getString("Status"),
                                                        obj.getString("EntryDate"),
                                                        obj.getString("Comment"),
                                                        obj.getString("Source_Subject"),
                                                        obj.getString("PA_Instruction"),
                                                        obj.getString("TotalRecvAsOn"),
                                                        obj.getString("TotalPendAsOn"),
                                                        obj.getString("TotalDispAsOn"),
                                                        obj.getString("TodayReceived"),
                                                        obj.getString("TodayPending"),
                                                        obj.getString("TodayDisposed"),
                                                        obj.getString("MarkedTo"),
                                                        obj.getString("ReadStatus"));

                                                letters.add(letter);

                                            }
                                            JSONObject obj = jsonarray.getJSONObject(0);
                                            totalFiles = obj.getString("TotalRecvAsOn");
                                            todayFiles = obj.getString("TodayReceived");
                                            totalDisposed = obj.getString("TotalDispAsOn");
                                            todayDisposed = obj.getString("TodayDisposed");
                                            totalPending = obj.getString("TotalPendAsOn");
                                            todayPending = obj.getString("TodayPending");

                                            totalFilesTv.setText("Total Letters: " + totalFiles);
                                            todayFilesTv.setText("Today Letters: " + todayFiles);
                                            totalDisposedFilesTv.setText("Total Disposed: " + totalDisposed);
                                            todayDisposedFilesTv.setText("Disposed Today: " + todayDisposed);
                                            totalPendingFilesTv.setText("Total Pending: " + totalPending);
                                            todayPendingFilesTv.setText("Pending Today: " + todayPending);
                                            getOfficerList();

                                        } else {
                                            showAlertDialog(LetterDisposeActivity.this, "No record found", false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showDialogRetry("Some error occurred..json exception", "letter");

                                    }
                                }
                            }
                        }, 2000);
                    } else {
                        dialog.dismiss();
                        letterAdapter = new LetterAdapter(letters, mContext);
                        recyclerView.setAdapter(letterAdapter);
                        letterAdapter.notifyDataSetChanged();
                        showAlertDialog(LetterDisposeActivity.this, "No letter pending for disposal.", false);
                    }
                } else {
                    showDialogRetry("Something went wrong!! please try again", "letter");
                    dialog.dismiss();

                }
            } else {
                dialog.dismiss();
                showDialogRetry("Some error occurred, please try again", "letter");
            }
        }
    }

    private class AsyncCallOfficers extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            officers = new ArrayList<>();
            dialog = new ProgressDialog(LetterDisposeActivity.this);
            dialog.setMessage("Loading Officer Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_GetMarkedOfficerLetterDisposal);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL, 10000);
                androidHttpTransport.call(MyConstants.SOAP_ACTION_GetMarkedOfficerLetterDisposal, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
                System.out.println("Call Time " + webResponse);
                System.out.println("response time" + webResponse);

            } catch (Exception e) {
                webResponse = e.getMessage();
                System.out.println("response err " + e.getMessage());
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(final String result) {

            if (result != null) {
                if (result.contains("Timeout") || result.contains("timeout")) {
                    dialog.dismiss();
                    showDialogRetry(result, "officer");
//                    getOfficerList();
                    return;
                }
                if (!result.equalsIgnoreCase("99")) {
                    if (!result.equalsIgnoreCase("No Record found")) {
                        counterLayout.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (mContext != null/* && !isFinishing()*/) {
                                    dialog.dismiss();
                                    try {
                                        JSONArray jsonarray = new JSONArray(result);
                                        if (jsonarray.length() > 0) {
                                            for (int i = 0; i < jsonarray.length(); i++) {
                                                JSONObject obj = jsonarray.getJSONObject(i);
                                                ModelOfficer officer = new ModelOfficer(
                                                        obj.getString("Officer_Name"),
                                                        obj.getString("Actual_Designation"),
                                                        obj.getString("Mobile_Number"));
                                                officers.add(officer);
                                            }
                                            userPreferences.setOfficers(officers);
                                            letterAdapter = new LetterAdapter(letters, mContext);
                                            recyclerView.setAdapter(letterAdapter);
                                            letterAdapter.notifyDataSetChanged();
                                        } else {
                                            showAlertDialog(LetterDisposeActivity.this, "No record found", false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showDialogRetry("Some error occurred..json exception", "officer");

                                    }
                                }
                            }
                        }, 500);
                    } else {
                        dialog.dismiss();
                        showAlertDialog(LetterDisposeActivity.this, "No officer found ", false);
                    }
                } else {
                    showDialogRetry("Something went wrong!! please try again", "officer");
                    dialog.dismiss();

                }
            } else {
                dialog.dismiss();
                showDialogRetry("Some error occurred, please try again", "officer");
            }
        }
    }


    public void showDialogRetry(String Message, final String Async) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(LetterDisposeActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(LetterDisposeActivity.this);
        }
//        builder.setTitle("Queue Management System");
        builder.setCancelable(false);
        builder.setMessage(Message);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (ConnectionDetector.isConnectedToInernet(LetterDisposeActivity.this)) {
                    if (Async.equalsIgnoreCase("officer")) {
                        getOfficerList();
                    } else if (Async.equalsIgnoreCase("letter")) {
                        getLetterList();
                    }

                } else {
                    ShowDialog.showAlertDialog(LetterDisposeActivity.this, "Please check internet service", false);
                }
            }
        });
        builder.show();
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == LETTER_DISPOSE) {
            if (resultCode == RESULT_OK) {
                if (ConnectionDetector.isConnectedToInernet(LetterDisposeActivity.this)) {
                    getLetterList();
                } else {
                    ShowDialog.showAlertDialog(LetterDisposeActivity.this, "Please check internet service", false);
                }

            }
        }
    }


}
