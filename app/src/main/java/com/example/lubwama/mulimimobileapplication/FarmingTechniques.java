package com.example.lubwama.mulimimobileapplication;


/**
 * Created by inn50cent on 6/5/2017.
 */

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.StatusLine;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedInputStream;
        import java.io.BufferedOutputStream;
        import java.io.BufferedReader;
        import java.io.ByteArrayOutputStream;
        import java.io.Closeable;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;


/**
 * Created by inn50cent on 5/26/2017.
 */


public class FarmingTechniques extends Activity {
    String my_email,my_full_names,my_location,my_phone;
    private EditText comment;
    private ImageView commentButton;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farming_techniques);
        //comment = (EditText) findViewById(R.id.comment);

        //comment_button();



        Intent intent = getIntent();
        my_email = intent.getStringExtra(GetFarmerDetails.MY_EMAIL);
        my_full_names = intent.getStringExtra(GetFarmerDetails.MY_FULL_NAMES);
        my_location = intent.getStringExtra(GetFarmerDetails.MY_LOCATION);
        my_phone = intent.getStringExtra(GetFarmerDetails.MY_PHONE_NUMBER);



        // listView1
        final ListView lstView1 = (ListView) findViewById(R.id.listView1);

        String url = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/farmingTechniques.php";
        try {
            JSONArray data = new JSONArray(getJSONUrl(url));

            final ArrayList<HashMap
                    <String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();

                map.put("technique_image", c.getString("technique_image"));
                map.put("description", c.getString("description"));
                map.put("farmer_image", c.getString("farmer_image"));
                map.put("farmer_names", c.getString("farmer_names"));
                map.put("date", c.getString("date"));
                map.put("location", c.getString("location"));

                MyArrList.add(map);
            }


            lstView1.setAdapter(new ImageAdapter(this, MyArrList));
            final AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);





            // OnClick
            lstView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                            (ViewGroup) findViewById(R.id.layout_root));
                    ImageView image = (ImageView) layout.findViewById(R.id.fullimage);


                    try {
                        image.setImageBitmap(loadBitmap(MyArrList.get(position).get("farmer_image")));
                    } catch (Exception e) {
                        // When Error
                        image.setImageResource(android.R.drawable.ic_menu_report_image);
                    }

                    imageDialog.setIcon(android.R.drawable.btn_star_big_on);
                    imageDialog.setTitle("By: " + MyArrList.get(position).get("farmer_names"));
                    imageDialog.setView(layout);


                    imageDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }

                    });



                    imageDialog.create();
                    imageDialog.show();

                }
            });



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }//end onCreate method

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) {
            // TODO Auto-generated method stub
            context = c;
            MyArr = list;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArr.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_column3, null);
            }

            // ColImage
            ImageView imageView = (ImageView) convertView.findViewById(R.id.ColImgPath);
            imageView.getLayoutParams().height = 100;
            imageView.getLayoutParams().width = 100;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            try {
                imageView.setImageBitmap(loadBitmap(MyArr.get(position).get("farmer_image")));
            } catch (Exception e) {
                // When Error
                imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            }

            TextView farmer = (TextView) convertView.findViewById(R.id.farmerName);
            farmer.setPadding(2, 0, 0, 0);
            farmer.setText( MyArr.get(position).get("farmer_names"));

            TextView techniqueDescription = (TextView) convertView.findViewById(R.id.techniqueDescription);
            techniqueDescription.setPadding(2, 0, 0, 0);
            techniqueDescription.setText(MyArr.get(position).get("description"));

            TextView mydate = (TextView) convertView.findViewById(R.id.date);
            mydate.setPadding(2, 0, 0, 0);
            mydate.setText(MyArr.get(position).get("date"));

            TextView location = (TextView) convertView.findViewById(R.id.location);
            location.setPadding(2, 0, 0, 0);
            location.setText(MyArr.get(position).get("location"));

            return convertView;

        }

    }

    /***
     * Get JSON Code from URL
     ***/
    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download file..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    /*****
     * Get Image Resource from URL (Start)
     *****/
    private static final String TAG = "ERROR";
    private static final int IO_BUFFER_SIZE = 4 * 1024;

    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    String getComment = comment.getText().toString();

    void comment_button(){
        commentButton = (ImageView) findViewById(R.id.comment_buuton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getComment.equals("")){
                    comment.setError( "Provide a comment!" );
                    comment.requestFocus();
                }
                else{
                    //go ahead and post my comment i.e insert and after wards retrieve, the inserting and retrieving are done in one php file
                    postComment(my_email,getComment);
                }
            }
        });



    }


    private void postComment(String your_email, String your_comment){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FarmingTechniques.this, "Please wait", "Updating your comment...");
            }

            @Override
            protected String doInBackground(String... params) {
                String paramEmail = params[0];
                String paramComment = params[1];

                String your_comment;
                your_comment = getComment;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", my_email));
                nameValuePairs.add(new BasicNameValuePair("comment", your_comment));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://epipetalous-carrier.000webhostapp.com/mulimi_application/post_comment.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }



                return "";

            }//end doInBackground

            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(my_email);
    }



}

