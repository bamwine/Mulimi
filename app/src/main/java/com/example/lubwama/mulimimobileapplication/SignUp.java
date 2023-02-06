package com.example.lubwama.mulimimobileapplication;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;

        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;

        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;


        import java.io.IOException;


        import java.util.ArrayList;
        import java.util.List;

public class SignUp extends Activity{

    private EditText full_names;
    private EditText location;
    private EditText phone_number;
    private EditText email;
    private EditText password;
    // private RadioGroup radioGroup1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        full_names = (EditText) findViewById(R.id.your_full_names);
        full_names.requestFocus();
        location = (EditText) findViewById(R.id.your_location);
        phone_number = (EditText) findViewById(R.id.your_phone_number);
        email = (EditText) findViewById(R.id.your_email);
        password = (EditText) findViewById(R.id.your_password);
        //radioGroup1 = (RadioGroup) findViewById(R.id.gender);

        signUpImageButton();



    }

    void signUpImageButton() {
        final Context context = this;

        ImageView imageButton = (ImageView) findViewById(R.id.imageView7);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String your_full_names = full_names.getText().toString();
                String your_location = location.getText().toString();
                String your_phone_number = phone_number.getText().toString();
                String your_email = email.getText().toString();
                String your_password = password.getText().toString();
                //String radioGroup1 = radioGroup1.getText().toString();



                if( your_full_names.equals(""))
                {
                    full_names.setError( "Your names are required!" );
                    full_names.requestFocus();

                } else if( your_location.equals(""))
                {
                    location.setError( "your location is required!" );
                    location.requestFocus();

                }
                else if( your_phone_number.equals(""))
                {
                    phone_number.setError( "your Phone number is required!" );
                    phone_number.requestFocus();

                }
                else if( your_email.equals(""))
                {
                    email.setError( "your email is required!" );
                    email.requestFocus();

                }
                else if( your_password.equals(""))
                {
                    password.setError( "your password is required!" );
                    password.requestFocus();

                }
                else{

                    insertToDatabase(your_full_names,your_location,your_phone_number,your_email,your_password);

                }


            }

        });

        TextView signUpText = (TextView) findViewById(R.id.textView14);
        signUpText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String your_full_names = full_names.getText().toString();
                String your_location = location.getText().toString();
                String your_phone_number = phone_number.getText().toString();
                String your_email = email.getText().toString();
                String your_password = password.getText().toString();
                //String radioGroup1 = radioGroup1.getText().toString();



                if( your_full_names.equals(""))
                {
                    full_names.setError( "Your names are required!" );
                    full_names.requestFocus();

                } else if( your_location.equals(""))
                {
                    location.setError( "your location is required!" );
                    location.requestFocus();

                }
                else if( your_phone_number.equals(""))
                {
                    phone_number.setError( "your Phone number is required!" );
                    phone_number.requestFocus();

                }
                else if( your_email.equals(""))
                {
                    email.setError( "your email is required!" );
                    email.requestFocus();

                }
                else if( your_password.equals(""))
                {
                    password.setError( "your password is required!" );
                    password.requestFocus();

                }
                else{

                    insertToDatabase(your_full_names,your_location,your_phone_number,your_email,your_password);

                }


            }

        });
    }


    public void insert(View view){
        String your_full_names = full_names.getText().toString();
        String your_location = location.getText().toString();
        String your_phone_number = phone_number.getText().toString();
        String your_email = email.getText().toString();
        String your_password = password.getText().toString();
        //String radioGroup1 = radioGroup1.getText().toString();



        if( your_full_names.equals(""))
        {
            full_names.setError( "Your names are required!" );
            full_names.requestFocus();

        } else if( your_location.equals(""))
        {
            location.setError( "your location is required!" );
            location.requestFocus();

        }
        else if( your_phone_number.equals(""))
        {
            phone_number.setError( "your Phone number is required!" );
            phone_number.requestFocus();

        }
        else if( your_email.equals(""))
        {
            email.setError( "your email is required!" );
            email.requestFocus();

        }
        else if( your_password.equals(""))
        {
            password.setError( "your password is required!" );
            password.requestFocus();

        }
        else{

            insertToDatabase(your_full_names,your_location,your_phone_number,your_email,your_password);

        }
    }

    private void insertToDatabase(String your_full_names, String your_location, String your_phone_number, String your_email, String your_password){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SignUp.this, "Please wait", "Submitting your details...");
            }

            @Override
            protected String doInBackground(String... params) {
                String paramFullName = params[0];
                String paramLocation = params[1];
                String paramphoneNumber = params[2];
                String paramEmailAddress = params[3];
                String paramPassword = params[4];



                String your_full_names = full_names.getText().toString();
                String your_location = location.getText().toString();
                String your_phone_number = phone_number.getText().toString();
                String your_email = email.getText().toString();
                String your_password = password.getText().toString();
                //String radioGroup1 = radioGroup1.getText().toString();



                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("full_names", your_full_names));
                nameValuePairs.add(new BasicNameValuePair("location", your_location));
                nameValuePairs.add(new BasicNameValuePair("phone_number", your_phone_number));
                nameValuePairs.add(new BasicNameValuePair("email", your_email));
                nameValuePairs.add(new BasicNameValuePair("password", your_password));
                //nameValuePairs.add(new BasicNameValuePair("gender", gender));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://epipetalous-carrier.000webhostapp.com/mulimi_application/register_farmer.php");
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

                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder1=new AlertDialog.Builder(SignUp.this);
                builder1.setMessage("Details submitted successfully!");
                builder1.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getApplicationContext(), "Ok is pressed", Toast.LENGTH_LONG).show();
                        Intent ii = new Intent(SignUp.this,Login.class);
                        startActivity(ii);
                    }
                });
                builder1.show();




            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(your_full_names,your_location,your_phone_number,your_email,your_password);
    }


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




}
