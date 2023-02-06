package com.example.lubwama.mulimimobileapplication;


/**
 * Created by inn50cent on 6/2/2017.
 */
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.view.Menu;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import android.app.Activity;


public class SendSms extends Activity {
    String farmer_phone_number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms);
        Intent intent =getIntent();
        farmer_phone_number = intent.getStringExtra(ShowAllProducts.FARMER_PHONE_NUMBER);

        final Button btSend = (Button) findViewById(R.id.btnSend);
        final EditText sPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        sPhoneNo.setText(farmer_phone_number);



        final EditText sMessage = (EditText) findViewById(R.id.txtMessage);
        sMessage.requestFocus();
        btSend.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String strPhoneNo = sPhoneNo.getText().toString();
                String strMessage = sMessage.getText().toString();
                if(strMessage.equals("")){

                    final AlertDialog.Builder builder1=new AlertDialog.Builder(SendSms.this);
                    builder1.setMessage("Please compose a  message!");
                    builder1.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builder1.show();

                }
                else {

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(strPhoneNo, null, strMessage, null, null);
                    AlertDialog.Builder builder1=new AlertDialog.Builder(SendSms.this);
                    builder1.setMessage("Your Message has been sent successfully");
                    builder1.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            //Toast.makeText(getApplicationContext(), "Ok is pressed", Toast.LENGTH_LONG).show();
                            Intent ii = new Intent(SendSms.this,ShowAllProducts.class);
                            startActivity(ii);
                        }
                    });
                    builder1.show();
                }

            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

