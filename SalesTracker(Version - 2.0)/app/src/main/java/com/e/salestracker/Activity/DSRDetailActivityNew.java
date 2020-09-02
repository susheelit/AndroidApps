package com.e.salestracker.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Modal.People;
import com.e.salestracker.R;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DSRDetailActivityNew extends AppCompatActivity implements View.OnClickListener {

    LinearLayout dsrdetaillinear;
    LinearLayout dsredittextlinear;
    String responsesave ;
    JSONArray jsonarrayfulldata ;
    HashMap<String,String> postdata;
    ArrayList<EditText> alledittext;
    ArrayList<Button> allButton;
    ArrayList<String> alltypes;
    ArrayList<String> allnames;
    JSONArray drop_down_data_array;
    String [] title;
    int k=0;
    int pos=0;
    ArrayList<RadioGroup> allradiogroup;
    ArrayList<Spinner>allSpinnergroup;
    ArrayList<Boolean> validation_list;
    ArrayList<Boolean> check_validation;

    ArrayList<TextView> radiogroup_textlist;

    String TAG = "salestracker.DSRDetailActivityNew";

    LinearLayout mainlinearlayout;

    Spinner category_spinner;

    Button submit;
    Button datetime;

    int radiogroup_no = 0;

    TextView date,name;

    int num_edittext =0;
    int num_button=0;
    int num_radiogroup = 0;
    int num_date = 0;

    String datetime_value = "";
    String selectedCat = "";

    String number_valid = "NUMBER";
    String value_valid = "VALUE";

    //intent data
    People obj;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsrdetailnew);

        initObj();
        initToolbar();
        Tools.setSystemBarColor(this);

        dsrdetaillinear = findViewById(R.id.lyt_form);
        dsredittextlinear = findViewById(R.id.edit_layout);

        mainlinearlayout = findViewById(R.id.mainlinearlayout);
        date=findViewById(R.id.date);
        name=findViewById(R.id.name);
        datetime = findViewById(R.id.select_date);

        postdata = new HashMap<String, String>();
        alledittext = new ArrayList<EditText>();
        allButton = new ArrayList<Button>();
        alltypes = new ArrayList<String>();
        allnames = new ArrayList<String>();
        allradiogroup = new ArrayList<RadioGroup>();
        allSpinnergroup = new ArrayList<Spinner>();
        validation_list = new ArrayList<Boolean>();
        radiogroup_textlist = new ArrayList<TextView>();
        check_validation = new ArrayList<Boolean>();

        category_spinner = new Spinner(DSRDetailActivityNew.this);

        setValues();

     /*   ArrayList<CustomEdittext> pp = new ArrayList<CustomEdittext>();

        ArrayList<String> ss = new ArrayList<String>();
        ss.add("name1");
        ss.add("name2");
        ss.add("name3");

        CustomEdittext customedit = new CustomEdittext("edittext",3,ss);

        for (int i = 0; i < ss.size(); i++) {
            AppCompatEditText editText = new AppCompatEditText(this);
            editText.setText(ss.get(i));
            editText.setTextSize(15);
            dsrdetaillinear.addView(editText);

            TextView tt = new TextView(this);
            dsrdetaillinear.addView(tt);

        }*/

        /*final List<RadioGroup> radio = new ArrayList<RadioGroup>();

        for (int row = 0; row < 5; row++) {

            TextView textview = new TextView(this);
            textview.setText("experience");
            textview.setTextSize(18);
            dsrdetaillinear.addView(textview);

            RadioGroup ll = new RadioGroup(this);
            radio.add(ll);
            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i <= 4; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText("Radio " + rdbtn.getId());
                ll.addView(rdbtn);
            }

        dsrdetaillinear.addView(ll);

           TextView tt = new TextView(this);
            dsrdetaillinear.addView(tt);
        }*/

        /*Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogDatePickerLight();
            }
        });

        dsrdetaillinear.addView(button); */

       getFormFormat();
        // getData();
       submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }
      public  void getData(){
          try {
              JSONArray jsonarray = new JSONArray(loadJSONFromAsset());
              jsonarrayfulldata = new JSONArray(loadJSONFromAsset());

              for(int i = 0;i<jsonarray.length();i++)
              {
                  String typeof = jsonarray.getJSONObject(i).getString("type");

                 // set EditText
                  if(typeof.equalsIgnoreCase("edit_text")) {
                      num_edittext = jsonarray.getJSONObject(i).getInt("number");
                      for (int j = 0; j < jsonarray.getJSONObject(i).getInt("number"); j++) {

                          //ss.add(jsonarray.getJSONObject(j).getJSONObject("0").getJSONArray("value").getString(j));
                          TextInputLayout til = new TextInputLayout(DSRDetailActivityNew.this);
                          AppCompatEditText editText = new AppCompatEditText(DSRDetailActivityNew.this);
                          editText.setHint(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));

                          //Log.e("salestrascker","new size "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                          editText.setTextSize(15);
                          til.addView(editText);
                          dsrdetaillinear.addView(til);

                          alledittext.add(editText);

                          if(!jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2).equalsIgnoreCase("no_data")) {
                              editText.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2));
                          }
                                    if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).equalsIgnoreCase("Number"))
                                    {
                                        editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                    }

                                    if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(number_valid)
                                            ||jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(value_valid)) {
                                        editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                                    }
                                    else {
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                        }

                                        TextView tt = new TextView(DSRDetailActivityNew.this);
                                    dsrdetaillinear.addView(tt);
                                    //for validation of radiobutto
                               TextView tv = new TextView(DSRDetailActivityNew.this);
                          radiogroup_textlist.add(tv);

                          //postdata.put(typeof+","+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j),"");
                          //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                          alltypes.add(typeof);
                          allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));
                          validation_list.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getBoolean(1));
                      }
                      //Set Radio group
                  } else if(typeof.equalsIgnoreCase("radio_group")) {


                      String  radioList= jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0);
                      Log.e("radioList ", "Test"+radioList);

                      List<RadioGroup> radio = new ArrayList<RadioGroup>();

                      num_radiogroup = jsonarray.getJSONObject(i).getInt("number");

                      for(int k = 0; k < jsonarray.getJSONObject(i).getInt("number"); k++)
                      {
                          TextView textview = new TextView(DSRDetailActivityNew.this);
                          textview.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                          textview.setTextSize(18);
                          dsrdetaillinear.addView(textview);
                          allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                          radiogroup_textlist.add(textview);

                          RadioGroup ll = new RadioGroup(DSRDetailActivityNew.this);
                          radio.add(ll);
                          ll.setOrientation(LinearLayout.VERTICAL);

                          Log.e(TAG,"value is "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getInt(1));

                          for (int l = 4; l < jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getInt(1)+4; l++) {
                              RadioButton rdbtn = new RadioButton(DSRDetailActivityNew.this);
                              rdbtn.setId(View.generateViewId());
                              rdbtn.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(l) );
                              ll.addView(rdbtn);

                              if(rdbtn.getText().toString().equalsIgnoreCase(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(3)))
                              {
                                  rdbtn.setChecked(true);
                              }
                          }

                          dsrdetaillinear.addView(ll);
                          //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                          alltypes.add(typeof);
                          allradiogroup.add(ll);


                          if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getBoolean(2) == true)
                          {
                              validation_list.add(true);
                          }
                          else
                          {
                              validation_list.add(false);
                          }

                          TextView tt = new TextView(DSRDetailActivityNew.this);
                          dsrdetaillinear.addView(tt);
                      }

                      // set TextView
                  }else if(typeof.equalsIgnoreCase("date")){


                      num_date = jsonarray.getJSONObject(i).getInt("number");

                      datetime.setVisibility(View.VISIBLE);
                      datetime.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(0));
                      datetime.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialogDatePickerLight();
                          }
                      });


                      if(!jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2).equalsIgnoreCase("no_data"))
                      {
                          datetime.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2));
                          datetime_value = jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2);
                      }


                      TextView tv = new TextView(DSRDetailActivityNew.this);
                      radiogroup_textlist.add(tv);

                      alltypes.add(typeof);
                      allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(0));
                      validation_list.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getBoolean(1));

                  }  // set Drop down
                  else if(typeof.equalsIgnoreCase("drop_down")){

                      //Toast.makeText(this,typeof , Toast.LENGTH_SHORT).show();
                      num_button = jsonarray.getJSONObject(i).getInt("number");
                      for (int j = 0; j < jsonarray.getJSONObject(i).getInt("number"); j++) {

                          //ss.add(jsonarray.getJSONObject(j).getJSONObject("0").getJSONArray("value").getString(j));
                          drop_down_data_array=jsonarray.getJSONObject(i).getJSONArray("data").getJSONObject(j).getJSONArray("drop_down_data");

                          title=new String[drop_down_data_array.length()];
                          if (title != null) {
                              for (int x = 0; x < drop_down_data_array.length(); x++) {
                                  title[x]="";
                              }
                          }
                          for (int y = 0; y <drop_down_data_array.length(); y++) {
                              Log.e("langth",Integer.toString(drop_down_data_array.length()));
                              JSONObject jsonObject1 = drop_down_data_array.getJSONObject(y);
                              title[y]=jsonObject1.getString("title");
                          }

                              Button button = (Button) LayoutInflater.from(this).inflate(R.layout.section_button, null);
                              button.setText(jsonarray.getJSONObject(i).getJSONArray("data").getJSONObject(j).getString("title"));
                              button.setId(j+1);
                              dsrdetaillinear.addView(button);
                              allButton.add(button);
                              alltypes.add(typeof);
                              allnames.add(jsonarray.getJSONObject(i).getJSONArray("data").getJSONObject(j).getString("id"));
                          for(int e = 0;e<drop_down_data_array.length();e++) {
                              typeof = drop_down_data_array.getJSONObject(e).getString("type");
                              if (typeof.equalsIgnoreCase("edit_text")) {
                                  num_edittext = drop_down_data_array.getJSONObject(e).getInt("number");
                                  for (int f = 0; f < drop_down_data_array.getJSONObject(e).getInt("number"); f++) {

                                      //ss.add(jsonarray.getJSONObject(j).getJSONObject("0").getJSONArray("value").getString(j));
                                      TextInputLayout til = new TextInputLayout(DSRDetailActivityNew.this);
                                      AppCompatEditText editText = new AppCompatEditText(DSRDetailActivityNew.this);
                                      editText.setHint(drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));

                                      //Log.e("salestrascker","new size "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                      editText.setTextSize(15);
                                      til.addView(editText);
                                      dsrdetaillinear.addView(til);
                                      alledittext.add(editText);

                                      if (!drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2).equalsIgnoreCase("no_data")) {
                                          editText.setText(drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2));
                                      }

                                      if (drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).equalsIgnoreCase("Number")) {
                                          editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                      }

                                      if (drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(number_valid)) {
                                          editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                          editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                                      } else {
                                          editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                      }

                                      TextView tt = new TextView(DSRDetailActivityNew.this);
                                      dsrdetaillinear.addView(tt);

                                      //for validation of radiobutton
                                      TextView tv = new TextView(DSRDetailActivityNew.this);
                                      radiogroup_textlist.add(tv);

                                      //postdata.put(typeof+","+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j),"");
                                      //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                      alltypes.add(typeof);
                                      allnames.add(drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));
                                      validation_list.add(drop_down_data_array.getJSONObject(e).getJSONObject("0").getJSONArray("value").getJSONArray(j).getBoolean(1));

                                  }
                              }
                          }
                      }
                      for ( k=0;k<drop_down_data_array.length();k++){
                          switch (k){
                              case 0:
                                  if (allButton.size()>=1) {
                                      try {
                                          allButton.get(0).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              final AlertDialog.Builder builder = new AlertDialog.Builder(DSRDetailActivityNew.this);
                                              builder.setCancelable(true);
                                                 builder.setSingleChoiceItems(title,0, new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          dialog.dismiss();
                                                          String typeof = null;
                                                          try {
                                                              typeof = drop_down_data_array.getJSONObject(which).getString("type");
                                                              if(typeof.equalsIgnoreCase("edit_text"))
                                                              {
                                                                  num_edittext = drop_down_data_array.getJSONObject(which).getInt("number");
                                                                  for (int j = 0; j < drop_down_data_array.getJSONObject(which).getInt("number"); j++) {

                                                                      //ss.add(jsonarray.getJSONObject(j).getJSONObject("0").getJSONArray("value").getString(j));
                                                                      TextInputLayout til = new TextInputLayout(DSRDetailActivityNew.this);
                                                                      AppCompatEditText editText = new AppCompatEditText(DSRDetailActivityNew.this);
                                                                      editText.setHint(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));

                                                                      //Log.e("salestrascker","new size "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                                                      editText.setTextSize(15);
                                                                      til.addView(editText);
                                                                      dsrdetaillinear.addView(til);

                                                                      alledittext.add(editText);

                                                                      if(!drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2).equalsIgnoreCase("no_data"))
                                                                      {
                                                                          editText.setText(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2));
                                                                      }

                                                                      if(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).equalsIgnoreCase("Number"))
                                                                      {
                                                                          editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                                                      }


                                                                      if(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(number_valid)
                                                                      ||drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(value_valid))
                                                                      {
                                                                          editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                                                          editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                                                                      }
                                                                      else
                                                                      {
                                                                          editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                                                      }

                                                                      TextView tt = new TextView(DSRDetailActivityNew.this);
                                                                      dsrdetaillinear.addView(tt);

                                                                      //for validation of radiobutton
                                                                      TextView tv = new TextView(DSRDetailActivityNew.this);
                                                                      radiogroup_textlist.add(tv);


                                                                      //postdata.put(typeof+","+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j),"");
                                                                      //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                                                      alltypes.add(typeof);
                                                                      allnames.add(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));
                                                                      validation_list.add(drop_down_data_array.getJSONObject(which).getJSONObject("0").getJSONArray("value").getJSONArray(j).getBoolean(1));
                                                                  }
                                                              }
                                                          } catch (JSONException e) {
                                                              e.printStackTrace();
                                                          }
                                                      }
                                                  });
                                              builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      dialog.dismiss();
                                                  }
                                              });
                                              builder.show();
                                          }
                                      });
                                      }catch (Exception e){
                                      }
                                  }
                                  case 1:
                                  if (allButton.size()>=2) {
                                      allButton.get(1).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                                  case 2:
                                  if (allButton.size()>=3) {
                                      allButton.get(2).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                                  case 3:
                                  if (allButton.size()>=4) {
                                      allButton.get(3).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                                  case 4:
                                  if (allButton.size()>=5) {
                                      allButton.get(4).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                                  case 5:
                                  if (allButton.size()>=6) {
                                      allButton.get(5).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                                  case 6:
                                  if (allButton.size()>=7) {
                                      allButton.get(6).setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(DSRDetailActivityNew.this, "second", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                  }
                          }

                      }
                  }
              }
              mainlinearlayout.setVisibility(View.VISIBLE);
          } catch (JSONException e) {
              Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
              e.printStackTrace();
          }
      }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("dynamic.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private void dialogDatePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();

                        Button select_date =  findViewById(R.id.select_date);
                        //((Button) findViewById(R.id.select_date)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                        select_date.setText(Tools.getFormattedDateSimple(date_ship_millis));
                        datetime_value = ((Button) findViewById(R.id.select_date)).getText().toString();
                        dialogTimePickerLight(date_ship_millis);
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    private void dialogTimePickerLight(long date_ship_millis) {
        boolean isSelectedToday= false;
         Date c = Calendar.getInstance().getTime();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
            String todayDate = formatter.format(c);
            Log.e("Today", ""+todayDate);
            String selectedDate = Tools.getFormattedDateSimple(date_ship_millis);
            Log.e("selected Today", ""+selectedDate);

            if(todayDate.equalsIgnoreCase(selectedDate)){
                isSelectedToday = true;
            }
        }

        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog datePicker = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                ((Button) findViewById(R.id.select_date)).setText(((Button) findViewById(R.id.select_date)).getText().toString()+" / "+hourOfDay + ":" + minute + ":"+second);
                ((Button) findViewById(R.id.select_date)).setTextColor(getResources().getColor(R.color.colorBlack));
                datetime_value = ((Button) findViewById(R.id.select_date)).getText().toString();
                datetime.setError(null);
                Log.e(TAG,"selected date/time is "+datetime_value);

            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        //set dark light
        datePicker.setThemeDark(false);
        if(isSelectedToday) {
            datePicker.is24HourMode();
            datePicker.setMinTime(cur_calender.get(Calendar.HOUR_OF_DAY),cur_calender.get(Calendar.MINUTE),00);
        }
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    public void getFormFormat() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest= null;
        try {
            stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php?status="+URLEncoder.encode(obj.getProject_name_form(), "UTF-8")+"&agent_id="+ShareprefreancesUtility.getInstance().getString("userId")+"&ccid="+obj.getCcid(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();
                    submit.setEnabled(true);
                    try {

                        Log.e(TAG,"form dataq is "+response.toString());

                        JSONArray jsonarray = new JSONArray(response);
                        jsonarrayfulldata = new JSONArray(response);
                        for(int i = 0;i<jsonarray.length();i++)
                        {
                            String typeof = jsonarray.getJSONObject(i).getString("type");
                            if(typeof.equalsIgnoreCase("edit_text"))
                            {
                                num_edittext = jsonarray.getJSONObject(i).getInt("number");
                                for (int j = 0; j < jsonarray.getJSONObject(i).getInt("number"); j++) {

                                   //ss.add(jsonarray.getJSONObject(j).getJSONObject("0").getJSONArray("value").getString(j));
                                   TextInputLayout til = new TextInputLayout(DSRDetailActivityNew.this);
                                   AppCompatEditText editText = new AppCompatEditText(DSRDetailActivityNew.this);
                                   editText.setHint(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));

                                   //Log.e("salestrascker","new size "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                   editText.setTextSize(15);
                                   til.addView(editText);
                                   dsrdetaillinear.addView(til);
                                   alledittext.add(editText);

                                   if(!jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2).equalsIgnoreCase("no_data"))
                                   {
                                       editText.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(2));
                                   }

                                   /* if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).equalsIgnoreCase("Number"))
                                    {
                                        editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                    }*/

                                   if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(number_valid)
                                   ||jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0).toUpperCase().contains(value_valid))
                                   {
                                       editText.setInputType(InputType.TYPE_CLASS_NUMBER );
                                       editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                                   } else {
                                       editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                                   }

                                   TextView tt = new TextView(DSRDetailActivityNew.this);
                                   dsrdetaillinear.addView(tt);

                                   //for validation of radiobutton
                                   TextView tv = new TextView(DSRDetailActivityNew.this);
                                   radiogroup_textlist.add(tv);

                                   //postdata.put(typeof+","+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j),"");
                                    //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getString(j));
                                   alltypes.add(typeof);
                                   allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getString(0));
                                   validation_list.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(j).getBoolean(1));
                               }

                           } else if(typeof.equalsIgnoreCase("spinner")) {

                               List<RadioGroup> radio = new ArrayList<RadioGroup>();

                               num_radiogroup = jsonarray.getJSONObject(i).getInt("number");

                               for(int k = 0; k < jsonarray.getJSONObject(i).getInt("number"); k++)
                               {
                                  /* TextView textview = new TextView(DSRDetailActivityNew.this);
                                   textview.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                                   textview.setTextSize(18);
                                   dsrdetaillinear.addView(textview);
                                   allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                                   radiogroup_textlist.add(textview);*/

                                  // RadioGroup ll = new RadioGroup(DSRDetailActivityNew.this);
                                   //radio.add(ll);
                                  // ll.setOrientation(LinearLayout.VERTICAL);

                                   Log.e(TAG,"value is "+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getInt(1));

                                 /*  ArrayList<String> spinnerArray = new ArrayList<String>();

                                   for (int l = 4; l < jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getInt(1)+4; l++) {

                                       spinnerArray.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(l));
                                       *//* RadioButton rdbtn = new RadioButton(DSRDetailActivityNew.this);
                                       rdbtn.setId(View.generateViewId());
                                       rdbtn.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(l) );
                                       ll.addView(rdbtn);

                                       if(rdbtn.getText().toString().equalsIgnoreCase(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(3)))
                                       {
                                           rdbtn.setChecked(true);
                                       }*//*

                                     *//*  if (jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(l).equalsIgnoreCase(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(3))) {
                                           pos = i;
                                       }*//*

                                       Log.e("cat 1", "" + jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(l));
                                       Log.e("cat 2", "" + jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(3));
                                       Log.e("cat pos", "" + pos);
                                   }*/

                                    selectedCat = jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(3);
                                    Log.e("selectedCat", ""+selectedCat);
                                   // Bind Category with Spinner
                                   // Spinner category_spinner = new Spinner(DSRDetailActivityNew.this);
                                  /* ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(DSRDetailActivityNew.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                                   category_spinner.setAdapter(spinnerArrayAdapter);
                                   dsrdetaillinear.addView(category_spinner);

                                   for(int ss=0;ss<spinnerArray.size();ss++){

                                       if(selectedCat.equalsIgnoreCase(spinnerArray.get(ss))){
                                           pos = ss;
                                           category_spinner.setSelection(ss);
                                           break;
                                       }
                                   }
                                   category_spinner.setSelection(pos);*/

                                   alltypes.add(typeof);
                                   //allSpinnergroup.add(category_spinner);

                                   //alltypes.add(typeof+"^"+jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getString(0));
                                  // alltypes.add(typeof);
                                  // allradiogroup.add(ll);

                                   if(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(k).getBoolean(2) == true)
                                   {
                                       validation_list.add(true);
                                   } else
                                   {
                                       validation_list.add(false);
                                   }

                                   TextView tt = new TextView(DSRDetailActivityNew.this);
                                   dsrdetaillinear.addView(tt);
                               }

                           }
                           else if(typeof.equalsIgnoreCase("date")){

                            num_date = jsonarray.getJSONObject(i).getInt("number");

                            datetime.setVisibility(View.VISIBLE);
                            datetime.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(0));
                            datetime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogDatePickerLight();
                                }
                            });

                               if(!jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2).equalsIgnoreCase("no_data"))
                               {
                                   datetime.setText(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2));
                                   datetime_value = jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(2);
                               }

                               TextView tv = new TextView(DSRDetailActivityNew.this);
                               radiogroup_textlist.add(tv);

                               alltypes.add(typeof);
                               allnames.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getString(0));
                               validation_list.add(jsonarray.getJSONObject(i).getJSONObject("0").getJSONArray("value").getJSONArray(0).getBoolean(1));

                           }
                           else if(typeof.equalsIgnoreCase("drop_down")){

                           }

                        }

                        mainlinearlayout.setVisibility(View.VISIBLE);

                      // Log.e("salestracker","value is "+jsonObject.getJSONObject("1").getJSONObject("0").getJSONArray("value"));

                    } catch (JSONException e) {

                        final android.app.AlertDialog.Builder alertDialog1 = new android.app.AlertDialog.Builder(DSRDetailActivityNew.this);
                        alertDialog1.setMessage(" There was some error in json "+e.toString());
                        alertDialog1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startActivity(new Intent(DSRDetailActivityNew.this,HomeActivity.class));
                                finish();
                            }
                        });
                        alertDialog1.show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DSRDetailActivityNew.this);
                    alertDialog.setMessage(" Please check your internet connection ");
                    alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            startActivity(new Intent(DSRDetailActivityNew.this,HomeActivity.class));
                            finish();
                        }
                    });
                    alertDialog.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

            Log.e(TAG,e.toString());
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(DSRDetailActivityNew.this).addToRequestQueue(stringRequest);
    }
    private void validation() {
        try {
            boolean cancel = false;
            Log.e(TAG, "alltypes " + alltypes);
            Log.e(TAG, "validatoion list " + validation_list);
            Log.e(TAG, "all names " + allnames);
            Log.e(TAG, "all radiogroup " + allradiogroup);
            Log.e(TAG, "all textview " + radiogroup_textlist.size());
            for (int i = 0; i < alltypes.size(); i++) {
                Log.e(TAG, "alltypes name " + alltypes.get(i));

                if (alltypes.get(i).equalsIgnoreCase("edit_text")) {
                    if (validation_list.get(i) == false) {
                        postdata.put(allnames.get(i), alledittext.get(i).getText().toString());
                    } else {
                        //Log.e("salestracker","value of edittext is "+alledittext.get(i).getText().toString());
                        if (alledittext.get(i).getText().toString().trim().length() == 0) {
                            alledittext.get(i).setError("Please enter " + allnames.get(i));
                            check_validation.add(true);
                            String returned_value = (String) postdata.remove(allnames.get(i));
                        } else {
                            postdata.put(allnames.get(i), alledittext.get(i).getText().toString());
                            check_validation.add(false);
                        }
                    }
                } else if (alltypes.get(i).equalsIgnoreCase("radio_group")) {
                    //for (int j = 0; j < allradiogroup.size(); j++) {}
                    if (validation_list.get(i) == false) {
                        int selectedid = allradiogroup.get(radiogroup_no).getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) findViewById(selectedid);
                        if (selectedid == -1) {
                            postdata.put(allnames.get(i), "");
                        } else {
                            postdata.put(allnames.get(i), radioButton.getText().toString());
                        }
                        radiogroup_no++;
                        /*if(radiogroup_no < allradiogroup.size()) {
                        radiogroup_no++;                   }
                        else{
                          radiogroup_no = 0;
                        }*/
                    } else {
                        int selectedid = allradiogroup.get(radiogroup_no).getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) findViewById(selectedid);
                        Log.e(TAG, "radiogroup no " + radiogroup_no);
                        Log.e(TAG, "selected id " + selectedid);
                        if (selectedid == -1) {
                            //postdata.put(alltypes.get(i) + "^" + allnames.get(i), "");
                            radiogroup_textlist.get(i).setError("Please select " + radiogroup_textlist.get(i).getText().toString());
                            check_validation.add(true);
                            Log.e("salestracker", "i am here " + selectedid);
                        } else {
                            postdata.put(allnames.get(i), radioButton.getText().toString());
                            radiogroup_textlist.get(i).setError(null);
                            check_validation.add(false);
                        }
                        radiogroup_no++;
                    }
                } else if (alltypes.get(i).equalsIgnoreCase("date")) {
                    if (validation_list.get(i) == false) {
                        postdata.put(allnames.get(i), datetime_value);
                    } else {
                        if (datetime_value.trim().length() == 0) {
                            datetime.setError("Please select " + allnames.get(i));
                            check_validation.add(true);
                        } else {
                            postdata.put(allnames.get(i), datetime_value);
                            datetime.setError(null);
                            check_validation.add(false);
                        }
                    }
                }/*else if(alltypes.get(i).equalsIgnoreCase("spinner")){
                    if (validation_list.get(i) == true) {
                        postdata.put(allnames.get(i), datetime_value);
                    } else {
                        if (datetime_value.trim().length() == 0) {
                            datetime.setError("Please select " + allnames.get(i));
                            check_validation.add(true);
                        } else {
                            postdata.put(allnames.get(i), datetime_value);
                            datetime.setError(null);
                            check_validation.add(false);
                        }
                    }

                }*/
            }
            Log.e(TAG, "value of postdata is " + postdata);
            Log.e(TAG, "value of check_valid is " + check_validation);
            radiogroup_no = 0;
            for (int i = 0; i < check_validation.size(); i++) {
                if (check_validation.get(i) == true) {
                    cancel = true;
                }
            }
            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                check_validation.clear();
            } else {
                Log.e(TAG, "please checkk new");
                check_validation.clear();
                if (InternetConnection.checkInternetConnectivity()) {

                    postdata.put("ccid", obj.getCcid());
                    postdata.put("no_of_fields", "" + alltypes.size());
                    postdata.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                    postdata.put("dsr_status", "complete");
                    postdata.put("visited_type", "client");
                    postdata.put("project_name",obj.getProject_name_form());
                    postdata.put("Category",""+selectedCat);

                    sendDSRDetailNew();
                } else {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
                    alertDialog.setMessage("Please check your internet connection.");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error is "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private void initObj() {
        Bundle bundle= getIntent().getExtras();
        assert bundle != null;
        obj= (People) bundle.getSerializable("MyClass");
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DSRDetailActivityNew.this,HomeActivity.class));
                finish();
            }
        });
        toolbar.setTitle("Report");
    }
    private void sendDSRDetailNew() {
        submit.setEnabled(false);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest= null;
        try {
            stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.host_name)+"/sales_tracker/new_update_dscr_dis.php"
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();
                    submit.setEnabled(true);

                    Log.e(TAG,"response is "+response.toString());

                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("update successful Dsr ")){
                            Toast.makeText(DSRDetailActivityNew.this, jsonObject.getString("status"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(DSRDetailActivityNew.this,HomeActivity.class));
                            finish();
                        }
                        else {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DSRDetailActivityNew.this);
                            alertDialog.setMessage(jsonObject.getString("status"));
                            alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();

                        }
                    } catch (JSONException e) {
                        Toast.makeText(DSRDetailActivityNew.this, e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    submit.setEnabled(true);
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DSRDetailActivityNew.this);
                    alertDialog.setMessage(error.toString());
                    alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.putAll(postdata);
                    Log.e(TAG,"params value is "+params);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
        } catch (Exception e) {
            Log.e(TAG,"exception is "+e.toString());
        }
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(DSRDetailActivityNew.this).addToRequestQueue(stringRequest);
    }

    private void setValues() {

        if(obj!=null){
            name.setText(obj.getClient_name());
            date.setText(getDate(obj.getCheckin_date()));
        }
       /* if (dsrDetail!=null) {
            contact_person_name.setText(dsrDetail.getContact_person());
            mobile_no.setText(dsrDetail.getPhone());
            email.setText(dsrDetail.getEmail());
            address.setText(dsrDetail.getAddress());
            Designation.setText(dsrDetail.getDesignation());
            Description.setText(dsrDetail.getDescription());
            Log.d("projct", dsrDetail.getProject_name());
            if (dsrDetail.getProject_name().equalsIgnoreCase("")){
                ProjectName.setText("Select Project");
            }
            else {
                ProjectName.setText(dsrDetail.getProject_name());
                ProjectName.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }*/

    }

    private String getDate(String date){
        StringTokenizer st = new StringTokenizer(date, "-");
        String Year=st.nextToken();
        String month=st.nextToken();
        String Date=st.nextToken();

        return Date+"/"+month+"/"+Year;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(DSRDetailActivityNew.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
    }

}

