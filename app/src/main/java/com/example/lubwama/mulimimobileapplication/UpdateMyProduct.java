package com.example.lubwama.mulimimobileapplication;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Base64;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.HashMap;


/**
 * Created by inn50cent on 5/29/2017.
 */

public class UpdateMyProduct extends Activity implements View.OnClickListener {
    private EditText new_quantity;
    private EditText new_unit_price;
    private EditText new_description;
    private TextView thisCategory;
    private TextView thisProduct;
    private TextView my_names;

    private ImageView my_image;
    private Button browse_image;
    private Button upload_product;

    String my_new_image,my_new_quantity,my_new_description,my_new_unit_price;

    public static final String KEY_IMAGE = "my_new_image";
    public static final String KEY_QUANTITY = "my_new_quantity";
    public static final String KEY_DESCRIPTION = "my_new_description";
    public static final String KEY_UNIT_PRICE = "my_new_unit_price";
    public static final String KEY_PRODUCT_ID = "my_product_id";


    ImageLoader imageLoader = new ImageLoader(this);

    public static final String UPDATE_URL = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/update_product.php";

    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_my_product);

        ////////////////////////////////////////////////

        try {
            //Read personal data(Email)

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

//Read my names
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


            //Read product details
            FileInputStream myProductCategory=openFileInput("my_product_category.txt");
            InputStreamReader InputReadProductCategory= new InputStreamReader(myProductCategory);

            char[] inputProductCategoryBuffer= new char[READ_BLOCK_SIZE];
            String my_product_category="";
            int charReadProductCategory;

            while ((charReadProductCategory=InputReadProductCategory.read(inputProductCategoryBuffer))>0) {
                // char to string conversion
                String readProductCategoryString=String.copyValueOf(inputProductCategoryBuffer,0,charReadProductCategory);
                my_product_category +=readProductCategoryString;
            }
            InputReadProductCategory.close();


            FileInputStream myProductName=openFileInput("my_product_name.txt");
            InputStreamReader InputReadProductName= new InputStreamReader(myProductName);

            char[] inputProductNameBuffer= new char[READ_BLOCK_SIZE];
            String my_product_name="";
            int charReadProductName;

            while ((charReadProductName=InputReadProductName.read(inputProductNameBuffer))>0) {
                // char to string conversion
                String readProductNameString=String.copyValueOf(inputProductNameBuffer,0,charReadProductName);
                my_product_name +=readProductNameString;
            }
            InputReadProductName.close();


            FileInputStream myProductId=openFileInput("my_product_id.txt");
            InputStreamReader InputReadProductId= new InputStreamReader(myProductId);

            char[] inputProductIdBuffer= new char[READ_BLOCK_SIZE];
            String my_product_id="";
            int charReadProductId;

            while ((charReadProductId=InputReadProductId.read(inputProductIdBuffer))>0) {
                // char to string conversion
                String readProductIdString=String.copyValueOf(inputProductIdBuffer,0,charReadProductId);
                my_product_id +=readProductIdString;
            }
            InputReadProductId.close();


            FileInputStream myProductDescription=openFileInput("my_product_description.txt");
            InputStreamReader InputReadProductDescription= new InputStreamReader(myProductDescription);

            char[] inputProductDescriptionBuffer= new char[READ_BLOCK_SIZE];
            String my_product_description="";
            int charReadProductDescription;

            while ((charReadProductDescription=InputReadProductDescription.read(inputProductDescriptionBuffer))>0) {
                // char to string conversion
                String readProductDescriptionString=String.copyValueOf(inputProductDescriptionBuffer,0,charReadProductDescription);
                my_product_description +=readProductDescriptionString;
            }
            InputReadProductDescription.close();


            FileInputStream myProductQuantity=openFileInput("my_product_quantity.txt");
            InputStreamReader InputReadProductQuantity= new InputStreamReader(myProductQuantity);

            char[] inputProductQuantityBuffer= new char[READ_BLOCK_SIZE];
            String my_product_quantity="";
            int charReadProductQuantity;

            while ((charReadProductQuantity=InputReadProductQuantity.read(inputProductQuantityBuffer))>0) {
                // char to string conversion
                String readProductQuantityString=String.copyValueOf(inputProductQuantityBuffer,0,charReadProductQuantity);
                my_product_quantity +=readProductQuantityString;
            }
            InputReadProductQuantity.close();


            FileInputStream myProductPrice=openFileInput("my_product_price.txt");
            InputStreamReader InputReadProductPrice= new InputStreamReader(myProductPrice);

            char[] inputProductPriceBuffer= new char[READ_BLOCK_SIZE];
            String my_product_price="";
            int charReadProductPrice;

            while ((charReadProductPrice=InputReadProductPrice.read(inputProductPriceBuffer))>0) {
                // char to string conversion
                String readProductPriceString=String.copyValueOf(inputProductPriceBuffer,0,charReadProductPrice);
                my_product_price +=readProductPriceString;
            }
            InputReadProductPrice.close();


            FileInputStream myProductImage=openFileInput("my_product_image.txt");
            InputStreamReader InputReadProductImage= new InputStreamReader(myProductImage);

            char[] inputProductImageBuffer= new char[READ_BLOCK_SIZE];
            String my_product_image="";
            int charReadProductImage;

            while ((charReadProductImage=InputReadProductImage.read(inputProductImageBuffer))>0) {
                // char to string conversion
                String readProductImageString=String.copyValueOf(inputProductImageBuffer,0,charReadProductImage);
                my_product_image +=readProductImageString;
            }
            InputReadProductImage.close();



        ////////////////////////////////////////////////


        my_names = (TextView) findViewById(R.id.login);
        my_names.setText(my_full_names);

        thisCategory = (TextView) findViewById(R.id.view_techniques);
        thisCategory.setText(my_product_category);

        thisProduct = (TextView) findViewById(R.id.textView12);
        thisProduct.setText(my_product_name);

        new_quantity = (EditText) findViewById(R.id.quantity);
        new_quantity.setText(my_product_quantity);

        new_unit_price = (EditText) findViewById(R.id.unit_price);
        new_unit_price.setText(my_product_price);

        new_description = (EditText) findViewById(R.id.description);
        new_description.setText(my_product_description);

        my_image = (ImageView) findViewById(R.id.my_image);

        imageLoader.DisplayImage(my_product_image, my_image);

        browse_image = (Button) findViewById(R.id.browse_image);
        upload_product = (Button) findViewById(R.id.update_product);



        browse_image.setOnClickListener(this);
        upload_product.setOnClickListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }




    }//end onCreate Method



    //////////////////////////code for image capture

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select new Product Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                my_image.setImageBitmap(bitmap);
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


    public void uploadImage() {

        //final String myemail = my_email;
        //final String mypdtid = my_product_id;
        final String pdt_cat = thisCategory.getText().toString().trim();
        final String pdt = thisProduct.getText().toString().trim();
        final String description = new_description.getText().toString().trim();
        final String quantity = new_quantity.getText().toString().trim();
        final String unit_price = new_unit_price.getText().toString().trim();

        final String image = getStringImage(bitmap);


        class UploadImage extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateMyProduct.this, "Please wait...", "Updating "+pdt, false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                AlertDialog.Builder builder1=new AlertDialog.Builder(UpdateMyProduct.this);
                builder1.setMessage(pdt+" updated successfully!");
                builder1.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getApplicationContext(), "Ok is pressed", Toast.LENGTH_LONG).show();

                       /*
                        Intent ii = new Intent(UpdateMyProduct.this,MyProducts.class);
                        ii.putExtra(MY_FULL_NAMES,myfullnames);
                        ii.putExtra(MY_EMAIL,my_email);
                        finish();
                        startActivity(ii);
                        */
                    }
                });
                builder1.show();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();



               /* param.put(KEY_DESCRIPTION, description);
                param.put(KEY_QUANTITY, quantity);
                param.put(KEY_UNIT_PRICE, unit_price);
                param.put(KEY_IMAGE, image);
                param.put(KEY_PRODUCT_ID, mypdtid);
                */
                String result = rh.sendPostRequest(UPDATE_URL, param);
                return result;

            }

        }
        UploadImage u = new UploadImage();
        u.execute();

    }



    ////////////////////////end this code





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void onClick(View v) {
        // TODO Auto-generated method stub



        if(v == browse_image){
            showFileChooser();
        }
        if(v == upload_product){
            uploadImage();
        }
    }






}


