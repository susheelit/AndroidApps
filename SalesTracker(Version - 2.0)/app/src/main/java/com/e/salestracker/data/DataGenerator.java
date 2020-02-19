package com.e.salestracker.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Modal.Social;
import com.e.salestracker.R;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ResourceType")
public class DataGenerator {

    private static Random r = new Random();
   private static Context context;

    /**
     * Generate dummy data social
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<Social> getSocialData(final Context ctx) {
        DataGenerator.context=ctx;
        final List<Social> items = new ArrayList<>();
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://aobindia.in/sales_tracker/team_leader_emp_location.php?team_leader_email=" + ShareprefreancesUtility.getInstance().getString("email"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("ok")){
                        JSONArray jsonArray=jsonObject.getJSONArray("Data");
                        for (int i=0;i<jsonArray.length();i++){
                            Social obj = new Social();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                          //  Social obj = new Social();
                            obj.name = jsonObject1.getString("location");
                            items.add(obj);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        Collections.shuffle(items);
        return items;
    }

    /**
     * Generate dummy data social
     * @param ctx android context
     * @return list of object
     */
    public static List<Social> getPersonData(Context ctx) {
        List<Social> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.persion_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.person_names);

        for (int i = 0; i < drw_arr.length(); i++) {
            Social obj = new Social();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

}
