package com.example.lubwama.mulimimobileapplication;


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
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
        import android.widget.VideoView;

        import org.w3c.dom.Text;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.HashMap;

/**
 * Created by inn50cent on 6/5/2017.
 */

public class ShareTechnique extends Activity implements View.OnClickListener {

    EditText description;
    Button post_technique, browse_photo;
    ImageView technique_photo;
    VideoView video;
    String my_email, my_full_names, my_phone, my_location,my_profile_pic;
    public static final String MY_FULL_NAMES = "my_full_names";
    public static final String MY_LOCATION = "my_location";
    public static final String MY_PHONE_NUMBER = "my_phone";
    public static final String MY_EMAIL = "my_email";
    public static final String MY_TECHNIQUE = "description";
    public static final String MY_TECHNIQUE_PHOTO = "technique_photo";
    public static final String MY_PROFILE_PIC = "my_profile_pic";
    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;

    public static final String UPLOAD_URL = "http://epipetalous-carrier.000webhostapp.com/mulimi_application/insertTechnique.php";
    private TextView full_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technique_sharing);
        Intent intent = getIntent();
        my_email = intent.getStringExtra(GetFarmerDetails.MY_EMAIL);
        my_full_names = intent.getStringExtra(GetFarmerDetails.MY_FULL_NAMES);
        my_location = intent.getStringExtra(GetFarmerDetails.MY_LOCATION);
        my_phone = intent.getStringExtra(GetFarmerDetails.MY_PHONE_NUMBER);
        my_profile_pic = intent.getStringExtra(GetFarmerDetails.MY_PROFILE_PIC);


        description = (EditText) findViewById(R.id.description);
        technique_photo = (ImageView) findViewById(R.id.photo);
        //video = (VideoView) findViewById(R.id.video);

        full_names = (TextView) findViewById(R.id.textView3);
        full_names.setText(my_full_names);


        //browse_photo = (Button) findViewById(R.id.browse_photo);
        browse_photo();

        viewTechniques();

    }
    void browse_photo(){

        final Context context = this;
        browse_photo = (Button) findViewById(R.id.browse_photo);
        browse_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

    }
    public void post_technique(View view){
        uploadTechnique();

    }

    void viewTechniques() {
        final Context context = this;

        TextView view_techniques = (TextView) findViewById(R.id.view_techniques);
        view_techniques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareTechnique.this, FarmingTechniques.class);
                intent.putExtra(MY_FULL_NAMES, my_full_names);
                intent.putExtra(MY_EMAIL, my_email);
                intent.putExtra(MY_LOCATION, my_location);
                intent.putExtra(MY_PHONE_NUMBER, my_phone);
                finish();
                startActivity(intent);

            }
        });
    }

    //////////////////////////code for image capture

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
                technique_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void uploadTechnique() {


        final String myemail = my_email;
        final String mylocation = my_location;
        final String myfullnames = my_full_names;
        final String myprofilepic = my_profile_pic;
        final String mydescription = description.getText().toString().trim();
        final String technique_photo = getStringImage(bitmap);

        if (mydescription.equals("")) {
            description.setError("Please describe technique!");
            description.requestFocus();


        } else {
            class UploadTechnique extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(ShareTechnique.this, "Please wait...", "Uploading your technique", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(ShareTechnique.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put(MY_EMAIL, myemail);
                    param.put(MY_FULL_NAMES, myfullnames);
                    param.put(MY_LOCATION, mylocation);
                    param.put(MY_PROFILE_PIC, myprofilepic);
                    param.put(MY_TECHNIQUE,mydescription);
                    param.put(MY_TECHNIQUE_PHOTO, technique_photo);

                    String result = rh.sendPostRequest(UPLOAD_URL, param);
                    return result;
                }
            }
            UploadTechnique u = new UploadTechnique();
            u.execute();

        }
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


        if (v == browse_photo) {
            showFileChooser();
        }
        if (v == post_technique) {
            uploadTechnique();
        }
    }
}
