package com.example.lubwama.mulimimobileapplication;


/**
 * Created by inn50cent on 6/1/2017.
 */

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

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
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.StatusLine;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;

public class ShowAllProducts extends Activity {
    static final String FARMER_PHONE_NUMBER = "FARMER_PHONE_NUMBER";
    static final String FARMER_EMAIL_ADDRESS = "FARMER_EMAIL_ADDRESS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_products);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // spinner1
        final Spinner spin = (Spinner)findViewById(R.id.spinner1);



        String url = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/getAllProductCategories.php";

        try {

            JSONArray data = new JSONArray(getJSONUrl(url));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("category", c.getString("category"));
                MyArrList.add(map);

            }


            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(ShowAllProducts.this, MyArrList, R.layout.activity_column1,
                    new String[] {"category"}, new int[] {R.id.ColName});
            spin.setAdapter(sAdap);

            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View selectedItemView,
                                           int position, long id) {
                    //this is my cproduct category
                    String product_category = MyArrList.get(position).get("category")
                            .toString();

                    //call function to retrieve products based on the category passed as  a parameter
                    show_products_in_this_category(product_category);


                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(ShowAllProducts.this,
                            "Your Selected : Nothing",
                            Toast.LENGTH_SHORT).show();
                }


            });



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


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
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    ///////////////////////////////////////////////////////////////////////////

    void show_products_in_this_category(String product_category){
        String myProductCategory =  product_category;

        // listView1
        final ListView lstView1 = (ListView) findViewById(R.id.listView1);

        String url2 = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/show_all_products.php?myProductCategory="+myProductCategory;
        try {
            JSONArray data = new JSONArray(getJSONUrl2(url2));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("product_id", c.getString("product_id"));
                map.put("product_category", c.getString("product_category"));
                map.put("product_name", c.getString("product_name"));
                map.put("product_description", c.getString("product_description"));
                map.put("quantity", c.getString("quantity"));
                map.put("unit_price", c.getString("unit_price"));
                map.put("image", c.getString("image"));
                map.put("phone_number", c.getString("phone_number"));
                map.put("location", c.getString("location"));
                map.put("email_address", c.getString("email_address"));
                MyArrList.add(map);
            }


            lstView1.setAdapter(new ImageAdapter(this, MyArrList));
            final AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

            // OnClick
            lstView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    View layout = inflater.inflate(R.layout.custom_fullimage_dialog1,
                            (ViewGroup) findViewById(R.id.layout_root));
                    ImageView image = (ImageView) layout.findViewById(R.id.fullimage);


                    try {
                        image.setImageBitmap(loadBitmap(MyArrList.get(position).get("image")));
                    } catch (Exception e) {
                        // When Error
                        image.setImageResource(android.R.drawable.ic_menu_report_image);
                    }

                    imageDialog.setIcon(android.R.drawable.btn_star_big_on);
                    imageDialog.setTitle("Product : " + MyArrList.get(position).get("product_name"));
                    imageDialog.setView(layout);

                    final String my_product_id = MyArrList.get(position).get("product_id");
                    final String my_product_category = MyArrList.get(position).get("product_category");
                    final String my_product = MyArrList.get(position).get("product_name");
                    final String my_unit_price = MyArrList.get(position).get("unit_price");
                    final String my_quantity = MyArrList.get(position).get("quantity");
                    final String my_description = MyArrList.get(position).get("product_description");
                    final String farmer_location = MyArrList.get(position).get("location");
                    final String phone_number = MyArrList.get(position).get("phone_number");
                    final String email_address= MyArrList.get(position).get("email_address");

                    imageDialog.setPositiveButton("SMS Farmer", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.dismiss();
                            Intent intent = new Intent(ShowAllProducts.this,SendSms.class);
                            intent.putExtra(FARMER_PHONE_NUMBER,phone_number);
                            finish();
                            startActivity(intent);
                        }

                    });



                    imageDialog.setNegativeButton("Email Farmer", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) { //dialog.dismiss();

                            Intent intent = new Intent(ShowAllProducts.this,SendEmail.class);
                            intent.putExtra(FARMER_EMAIL_ADDRESS,email_address);
                            finish();
                            startActivity(intent);



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

    }

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
                convertView = inflater.inflate(R.layout.activity_column2, null);
            }

            // ColImage
            ImageView imageView = (ImageView) convertView.findViewById(R.id.ColImgPath);
            imageView.getLayoutParams().height = 130;
            imageView.getLayoutParams().width = 135;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            try {
                imageView.setImageBitmap(loadBitmap(MyArr.get(position).get("image")));
            } catch (Exception e) {
                // When Error
                imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            }

            // ColPosition
            TextView myproduct = (TextView) convertView.findViewById(R.id.farmerName);
            myproduct.setPadding(2, 0, 0, 0);
            myproduct.setText( MyArr.get(position).get("product_name"));

            TextView unit_price = (TextView) convertView.findViewById(R.id.techniqueDescription);
            unit_price.setPadding(2, 0, 0, 0);
            unit_price.setText("Unit price: " + MyArr.get(position).get("unit_price"));

            TextView quantity = (TextView) convertView.findViewById(R.id.date);
            quantity .setPadding(2, 0, 0, 0);
            quantity .setText("Quantity: " + MyArr.get(position).get("quantity"));

            TextView product_desciption = (TextView) convertView.findViewById(R.id.ProductDescription);
            product_desciption .setPadding(2, 0, 0, 0);
            product_desciption.setText("My description: " + MyArr.get(position).get("product_description"));

            TextView telephone = (TextView) convertView.findViewById(R.id.telephone_contact);
            telephone .setPadding(2, 0, 0, 0);
            telephone.setText("Phone number: " + MyArr.get(position).get("phone_number"));

            TextView location = (TextView) convertView.findViewById(R.id.farmer_location);
            location .setPadding(2, 0, 0, 0);
            location.setText("Location: " + MyArr.get(position).get("location"));

            return convertView;

        }

    }


    public String getJSONUrl2(String url2) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url2);
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

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
