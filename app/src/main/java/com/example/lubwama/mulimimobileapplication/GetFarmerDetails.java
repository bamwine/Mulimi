package com.example.lubwama.mulimimobileapplication;

        import android.R;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.Toast;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.OutputStreamWriter;


public class GetFarmerDetails extends Activity {
    public static final String MY_FULL_NAMES = "MY_FULL_NAMES";
    public static final String MY_LOCATION = "MY_LOCATION";
    public static final String MY_PHONE_NUMBER = "MY_PHONE_NUMBER";
    public static final String MY_EMAIL = "MY_EMAIL";
    public static final String MY_PROFILE_PIC = "MY_PROFILE_PIC";
    public static final String MY_STRING = "MY_STRING";
    private ProgressDialog loading;
    String saved_email_or_phone, myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.lubwama.mulimimobileapplication.R.layout.get_farmer_details);
        Intent intent = getIntent();
        saved_email_or_phone = intent.getStringExtra(Login.EMAIL_OR_PHONE);
        myString = intent.getStringExtra(Login.MY_STRING);


        getData();


        try{
            FileOutputStream fOutProductImage = openFileOutput("my_product_image.txt", MODE_PRIVATE);
            OutputStreamWriter outputProductImageWriter=new OutputStreamWriter(fOutProductImage);
            outputProductImageWriter.write("");
            outputProductImageWriter.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getData() {
        String myemail_or_phone = saved_email_or_phone;

        loading = ProgressDialog.show(this, "Please wait...", "Redirecting you to your page", false, false);

        String url = Config.DATA_URL + myemail_or_phone.toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GetFarmerDetails.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String full_names = "";
        String email_address = "";
        String location = "";
        String phone_number = "";
        String profile_pic = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            full_names = collegeData.getString(Config.KEY_NAME);
            email_address = collegeData.getString(Config.KEY_EMAIL);
            location = collegeData.getString(Config.KEY_LOCATION);
            phone_number = collegeData.getString(Config.KEY_PHONE_NUMBER);
            profile_pic = collegeData.getString(Config.KEY_PROFILE_PIC);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String my_full_names = full_names;
        String my_email = email_address;
        String my_location = location;
        String my_phone_number = phone_number;
        String my_profile_pic = profile_pic;

        //now write these details to text files
        try {


            FileOutputStream fOutNames = openFileOutput("my_full_names.txt", MODE_PRIVATE);
            OutputStreamWriter outputNameWriter=new OutputStreamWriter(fOutNames);
            outputNameWriter.write(my_full_names);
            outputNameWriter.close();


            FileOutputStream fOutEmail = openFileOutput("my_email.txt", MODE_PRIVATE);
            OutputStreamWriter outputEmailWriter=new OutputStreamWriter(fOutEmail);
            outputEmailWriter.write(my_email);
            outputEmailWriter.close();

            FileOutputStream fOutLocation = openFileOutput("my_location.txt", MODE_PRIVATE);
            OutputStreamWriter outputLocationWriter=new OutputStreamWriter(fOutLocation);
            outputLocationWriter.write(my_location);
            outputLocationWriter.close();

            FileOutputStream fOutPhone = openFileOutput("my_phone_number.txt", MODE_PRIVATE);
            OutputStreamWriter outputPhoneWriter=new OutputStreamWriter(fOutPhone);
            outputPhoneWriter.write(my_phone_number);
            outputPhoneWriter.close();



            } catch (Exception e) {
                e.printStackTrace();
            }



        if (myString.equals("AdvertiseProduct")) {


            Intent intent = new Intent(GetFarmerDetails.this, FarmersPage.class);
            startActivity(intent);
        } else if (myString.equals("ShareTechnique")) {

            Intent intent = new Intent(GetFarmerDetails.this, ShareTechnique.class);
            startActivity(intent);


        }



    }

}

