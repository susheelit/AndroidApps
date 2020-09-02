package com.ceodelhi.filesystemapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.SearchLetterAdapter;
import com.ceodelhi.filesystemapp.model.ModelLetters;
import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.utility.ShowDialog;
import com.ceodelhi.filesystemapp.utility.UserContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;

import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;


public class SearchLetterActivity extends AppCompatActivity {

    private String photo, name;
    Context mContext;
    private EditText etSearch;
    private String webResponse;
    private ArrayList<String> letterImages;
    private ImageView search;
    private LinearLayout searchLayout;
    private TextView searchSubjectTv, searchDateTv, submit;
    private CalendarView simpleCalendarView;
    private ArrayList<ModelLetters> letters;
    private String LetterSubject = "";
    private String EntryDate = "";
    private RecyclerView letterRecyclerView;
    private SearchLetterAdapter searchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_letter);
        mContext = SearchLetterActivity.this;
        searchLayout = findViewById(R.id.searchLayout);
        search = findViewById(R.id.search);
        submit = findViewById(R.id.submit);
        search.setVisibility(View.GONE);
        etSearch = findViewById(R.id.etSearch);
        searchSubjectTv = findViewById(R.id.searchSubjectTv);
        searchDateTv = findViewById(R.id.searchDateTv);
        simpleCalendarView = findViewById(R.id.simpleCalendarView);
        simpleCalendarView.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(this);
        letterRecyclerView = findViewById(R.id.fileListView);
        letterRecyclerView.setHasFixedSize(true);
        letterRecyclerView.setLayoutManager(mLayoutManager);
        searchSubjectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout.setVisibility(View.VISIBLE);
                simpleCalendarView.setVisibility(View.GONE);
                letterRecyclerView.setVisibility(View.GONE);
            }
        });

        searchDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout.setVisibility(View.GONE);
                simpleCalendarView.setVisibility(View.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                simpleCalendarView.setMaxDate(calendar.getTimeInMillis());
                letterRecyclerView.setVisibility(View.GONE);
            }
        });

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                System.out.println("date " + Date);
                EntryDate = UserContext.dateFormat_date(Date);
                LetterSubject = "";
                simpleCalendarView.setVisibility(View.GONE);
                letterRecyclerView.setVisibility(View.GONE);
                getFileList();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryDate = "";
                LetterSubject = etSearch.getText().toString().trim();
                hidekeyBoard();
                letterRecyclerView.setVisibility(View.GONE);
                getFileList();
            }
        });
    }

    private void getFileList() {
        if (ConnectionDetector.isConnectedToInernet(mContext)) {
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else {
            showAlertDialog(SearchLetterActivity.this, "Please check internet service", false);
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SearchLetterActivity.this);
            dialog.setMessage("Loading Data, Please Wait ... ");
            dialog.setCancelable(false);
            dialog.show();
            letters = new ArrayList<>();
            System.out.println("EntryDate " + EntryDate);
            System.out.println("FileSubject " + LetterSubject);

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(MyConstants.NAMESPACE, MyConstants.METHOD_NAME_SearchGetLetterDisposal);
                request.addProperty("LetterSubject", LetterSubject);
                request.addProperty("EntryDate", EntryDate);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL);
                androidHttpTransport.call(MyConstants.SOAP_ACTION_SearchGetLetterDisposal, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
                System.out.println("response " + webResponse);

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
                    showDialogRetry(result);
                    return;
                }
                if (!result.equalsIgnoreCase("99")) {
                    if (!result.equalsIgnoreCase("No Record found")) {
                        letterRecyclerView.setVisibility(View.VISIBLE);
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
                                                searchAdapter = new SearchLetterAdapter(letters, mContext);
                                                letterRecyclerView.setAdapter(searchAdapter);
                                                letterRecyclerView.setVisibility(View.VISIBLE);
                                                simpleCalendarView.setVisibility(View.GONE);
                                                searchAdapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            showAlertDialog(SearchLetterActivity.this, "No record found", false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showDialogRetry("Some error occurred..json exception");

                                    }
                                }
                            }
                        }, 500);
                    } else {
                        dialog.dismiss();
                        showAlertDialog(SearchLetterActivity.this, "No record found", false);
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

    private String checkforNull(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }

    public void showDialogRetry(String Message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(SearchLetterActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(SearchLetterActivity.this);
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
                if (ConnectionDetector.isConnectedToInernet(SearchLetterActivity.this)) {
                    getFileList();
                } else {
                    ShowDialog.showAlertDialog(SearchLetterActivity.this, "Please check internet service", false);
                }
            }
        });
        builder.show();
    }

    protected void hidekeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

