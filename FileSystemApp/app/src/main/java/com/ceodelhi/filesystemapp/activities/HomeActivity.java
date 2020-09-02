package com.ceodelhi.filesystemapp.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.utility.ConnectionDetector;
import com.ceodelhi.filesystemapp.utility.MyConstants;
import com.ceodelhi.filesystemapp.utility.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.ceodelhi.filesystemapp.utility.MyConstants.NAMESPACE;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialog;
import static com.ceodelhi.filesystemapp.utility.ShowDialog.showAlertDialogFinish;

public class HomeActivity extends AppCompatActivity {


    private TextView filesBtn, letterBtn, tvFileNoti, tvLetterNoti;
    private ImageView search;
    UserPreferences userPreferences;
    private ProgressDialog progressDialog;
    private String webResponse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);
        filesBtn = findViewById(R.id.filesBtn);
        letterBtn = findViewById(R.id.letterBtn);
        tvFileNoti = findViewById(R.id.tvFileNoti);
        tvLetterNoti = findViewById(R.id.tvLetterNoti);
        userPreferences = new UserPreferences(this);

        //showNewNotification();
      //  new getPendingFileandLetter().execute();

        filesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ConnectionDetector.isConnectedToInernet(HomeActivity.this)) {
                    startActivity(new Intent(HomeActivity.this, FileDisposeActivity.class));
                  //  removeFileNoti();
                } else {
                    showAlertDialog(HomeActivity.this, "Please check your internet connection", false);
                }
            }
        });
        letterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionDetector.isConnectedToInernet(HomeActivity.this)) {
                    startActivity(new Intent(HomeActivity.this, LetterDisposeActivity.class));
                  //  removeLetterNoti();
                } else {
                    showAlertDialog(HomeActivity.this, "Please check your internet connection", false);
                }
            }
        });

    }


    private class getPendingFileandLetter extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, MyConstants.METHOD_PENDING_LETTER_FILE);
              //  request.addProperty("DeviceID", params[0]);
              //  request.addProperty("DeviceToken", params[1]);
               // request.addProperty("MobileNo", mobile);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(MyConstants.BASE_URL);

                androidHttpTransport.call(MyConstants.SOAP_ACTION_PENDING_LETTER_FILE, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                webResponse2 = response.toString();
                System.out.println("test response 123" + response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("test Exception " + e.getMessage());
            }
            return webResponse2;
        }

        @Override
        protected void onPostExecute(final String result) {

            if (!result.equalsIgnoreCase("1")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (this != null && !isFinishing()) {

                            try {
                                // set 0 default file letter status
                                userPreferences.setLetterorFileStatus("0");

                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String pendingFile = jsonObject.getString("FilePending");
                                String pendingLetter = jsonObject.getString("LetterPending");
                                showNewNotification(pendingFile, pendingLetter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
            } else {
                showAlertDialogFinish(HomeActivity.this, getString(R.string.error));
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get pending file letter when update status is 1
        if(userPreferences.getLetterorFileStatus().equals("1")){
            new getPendingFileandLetter().execute();
        }

    }

    private void showNewNotification(String pendingFile, String pendingLetter) {
        if(pendingFile != null && !pendingFile.equalsIgnoreCase("0")){
            tvFileNoti.setText(""+pendingFile);
            tvFileNoti.setVisibility(View.VISIBLE);
        }

       // Log.e("getLetterNotification ", userPreferences.getLetterNotification());
       // Log.e("getFileNotification ", userPreferences.getFileNotification());

        if(pendingLetter != null && !pendingLetter.equalsIgnoreCase("0")){
            tvLetterNoti.setText(""+pendingLetter);
            tvLetterNoti.setVisibility(View.VISIBLE);
        }
    }

   /* private void removeFileNoti(){
       // tvFileNoti.setText("0");
        userPreferences.setFileNotification("0");
        tvFileNoti.setVisibility(View.GONE);
    }

    private void removeLetterNoti(){
       // tvLetterNoti.setText("0");
        userPreferences.setLetterNotification("0");
        tvLetterNoti.setVisibility(View.GONE);
    }*/

    public void onBackPressed() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(HomeActivity.this);
        }
        builder.setTitle("FileApp");
        builder.setCancelable(false);
        builder.setMessage("Sure! You want to exit the FileApp ?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

}
