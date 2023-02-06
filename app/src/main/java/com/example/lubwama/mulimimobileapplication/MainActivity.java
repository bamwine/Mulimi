package com.example.lubwama.mulimimobileapplication;

        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.StrictMode;
        import android.view.Menu;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;

public class MainActivity extends Activity {

    final static  String MY_STRING="MY_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToLogin();
        goToSignUp();



    }


    public void advertiseProduct(View view){
        final String myString = "AdvertiseProduct";
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.putExtra(MY_STRING,myString);
        finish();
        startActivity(intent);

    }

    public  void viewProducts(View view){
        Intent intent = new Intent(MainActivity.this, ShowAllProducts.class);
        startActivity(intent);
    }

    public void signUp(View view){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }

    public  void shareTechnique(View view){

        final String myString = "ShareTechnique";
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.putExtra(MY_STRING,myString);
        finish();
        startActivity(intent);
    }

    public void log_in(View view){
        final String myString = "AdvertiseProduct";
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.putExtra(MY_STRING,myString);
        finish();
        startActivity(intent);

    }

    void goToLogin(){
        final Context context = this;

        TextView Login = (TextView) findViewById(R.id.login);
        Login.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }

        });

    }

    void goToSignUp(){
        final Context context = this;

        TextView SignUp = (TextView) findViewById(R.id.textView2);
        SignUp.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

