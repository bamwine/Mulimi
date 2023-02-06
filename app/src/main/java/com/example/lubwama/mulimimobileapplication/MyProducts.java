package com.example.lubwama.mulimimobileapplication;
/**
 * Created by inn50cent on 5/26/2017.
 */

        import java.io.BufferedInputStream;
        import java.io.BufferedOutputStream;
        import java.io.BufferedReader;
        import java.io.ByteArrayOutputStream;
        import java.io.Closeable;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import org.apache.http.HttpEntity;
        import org.apache.http.client.HttpClient;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import org.apache.http.HttpResponse;

        import org.apache.http.StatusLine;

        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.methods.HttpGet;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.StrictMode;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.widget.Toast;

public class MyProducts extends Activity {

    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String CROP_CATEGORY = "product_category";
    static String CROP_PRODUCT = "product_name";
    static String QUANTITY = "quantity";
    static String UNIT_PRICE = "unit_price";
    static String IMAGE= "image";
    static String PRODUCT_ID = "product_id";
    static String PRODUCT_DESCRIPTION = "product_description";;

    String myemail;
    static final int READ_BLOCK_SIZE = 100;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        // Execute DownloadJSON AsyncTask
        new DownloadJSON().execute();




    }

    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MyProducts.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Loading your crop products ");
            // Set progressdialog message
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address



               // myemail = my_email.toString();

try{
                FileInputStream myEmail=openFileInput("my_email.txt");
                InputStreamReader InputReadEmail= new InputStreamReader(myEmail);

                char[] inputEmailBuffer= new char[READ_BLOCK_SIZE];
                String my_email="";
                int charReadEmail;

                while ((charReadEmail=InputReadEmail.read(inputEmailBuffer))>0) {
                    // char to string conversion
                    String readEmailString=String.copyValueOf(inputEmailBuffer,0,charReadEmail);
                    my_email +=readEmailString;
                }
                InputReadEmail.close();

                //read my names
                FileInputStream myNames=openFileInput("my_full_names.txt");
                InputStreamReader InputReadName= new InputStreamReader(myNames);

                char[] inputNameBuffer= new char[READ_BLOCK_SIZE];
                String my_full_names="";
                int charReadName;

                while ((charReadName=InputReadName.read(inputNameBuffer))>0) {
                    // char to string conversion
                    String readNameString=String.copyValueOf(inputNameBuffer,0,charReadName);
                    my_full_names +=readNameString;
                }
                InputReadName.close();


                //read my location
                FileInputStream myLocation=openFileInput("my_location.txt");
                InputStreamReader InputReadLocation= new InputStreamReader(myLocation);

                char[] inputLocationBuffer= new char[READ_BLOCK_SIZE];
                String my_location="";
                int charReadLocation;

                while ((charReadLocation=InputReadLocation.read(inputLocationBuffer))>0) {
                    // char to string conversion
                    String readLocationString=String.copyValueOf(inputLocationBuffer,0,charReadLocation);
                    my_location +=readLocationString;
                }
                InputReadLocation.close();


                //read my phone
                FileInputStream myPhone=openFileInput("my_phone_number.txt");
                InputStreamReader InputReadPhone= new InputStreamReader(myPhone);

                char[] inputPhoneBuffer= new char[READ_BLOCK_SIZE];
                String my_phone_number="";
                int charReadPhone;

                while ((charReadPhone=InputReadPhone.read(inputPhoneBuffer))>0) {
                    // char to string conversion
                    String readPhoneString=String.copyValueOf(inputPhoneBuffer,0,charReadPhone);
                    my_phone_number +=readPhoneString;
                }
                InputReadPhone.close();


            jsonobject = JSONfunctions
                    .getJSONfromURL("http://epipetalous-carrier.000webhostapp.com/mulimi_application/myProducts.php?myemail="+my_email);

} catch (Exception e) {
    e.printStackTrace();
}

                try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("result");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("product_category", jsonobject.getString("product_category"));
                    map.put("product_name", jsonobject.getString("product_name"));
                    map.put("quantity", jsonobject.getString("quantity"));
                    map.put("unit_price", jsonobject.getString("unit_price"));
                    map.put("image", jsonobject.getString("image"));
                    map.put("product_description", jsonobject.getString("product_description"));
                    map.put("product_id", jsonobject.getString("product_id"));
                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MyProducts.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

}


