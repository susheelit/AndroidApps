package com.ceodelhi.filesystemapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.FileImagesAdapter;
import com.ceodelhi.filesystemapp.model.ModelFiles;
import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.utility.DividerItemDecoration;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.utility.ShowDialog;
import com.ceodelhi.filesystemapp.utility.UserPreferences;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;

//http://android-er.blogspot.com/2016/05/implement-pinch-to-zoom-with.html

public class FileFullScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView file_id_tv, file_name_tv, file_desc_tv, file_page_tv, submit;
    private String photo, name;
    private ModelFiles file;
    private ProgressBar loading;
    private Spinner spStatus;
    private ArrayAdapter<String> spinnerStatusArrayAdapter;
    private EditText etStatus, etInstructions;
    private int pos;
    private String webResponse;
    private String instructions;
    private RecyclerView fileImageGridView;
    private FileImagesAdapter fileImagesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> fileImages;
    private ImageView search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        file_name_tv = (TextView) findViewById(R.id.file_name_tv);
        file_id_tv = (TextView) findViewById(R.id.file_id_tv);
        file_desc_tv = (TextView) findViewById(R.id.file_desc_tv);
        file_page_tv = (TextView) findViewById(R.id.file_page_tv);
        search = findViewById(R.id.search);
        loading = (ProgressBar) findViewById(R.id.loading);
        spStatus = findViewById(R.id.spStatus);
        etStatus = findViewById(R.id.etStatus);
        etInstructions = findViewById(R.id.etInstructions);
        submit = findViewById(R.id.submit);
        search.setVisibility(View.GONE);
        fileImageGridView = findViewById(R.id.fileImageGridView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fileImages = new ArrayList<>();
        Intent i = getIntent();
        file = (ModelFiles) i.getSerializableExtra("file");
        buildArraylistImages(file.getPhoto());
        buildArraylistImages(file.getPhoto1());
        buildArraylistImages(file.getPhoto2());
        buildArraylistImages(file.getPhoto3());
        buildArraylistImages(file.getPhoto4());
        buildArraylistImages(file.getPhoto5());

        fileImagesAdapter = new FileImagesAdapter(fileImages, this);
        fileImageGridView.setAdapter(fileImagesAdapter);
        fileImageGridView.setLayoutManager(mLayoutManager);
//        fileImageGridView.addItemDecoration(new SimpleDividerItemDecoration(FullScreen.this));
//        fileImageGridView.addItemDecoration(new DividerItemDecoration(FullScreen.this, DividerItemDecoration.VERTICAL_LIST));
        fileImageGridView.addItemDecoration(new DividerItemDecoration(FileFullScreen.this, DividerItemDecoration.HORIZONTAL_LIST));

        file_id_tv.setText("File No. : " + (checkforNull(file.getFileNo())));
        file_name_tv.setText("File Name : " + (checkforNull(file.getFileSubject())));
        file_desc_tv.setText("Proposal in brief : " + (checkforNull(file.getPROP_Subject())));
        file_page_tv.setText("Page No. : " + (checkforNull(file.getPROP_Page())));
        etInstructions.setText((checkforNull(file.getPA_Instruction())));

        spinnerStatusArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.status));
        spinnerStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(spinnerStatusArrayAdapter);

        spStatus.setOnItemSelectedListener(this);
        spStatus.setSelection(0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitStatus();
            }
        });
        if (file.getStatus() != null) {
            int spinnerPosition = spinnerStatusArrayAdapter.getPosition(file.getStatus());
            spStatus.setSelection(spinnerPosition);
        }

        if (file.getStatus() != null && file.getComment() != null) {
            int spinnerPosition = spinnerStatusArrayAdapter.getPosition(file.getStatus());
            if (spinnerPosition == 3 || spinnerPosition == 2) {
                if (!file.getComment().isEmpty()) {
                    etStatus.setText(file.getComment());
                    etStatus.setMaxLines(5);
                    etStatus.setVerticalScrollBarEnabled(true);
                } else {
                    etStatus.setText("");
                }
            } else {
                etStatus.setText("");
            }
        }

        if (file.getStatus() != null && file.getPA_Instruction() != null) {
            int spinnerPosition = spinnerStatusArrayAdapter.getPosition(file.getStatus());
            if (spinnerPosition == 4) {
                if (!file.getPA_Instruction().isEmpty()) {
                    etInstructions.setText(file.getPA_Instruction());
                    etInstructions.setMaxLines(2);
                    etInstructions.setVerticalScrollBarEnabled(true);
                } else {
                    etInstructions.setText("");
                }
            } else {
                etInstructions.setText("");
            }
        }
    }

    private void buildArraylistImages(String photo) {
        System.out.println("photo " + photo);
        if (photo != null) {
            if (photo.equals("") || (photo.equals("null") || photo.equalsIgnoreCase("ceodelhinet.nic.in/onlineErmsDept/Images/FileDisposal/"))) {
            } else {
                fileImages.add(photo);
            }
        }
    }

    private String checkforNull(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }

    private void submitStatus() {
        if (!etInstructions.getText().toString().isEmpty()) {
            instructions = etInstructions.getText().toString();
        }
        if (spStatus.getSelectedItemPosition() == 2 || spStatus.getSelectedItemPosition() == 3) {
            if (etStatus.getText().toString().isEmpty()) {
                showAlertDialog(FileFullScreen.this, "Please enter comments", false);
            } else if (ConnectionDetector.isConnectedToInernet(FileFullScreen.this)) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            } else {
                showAlertDialog(FileFullScreen.this, "Please check internet service", false);
            }
        } else if (ConnectionDetector.isConnectedToInernet(FileFullScreen.this)) {
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else {
            showAlertDialog(FileFullScreen.this, "Please check internet service", false);
        }
        if (pos == 1) {
            etStatus.setText("");
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String status = spStatus.getSelectedItem().toString();
        String comments = etStatus.getText().toString();
        String pa_instructions = etInstructions.getText().toString();

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FileFullScreen.this);
            dialog.setMessage("Loading Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
            System.out.println("status " + status);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_UpdateFileDisposalStatus);
                request.addProperty("ID", file.getID());
                request.addProperty("FileNumber", file.getFileNo());
                request.addProperty("Status", status);
                request.addProperty("comment", comments);
                request.addProperty("PA_Instruction", pa_instructions);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL);
                androidHttpTransport.call(MyConstants.SOAP_ACTION_UpdateFileDisposalStatus, envelope);
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
            if (result != null) {
                if (result.contains("Timeout")|| result.contains("timeout")) {
                    dialog.dismiss();
                    showDialogRetry(result);
//                    submitStatus();
                    return;
                }

                Log.e("result", "" + result);
                if (!result.equalsIgnoreCase("99")) {
                    if (!result.equalsIgnoreCase("No Record found")) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (FileFullScreen.this != null/* && !isFinishing()*/) {
                                    dialog.dismiss();
                                    if (result.equals("1")) {
                                        // set 1 when file status change
                                        UserPreferences userPreferences = new UserPreferences(FileFullScreen.this);
                                        userPreferences.setLetterorFileStatus("1");
                                        showAlertDialogFinish(FileFullScreen.this, "Submit success");
                                    } else if (result.equals("0")) {
                                        showAlertDialog(FileFullScreen.this, "Submit Failed", false);
                                    }

                                }
                            }
                        }, 500);
                    } else {
                        dialog.dismiss();
                        showAlertDialog(FileFullScreen.this, "No file uploaded yet.", false);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        if (i == 1 || i == 2 || i == 3|| i == 4) {
            submit.setVisibility(View.VISIBLE);
        } else {
            submit.setVisibility(View.GONE);
        }
        if (i == 2 || i == 3) {
            etStatus.setVisibility(View.VISIBLE);
        } else {
            etStatus.setVisibility(View.GONE);
        }

        if (i == 4) {
            etInstructions.setVisibility(View.VISIBLE);
        } else {
            etInstructions.setVisibility(View.GONE);
        }
        if (i == 3) {
            etStatus.setHint(" Enter Remarks");
        } else {
            etStatus.setHint(" Enter Comments");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showDialogRetry(String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(FileFullScreen.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(FileFullScreen.this);
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
                if (ConnectionDetector.isConnectedToInernet(FileFullScreen.this)) {
                    submitStatus();
                } else {
                    ShowDialog.showAlertDialog(FileFullScreen.this, "Please check internet service", false);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public void showAlertDialogFinish(Context ctx, String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
//        builder.setTitle("Queue Management System");
        builder.setCancelable(false);
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        builder.show();
    }


}

