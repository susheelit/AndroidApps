package com.ceodelhi.filesystemapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.LetterImagesAdapter;
import com.ceodelhi.filesystemapp.model.ModelLetters;
import com.ceodelhi.filesystemapp.model.ModelOfficer;
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

import static com.ceodelhi.filesystemapp.utility.MyConstants.LETTER_MARKED;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;

//http://android-er.blogspot.com/2016/05/implement-pinch-to-zoom-with.html

public class LetterFullScreen extends AppCompatActivity {


    private TextView letter_number_tv, letter_sub_tv, source_tv, markTv, submit, markedOfficersTv;
    private ModelLetters letter;
    //    private Spinner spStatus;
//    private ArrayAdapter<String> spinnerStatusArrayAdapter;
    private EditText etStatus, etInstructions;
    private int pos;
    private String webResponse;
    private String instructions;
    private RecyclerView fileImageGridView;
    private LetterImagesAdapter letterImagesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> letterImages;
    private ImageView search;
    private String markedOfficersData = "";
    private ArrayList<ModelOfficer> selectedOfficers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_fullscreen);
        letter_sub_tv = (TextView) findViewById(R.id.letter_subject_tv);
        letter_number_tv = (TextView) findViewById(R.id.letter_number_tv);
        markedOfficersTv = (TextView) findViewById(R.id.markedOfficersTv);
        source_tv = (TextView) findViewById(R.id.source_tv);
        search = findViewById(R.id.search);
        markTv = findViewById(R.id.markTv);
//        spStatus = findViewById(R.id.spStatus);
        etStatus = findViewById(R.id.etStatus);
        etInstructions = findViewById(R.id.etInstructions);
        submit = findViewById(R.id.submit);
        search.setVisibility(View.GONE);
        fileImageGridView = findViewById(R.id.fileImageGridView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        letterImages = new ArrayList<>();
        Intent i = getIntent();
        letter = (ModelLetters) i.getSerializableExtra("letter");
        buildArraylistImages(letter.getPhoto());
        buildArraylistImages(letter.getPhoto1());
        buildArraylistImages(letter.getPhoto2());
        buildArraylistImages(letter.getPhoto3());
        buildArraylistImages(letter.getPhoto4());
        buildArraylistImages(letter.getPhoto5());

        letterImagesAdapter = new LetterImagesAdapter(letterImages, this);
        fileImageGridView.setAdapter(letterImagesAdapter);
        fileImageGridView.setLayoutManager(mLayoutManager);
//        fileImageGridView.addItemDecoration(new SimpleDividerItemDecoration(FullScreen.this));
//        fileImageGridView.addItemDecoration(new DividerItemDecoration(FullScreen.this, DividerItemDecoration.VERTICAL_LIST));
        fileImageGridView.addItemDecoration(new DividerItemDecoration(LetterFullScreen.this, DividerItemDecoration.HORIZONTAL_LIST));

        letter_number_tv.setText("Letter No. : " + (checkforNull(letter.getLetterNo())));
        letter_sub_tv.setText("Letter Subject : " + (checkforNull(letter.getLetterSubject())));
        source_tv.setText("Source : " + (checkforNull(letter.getSource_Subject())));

        etInstructions.setText((checkforNull(letter.getPA_Instruction())));

        markTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LetterFullScreen.this, ActivityMarkOfficer.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), LETTER_MARKED);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOfficers.size() != 0) {
                    updateMarkedOfficers();
                } else {

                }

            }
        });
    }

    private void buildArraylistImages(String photo) {
        System.out.println("photo " + photo);
        if (photo != null) {
            if (photo.equals("") || (photo.equals("null") || photo.equalsIgnoreCase("http://ceodelhinet.nic.in/onlineErmsDept/Images/LetterDisposal/"))) {
            } else {
                letterImages.add(photo);
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

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == LETTER_MARKED) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    selectedOfficers = (ArrayList<ModelOfficer>) data.getSerializableExtra("officers");
                    for (int i = 0; i < selectedOfficers.size(); i++) {
                        markedOfficersData = markedOfficersData + ", " + selectedOfficers.get(i).getActual_Designation();
                    }
                    markedOfficersTv.setText("Marked to: " + markedOfficersData.substring(1));
                    markedOfficersTv.setVisibility(View.VISIBLE);
                    markTv.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateMarkedOfficers() {
        if (ConnectionDetector.isConnectedToInernet(LetterFullScreen.this)) {
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else {
            showAlertDialog(LetterFullScreen.this, "Please check internet service", false);
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        String comments = etStatus.getText().toString();
        String pa_instructions = etInstructions.getText().toString();

        @Override

        protected void onPreExecute() {
            dialog = new ProgressDialog(LetterFullScreen.this);
            dialog.setMessage("Loading Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_UpdateLetterDisposalStatus);
                request.addProperty("ID", letter.getID());
                request.addProperty("LetterNumber", letter.getLetterNo());
                request.addProperty("Status", "Marked");
                request.addProperty("comment", comments);
                request.addProperty("PA_Instruction", pa_instructions);
                request.addProperty("MarkedTo", markedOfficersData.substring(1));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL);
                androidHttpTransport.call(MyConstants.SOAP_ACTION_UpdateLetterDisposalStatus, envelope);
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
                if (result.contains("Timeout") || result.contains("timeout")) {
                    dialog.dismiss();
                    showDialogRetry(result);
                    return;
                }
                if (!result.equalsIgnoreCase("99")) {
                    if (!result.equalsIgnoreCase("No Record found")) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (LetterFullScreen.this != null/* && !isFinishing()*/) {
                                    dialog.dismiss();
                                    if (result.equals("1")) {
                                        // set 1 when letter status change
                                        UserPreferences userPreferences = new UserPreferences(LetterFullScreen.this);
                                        userPreferences.setLetterorFileStatus("1");
                                        showAlertDialogFinish("Done successfully");
                                    } else if (result.equals("0")) {
                                        showAlertDialog(LetterFullScreen.this, "Submit Failed", false);
                                    }

                                }
                            }
                        }, 500);
                    } else {
                        dialog.dismiss();
                        showAlertDialog(LetterFullScreen.this, "No letter uploaded yet.", false);
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
            builder = new AlertDialog.Builder(LetterFullScreen.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(LetterFullScreen.this);
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
                if (ConnectionDetector.isConnectedToInernet(LetterFullScreen.this)) {
                    updateMarkedOfficers();
                } else {
                    ShowDialog.showAlertDialog(LetterFullScreen.this, "Please check internet service", false);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void showAlertDialogFinish(String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(LetterFullScreen.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(LetterFullScreen.this);
        }

        builder.setCancelable(false);
        builder.setMessage(Message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

}

