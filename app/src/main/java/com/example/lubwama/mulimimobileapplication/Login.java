package com.example.lubwama.mulimimobileapplication;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

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

        import android.content.Context;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.ProgressDialog;

        import android.content.Intent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

public class Login extends Activity {

    private EditText editTextUserName;
    private EditText editTextPassword;

    public static final String EMAIL_OR_PHONE = "EMAIL_OR_PHONE";
    public static final String MY_STRING = "MY_STRING";

    String email_or_phone;
    String password, myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        editTextUserName = (EditText) findViewById(R.id.email_or_phone);
        editTextUserName.requestFocus();

        editTextPassword = (EditText) findViewById(R.id.password);

        Intent intent = getIntent();
        myString = intent.getStringExtra(MainActivity.MY_STRING);
        goToSignUp();
        loginImageButton();
    }

    void goToSignUp() {
        final Context context = this;

        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }

        });

    }

    void loginImageButton() {
        final Context context = this;

        ImageView imageButton = (ImageView) findViewById(R.id.imageView5);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                email_or_phone = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                login(email_or_phone, password);

            }

        });

        TextView Login = (TextView) findViewById(R.id.textView15);
        Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                email_or_phone = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                login(email_or_phone, password);

            }

        });

    }

    public void invokeLogin(View view) {
        email_or_phone = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();

        login(email_or_phone, password);

    }

    private void login(final String email_or_phone, String password) {
        String myEmail = email_or_phone;
        String myPassword = password;

        if (myEmail.equals("")) {
            editTextUserName.setError("Email or phone required!");
            editTextUserName.requestFocus();
        } else if (myPassword.equals("")) {
            editTextPassword.setError("Password required!");
            editTextPassword.requestFocus();
        }
        else {



        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Verifying user...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email_or_phone", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://epipetalous-carrier.000webhostapp.com/mulimi_application/login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if (s.equalsIgnoreCase("success")) {


                    Intent intent = new Intent(Login.this, GetFarmerDetails.class);
                    intent.putExtra(EMAIL_OR_PHONE, email_or_phone);
                    intent.putExtra(MY_STRING, myString);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(email_or_phone, password);

    }

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

