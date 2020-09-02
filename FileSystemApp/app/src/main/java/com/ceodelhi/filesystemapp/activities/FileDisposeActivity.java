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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.adapter.FileAdapter;
import com.ceodelhi.filesystemapp.model.ModelFiles;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.utility.ShowDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static com.ceodelhi.filesystemapp.utility.MyConstants.FILE_DISPOSE;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;

//https://www.youtube.com/watch?v=2-vZ1g_G1Zo
public class FileDisposeActivity extends AppCompatActivity {


    Context mContext;
    private RecyclerView fileListView;
    private FileAdapter fileAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String webResponse;
    private ArrayList<ModelFiles> files;
    private boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFilesTv, todayFilesTv, totalDisposedFilesTv,
            todayDisposedFilesTv, totalPendingFilesTv, todayPendingFilesTv;
    private String totalFiles, todayFiles, totalDisposed, todayDisposed, totalPending, todayPending;
    private ImageView search;
    private LinearLayout counterLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_dispose);
        fileListView = (RecyclerView) findViewById(R.id.fileListView);
        fileListView.setHasFixedSize(true);
        mContext = FileDisposeActivity.this;
        mLayoutManager = new LinearLayoutManager(this);
        fileListView.setLayoutManager(mLayoutManager);
        counterLayout = findViewById(R.id.counterLayout);
        progress = findViewById(R.id.progress);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        totalFilesTv = findViewById(R.id.totalFilesTv);
        todayFilesTv = findViewById(R.id.todayFilesTv);
        totalDisposedFilesTv = findViewById(R.id.totalDisposedFilesTv);
        todayDisposedFilesTv = findViewById(R.id.todayDisposedFilesTv);
        totalPendingFilesTv = findViewById(R.id.totalPendingFilesTv);
        todayPendingFilesTv = findViewById(R.id.todayPendingFilesTv);
        counterLayout.setVisibility(View.GONE);

        search = findViewById(R.id.search);
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FileDisposeActivity.this, SearchFileActivity.class));
            }
        });
        getFileList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFileList();
            }
        });
    }


    private void getFileList() {
        if (ConnectionDetector.isConnectedToInernet(mContext)) {
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else {
            showAlertDialog(FileDisposeActivity.this, "Please check internet service", false);
        }
    }


    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FileDisposeActivity.this);
            dialog.setMessage("Loading Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
            files = new ArrayList<>();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_GetFileDisposal);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL, 10000);
                androidHttpTransport.call(MyConstants.SOAP_ACTION_GetFileDisposal, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
                System.out.println("response " + webResponse);

            } catch (Exception e) {
                webResponse = e.getMessage();
                System.out.println("response err" + e.getMessage());
                e.printStackTrace();
            }
            return webResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (result != null) {
                if (result.contains("Timeout") || result.contains("timeout") || result.contains("Read timed out")) {
                    dialog.dismiss();
                    showDialogRetry(result);
//                    getFileList();
                    return;
                }
                Log.e("result", "" + result);
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
                                                ModelFiles file = new ModelFiles(
                                                        obj.getInt("ID"),
                                                        obj.getString("FileNo"),
                                                        obj.getString("FileSubject"),
                                                        obj.getString("Photo"),
                                                        obj.getString("Photo1"),
                                                        obj.getString("Photo2"),
                                                        obj.getString("Photo3"),
                                                        obj.getString("Photo4"),
                                                        obj.getString("Photo5"),
                                                        obj.getString("Status"),
                                                        obj.getString("EntryDate"),
                                                        obj.getString("Comment"),
                                                        obj.getString("PROP_Subject"),
                                                        obj.getString("PROP_Page"),
                                                        obj.getString("PA_Instruction"),
                                                        obj.getString("TotalRecvAsOn"),
                                                        obj.getString("TotalPendAsOn"),
                                                        obj.getString("TotalDispAsOn"),
                                                        obj.getString("TodayReceived"),
                                                        obj.getString("TodayPending"),
                                                        obj.getString("TodayDisposed"));

                                                files.add(file);
                                                fileAdapter = new FileAdapter(files, mContext);
                                                fileListView.setAdapter(fileAdapter);
                                                fileAdapter.notifyDataSetChanged();

                                            }
                                            JSONObject obj = jsonarray.getJSONObject(0);
                                            totalFiles = obj.getString("TotalRecvAsOn");
                                            todayFiles = obj.getString("TodayReceived");
                                            totalDisposed = obj.getString("TotalDispAsOn");
                                            todayDisposed = obj.getString("TodayDisposed");
                                            totalPending = obj.getString("TotalPendAsOn");
                                            todayPending = obj.getString("TodayPending");

                                            totalFilesTv.setText("Total Files: " + totalFiles);
                                            todayFilesTv.setText("Today Files: " + todayFiles);
                                            totalDisposedFilesTv.setText("Total Disposed: " + totalDisposed);
                                            todayDisposedFilesTv.setText("Disposed Today: " + todayDisposed);
                                            totalPendingFilesTv.setText("Total Pending: " + totalPending);
                                            todayPendingFilesTv.setText("Pending Today: " + todayPending);

                                        } else {
                                            showAlertDialog(FileDisposeActivity.this, "No record found", false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showDialogRetry("Some error occurred..json exception");

                                    }
                                }
                            }
                        }, 2000);
                    } else {
                        dialog.dismiss();
                        fileAdapter = new FileAdapter(files, mContext);
                        fileListView.setAdapter(fileAdapter);
                        fileAdapter.notifyDataSetChanged();
                        showAlertDialog(FileDisposeActivity.this, "No file pending for disposal.", false);
                    }
                } else {
                    showDialogRetry("Something went wrong!! please try again");
                    dialog.dismiss();
                }
            } else {
                dialog.dismiss();
                showDialogRetry("Some error occurred, please try again");
            }
        }
    }


    public void showDialogRetry(String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(FileDisposeActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(FileDisposeActivity.this);
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
                if (ConnectionDetector.isConnectedToInernet(FileDisposeActivity.this)) {
                    getFileList();
                } else {
                    ShowDialog.showAlertDialog(FileDisposeActivity.this, "Please check internet service", false);
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
        if (requestCode == FILE_DISPOSE) {
            if (resultCode == RESULT_OK) {
                if (ConnectionDetector.isConnectedToInernet(FileDisposeActivity.this)) {
                    getFileList();
                } else {
                    ShowDialog.showAlertDialog(FileDisposeActivity.this, "Please check internet service", false);
                }

            }
        }
    }

}
