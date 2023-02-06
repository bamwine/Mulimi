package com.example.lubwama.mulimimobileapplication;

        import android.R;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.provider.MediaStore;
        import android.util.Base64;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.*;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.StatusLine;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.ArrayList;
        import java.util.HashMap;

        import static java.security.AccessController.getContext;

public class FarmersPage extends Activity implements  View.OnClickListener {

    private EditText mydescription;
    private EditText myquantity;
    private EditText myunit_price;
    private ImageView profile_pic;
    String readCategoryFile, readProductFile;
    private TextView my_products;

    private ImageView my_image;
    private Button browse_image;
    private Button upload_product;
    String my_full_names, my_email, my_location, my_phone_number;
    public static final String KEY_IMAGE = "my_image";

    public static final String KEY_QUANTITY = "myquantity";
    public static final String KEY_DESCRIPTION = "mydescription";
    public static final String KEY_UNIT_PRICE = "myunit_price";
    public static final String KEY_PRODUCT_CATEGORY = "readCategoryFile";
    public static final String KEY_PRODUCT = "readProductFile";
    public static final String MY_EMAIL = "my_email";
    public static final String MY_FULL_NAMES = "my_full_names";
    public static final String MY_PHONE_NUMBER = "my_phone";
    public static final String MY_LOCATION = "my_location";

    public static final String UPLOAD_URL = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/upload.php";

    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;
    String myfullnames;
    static final int READ_BLOCK_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.lubwama.mulimimobileapplication.R.layout.farmers_page);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

            try {

                //first read my names from my_full_names.txt file
                FileInputStream myNames=openFileInput("my_full_names.txt");
                InputStreamReader InputReadName= new InputStreamReader(myNames);

                char[] inputNameBuffer= new char[READ_BLOCK_SIZE];
                String my_names="";
                int charReadName;

                while ((charReadName=InputReadName.read(inputNameBuffer))>0) {
                    // char to string conversion
                    String readNameString=String.copyValueOf(inputNameBuffer,0,charReadName);
                    my_names +=readNameString;
                }
                InputReadName.close();


        profile_pic = (ImageView) findViewById(com.example.lubwama.mulimimobileapplication.R.id.profile_pic);

        TextView textView = (TextView) findViewById(com.example.lubwama.mulimimobileapplication.R.id.login);
        textView.setText(my_names);
        myfullnames = textView.getText().toString();

            } catch (Exception e) {
                e.printStackTrace();
            }


        mydescription = (EditText) findViewById(com.example.lubwama.mulimimobileapplication.R.id.description);

        myquantity = (EditText) findViewById(com.example.lubwama.mulimimobileapplication.R.id.quantity);
        myunit_price = (EditText) findViewById(com.example.lubwama.mulimimobileapplication.R.id.unit_price);

        browse_image = (Button) findViewById(com.example.lubwama.mulimimobileapplication.R.id.browse_image);
        upload_product = (Button) findViewById(com.example.lubwama.mulimimobileapplication.R.id.update_product);
        my_image = (ImageView) findViewById(com.example.lubwama.mulimimobileapplication.R.id.my_image);


        browse_image.setOnClickListener(this);
        upload_product.setOnClickListener(this);
        //profile_pic.setOnClickListener(this);
        showmy_products();

        //call my spinner function to populate Spinner with crop products
        populateSpinner();


        //first write to browed_image.txt file
        //////////////////////////////////////////////////////////////////////////////////
        try{
        FileOutputStream fOutBrowsedImage = openFileOutput("browsed_image.txt", MODE_PRIVATE);
        OutputStreamWriter outputBrowsedImageWriter=new OutputStreamWriter(fOutBrowsedImage);
        outputBrowsedImageWriter.write("NoPhoto");
        outputBrowsedImageWriter.close();



        } catch (Exception e) {
        e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////////////////////////






}//end onCreate method

    void populateSpinner(){
        try {
            String url = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/getAllProductCategories.php";
            Spinner categorySpinnner = (Spinner)findViewById(com.example.lubwama.mulimimobileapplication.R.id.spinner1);
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
            sAdap = new SimpleAdapter(FarmersPage.this, MyArrList, com.example.lubwama.mulimimobileapplication.R.layout.crop_products,
                    new String[] {"category"}, new int[] {com.example.lubwama.mulimimobileapplication.R.id.ProductCategory});
            categorySpinnner.setAdapter(sAdap);

            categorySpinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                    Toast.makeText(FarmersPage.this,
                            "Your Selected : Nothing",
                            Toast.LENGTH_SHORT).show();
                }


            });



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    ///////////////////////////////*Populating spinners with products from database*///////////////////////////////////////////////
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


    void show_products_in_this_category(String product_category){
        final String category = product_category;
        String url2 = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/products_in_this_category.php?myProductCategory="+category;

        try {

            JSONArray data = new JSONArray(getJSONUrl2(url2));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("name", c.getString("name"));
                MyArrList.add(map);

            }


            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(FarmersPage.this, MyArrList, com.example.lubwama.mulimimobileapplication.R.layout.product_in_cat,
                    new String[] {"name"}, new int[] {com.example.lubwama.mulimimobileapplication.R.id.ProductInCategory});


            Spinner productSpinnner = (Spinner)findViewById(com.example.lubwama.mulimimobileapplication.R.id.spinner2);
            productSpinnner.setAdapter(sAdap);

            productSpinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View selectedItemView,
                                           int position, long id) {
                    //this is my cproduct category
                    String product_in_this_category = MyArrList.get(position).get("name")
                            .toString();

                    //call function to retrieve products based on the category passed as  a parameter
                   show_products(category,product_in_this_category);


                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(FarmersPage.this,
                            "Your Selected : Nothing",
                            Toast.LENGTH_SHORT).show();
                }


            });



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    void show_products(String category,String product_in_this_category){
       final  String pdt_category = category;
       final  String pdt = product_in_this_category;

        Toast.makeText(FarmersPage.this,
                "Selected "+pdt_category+ ":" +pdt,
                Toast.LENGTH_SHORT).show();
        //write product and category to txt files
        try {


            FileOutputStream fOutCategory = openFileOutput("mycategory.txt", MODE_PRIVATE);
            OutputStreamWriter outputCategoryWriter=new OutputStreamWriter(fOutCategory);
            outputCategoryWriter.write(pdt_category);
            outputCategoryWriter.close();

            FileOutputStream fOutProduct = openFileOutput("myproduct.txt", MODE_PRIVATE);
            OutputStreamWriter outputProductWriter=new OutputStreamWriter(fOutProduct);
            outputProductWriter.write(pdt);
            outputProductWriter.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /////////////////////////////////////////////////////////////////////////////

    public void showmy_products(){
        my_products = (TextView) findViewById(com.example.lubwama.mulimimobileapplication.R.id.my_products);
        my_products.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent(FarmersPage.this, MyProducts.class);
                startActivity(intent);

            }

        });



    }


    //////////////////////////code for image capture from gallery////////////////////////////

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                my_image.setImageBitmap(bitmap);

                /*then write to file to show that my_image has been set to bitmap,,,, this file will be read when uploading
                * product details so that if no image bitmap is set, product should not be uploaded*/

                try{
                String mytext = "MyPhoto";

                    FileOutputStream fOutBrowsedPhoto = openFileOutput("browsed_image.txt", MODE_PRIVATE);
                    OutputStreamWriter outputBrowsedPhotoWriter=new OutputStreamWriter(fOutBrowsedPhoto);
                    outputBrowsedPhotoWriter.write(mytext);
                    outputBrowsedPhotoWriter.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*end this snippet for writing to file*/

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

//////////////////////////////////////////////////////////////////////////////////

    private void showFileChooserForProfilePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Pic"), PICK_IMAGE_REQUEST);
    }



    public String getStringProfileImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void uploadImage() {


        final String description = mydescription.getText().toString().trim();
        final String quantity = myquantity.getText().toString().trim();
        final String unit_price = myunit_price.getText().toString().trim();

        final String image = getStringImage(bitmap);
        final String profil_image = getStringProfileImage(bitmap);

        if (quantity.equals("") ) {
            myquantity.setError( "Product quantity required!" );
            myquantity.requestFocus();

        } else if (unit_price.equals("")) {
            myunit_price.setError( "Unit price required!" );
            myunit_price.requestFocus();

        }
        else if (description.equals("")) {
            mydescription.setError( "Provide product description!" );
            mydescription.requestFocus();

        }

        else {


            try {


                //Read my product category from mycategory.txt file
                FileInputStream myCategory=openFileInput("mycategory.txt");
                InputStreamReader InputReadmyCategory= new InputStreamReader(myCategory);

                char[] inputmyCategoryBuffer= new char[READ_BLOCK_SIZE];
                readCategoryFile="";
                int charReadmyCategory;

                while ((charReadmyCategory=InputReadmyCategory.read(inputmyCategoryBuffer))>0) {
                    // char to string conversion
                    String readmyCategoryString=String.copyValueOf(inputmyCategoryBuffer,0,charReadmyCategory);
                    readCategoryFile +=readmyCategoryString;
                }
                InputReadmyCategory.close();


                //Read my product from myproduct.txt file
                FileInputStream myProduct=openFileInput("myproduct.txt");
                InputStreamReader InputReadmyProduct= new InputStreamReader(myProduct);

                char[] inputmyProductBuffer= new char[READ_BLOCK_SIZE];
                readProductFile="";
                int charReadmyProduct;

                while ((charReadmyProduct=InputReadmyProduct.read(inputmyProductBuffer))>0) {
                    // char to string conversion
                    String readmyProductString=String.copyValueOf(inputmyProductBuffer,0,charReadmyProduct);
                    readProductFile +=readmyProductString;
                }
                InputReadmyProduct.close();




                //read from browsed_image.txt file to check if the imageView contains Bitmap from the browed image
                FileInputStream myBrowsedImage=openFileInput("browsed_image.txt");
                InputStreamReader InputReadBrowsedImage= new InputStreamReader(myBrowsedImage);

                char[] inputBrowsedImageBuffer= new char[READ_BLOCK_SIZE];
                String my_browsed_image="";
                int charReadBrowsedImage;

                while ((charReadBrowsedImage=InputReadBrowsedImage.read(inputBrowsedImageBuffer))>0) {
                    // char to string conversion
                    String readBrowsedImageString=String.copyValueOf(inputBrowsedImageBuffer,0,charReadBrowsedImage);
                    my_browsed_image +=readBrowsedImageString;
                }
                InputReadBrowsedImage.close();


                //check to see if user set my_image with browsed image Bitmap
                if (my_browsed_image.equals("MyPhoto")) {

                    //Read my email from my_email.txt file
                    FileInputStream myEmail=openFileInput("my_email.txt");
                    InputStreamReader InputReadEmail= new InputStreamReader(myEmail);

                    char[] inputEmailBuffer= new char[READ_BLOCK_SIZE];
                    my_email="";
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
                    my_full_names="";
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
                    my_location="";
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
                    my_phone_number="";
                    int charReadPhone;

                    while ((charReadPhone=InputReadPhone.read(inputPhoneBuffer))>0) {
                        // char to string conversion
                        String readPhoneString=String.copyValueOf(inputPhoneBuffer,0,charReadPhone);
                        my_phone_number +=readPhoneString;
                    }
                    InputReadPhone.close();

                    final String myProductCategory = readCategoryFile;
                    final String myProductName = readProductFile;
                    final String my_Email = my_email;
                    final String my_Phone = my_phone_number;
                    final String my_Location = my_location;
                    final String my_Names = my_full_names;

                    class UploadImage extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(FarmersPage.this, "Please wait...", "uploading", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();

                            /*if product details are submitted successfully, all fields must be reset, imageView must be set to
                 image gallery default photo */
                            mydescription.setText("");
                            myquantity.setText("");
                            myunit_price.setText("");

                            //write NoPhoto back to browsed_image.txt

                            try{
                            FileOutputStream fOutResetImage = openFileOutput("browsed_image.txt", MODE_PRIVATE);
                               OutputStreamWriter outputResetImageWriter=new OutputStreamWriter(fOutResetImage);
                               outputResetImageWriter.write("NoPhoto");
                               outputResetImageWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                            //Reset myImgeView to default View
                                my_image.setImageResource(R.drawable.gallery_thumb);
                                Toast.makeText(FarmersPage.this, s, Toast.LENGTH_LONG).show();

                    /*show alert dialog on  successful submission, when button is pressed, take me to my products*/
                        }

                        @Override
                        protected String doInBackground(Void... params) {
                            RequestHandler rh = new RequestHandler();
                            HashMap<String, String> param = new HashMap<String, String>();
                            param.put(MY_FULL_NAMES, my_Names);
                            param.put(MY_EMAIL, my_Email);
                            param.put(MY_LOCATION, my_Location);
                            param.put(MY_PHONE_NUMBER, my_Phone);
                            param.put(KEY_DESCRIPTION, description);
                            param.put(KEY_QUANTITY, quantity);
                            param.put(KEY_UNIT_PRICE, unit_price);
                            param.put(KEY_IMAGE, image);
                            param.put(KEY_PRODUCT_CATEGORY, myProductCategory);
                            param.put(KEY_PRODUCT, myProductName);
                            String result = rh.sendPostRequest(UPLOAD_URL, param);
                            return result;
                        }
                    }
                    UploadImage u = new UploadImage();
                    u.execute();


                }

                //flag user with message if they did not set product photo
               else {
                    Toast.makeText(FarmersPage.this,
                            "Please browse product photo!",
                            Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }//end else
    }



    ////////////////////////end this code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.lubwama.mulimimobileapplication.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.example.lubwama.mulimimobileapplication.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void onClick(View v) {
        // TODO Auto-generated method stub

        if(v == upload_product) {
            uploadImage();
        }
        if(v == browse_image){
            showFileChooser();
        }


        if(v == profile_pic){
            showFileChooserForProfilePic();
        }
    }

}


