package com.ceo.elc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.ceo.elc.R;
import com.ceo.elc.common.BaseActivity;
import com.ceo.elc.common.Konstant;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends BaseActivity {
    private TextView tveventDate;
    private EditText etDesc, etTitle;
    private Button btnSubmit;
    private Context mContext;
    String datetime_value = "";
    private Button create_btn, upload_btn;
    private LinearLayout event_layout, createEvent_layout;
    private String webResponse;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initView();
    }

    private void initView() {
        mContext = CreateEventActivity.this;
        toolbar = findViewById(R.id.toolbar);
        Konstant.setUpToolbar(toolbar, mContext);
        tveventDate = findViewById(R.id.tveventDate);
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        btnSubmit = findViewById(R.id.btnSubmit);
        create_btn = findViewById(R.id.create_btn);
        upload_btn = findViewById(R.id.upload_btn);
        event_layout = findViewById(R.id.event_layout);
        createEvent_layout = findViewById(R.id.createEvent_layout);

        upload_btn.setOnClickListener(onUpload);
        create_btn.setOnClickListener(onCreate);
        btnSubmit.setOnClickListener(onSubmit);
        tveventDate.setOnClickListener(onEventClick);
    }

    protected View.OnClickListener onEventClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogDatePickerLight();
        }
    };

    protected View.OnClickListener onSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String eventDate = tveventDate.getText().toString().trim();
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();

            if (!TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(title)) {
                new CreateEvent(title, desc).execute();
            } else {
                if (TextUtils.isEmpty(eventDate)) {
                    Konstant.altDialog(mContext, "Please select event date.");
                } else if (TextUtils.isEmpty(title)) {
                    Konstant.altDialog(mContext, "Please enter title.");
                } else {
                    Konstant.altDialog(mContext, "Please enter description.");
                }
            }

        }
    };

    protected View.OnClickListener onCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            event_layout.setVisibility(View.GONE);
            createEvent_layout.setVisibility(View.VISIBLE);
        }
    };

    protected View.OnClickListener onUpload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(CreateEventActivity.this, EventListActivity.class));
        }
    };

    /*get Event Date & Time*/
    private void dialogDatePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePicker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();

                        tveventDate.setText(Konstant.getFormattedDateSimple(date_ship_millis));
                        datetime_value = tveventDate.getText().toString();
                        dialogTimePickerLight(date_ship_millis);
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.primaryColor));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    private void dialogTimePickerLight(long date_ship_millis) {
        boolean isSelectedToday = false;
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        String todayDate = formatter.format(c);
        Log.e("Today", "" + todayDate);
        String selectedDate = Konstant.getFormattedDateSimple(date_ship_millis);
        Log.e("selected Today", "" + selectedDate);

        if (todayDate.equalsIgnoreCase(selectedDate)) {
            isSelectedToday = true;
        }

        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog datePicker = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                Log.e("TAG", "hourOfDay 1 " + hourOfDay);
                Log.e("TAG", "hourOfDay 2 " + minute);
                Log.e("TAG", "hourOfDay 3 " + second);

                tveventDate.setText(tveventDate.getText().toString() + " / " + hourOfDay + ":" + minute + ":" + second);
                tveventDate.setTextColor(getResources().getColor(R.color.colorBlack));
                datetime_value = tveventDate.getText().toString();
                // datetime.setError(null);
                Log.e("TAG", "selected date/time is " + datetime_value);
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
                tveventDate.setText("");
            }
        });


        //set dark light
        datePicker.setThemeDark(false);
        if (isSelectedToday) {
            datePicker.is24HourMode();
            datePicker.setMinTime(cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), 00);
        }
        datePicker.setAccentColor(getResources().getColor(R.color.primaryColor));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    public class CreateEvent extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String title, desc;

        public CreateEvent(String title, String desc) {
            this.title = title;
            this.desc = desc;
        }

        @Override
        protected void onPreExecute() {
            webResponse = "";
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Creating event...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(Konstant.NAMESPACE, Konstant.METHOD_InsertEvent);

                // to start service
                request.addProperty("ClubID", Integer.parseInt(getSetting("ClubID", "")));
                request.addProperty("EventDate", datetime_value);
                request.addProperty("EventTitle", "" + title);
                request.addProperty("EventDetails", "" + desc);
                request.addProperty("Status", "Upcoming");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Konstant.BASE_URL, 40000);

                androidHttpTransport.call(Konstant.SOAP_ACTION_InsertEvent, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return webResponse;
        }

        String EventId = "";

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
                            if (result.contains("EventID")) {
                                try {
                                    JSONArray jsonarray = new JSONArray(result);
                                    JSONObject obj = jsonarray.getJSONObject(0);
                                    EventId = obj.getString("EventID");
                                    Konstant.altDialog(CreateEventActivity.this, "Event created successfully, Event ID is " + EventId);
                                    onBackPressed();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Konstant.altDialog(mContext, "Something went wrong!!");
                            }
                        }
                    }
                }, 1000);
            } else {
                Konstant.toastShow(mContext, "Something went wrong!!");
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (createEvent_layout.getVisibility() == View.VISIBLE) {
            createEvent_layout.setVisibility(View.INVISIBLE);
            event_layout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (createEvent_layout.getVisibility() == View.VISIBLE) {
            createEvent_layout.setVisibility(View.INVISIBLE);
            event_layout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
        return true;
    }
}
