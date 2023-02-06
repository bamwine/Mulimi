package com.example.lubwama.mulimimobileapplication;

/**
 * Created by LUBWAMA on 6/21/2017.
 */


        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.ArrayList;
        import java.util.HashMap;

        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import static android.content.Context.LAYOUT_INFLATER_SERVICE;
        import static android.content.Context.MODE_PRIVATE;

public class ListViewAdapter extends BaseAdapter {
String readMyEmail,readMyNames;
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView product_category;
        TextView product_name;
        TextView unit_price;
        TextView quantity;
        ImageView image;
        String product_id, product_description,my_email,my_full_names;


        inflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        product_category = (TextView) itemView.findViewById(R.id.crop_category);
        product_name = (TextView) itemView.findViewById(R.id.crop_product);
        unit_price = (TextView) itemView.findViewById(R.id.unit_price);
        quantity = (TextView) itemView.findViewById(R.id.quantity);

        // Locate the ImageView in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.flag);

        // Capture position and set results to the TextViews
        product_category.setText(resultp.get(MyProducts.CROP_CATEGORY));
        product_name.setText(resultp.get(MyProducts.CROP_PRODUCT));
        unit_price.setText(resultp.get(MyProducts.UNIT_PRICE));
        quantity.setText(resultp.get(MyProducts.QUANTITY));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(MyProducts.IMAGE), image);


        product_id = resultp.get(MyProducts.PRODUCT_ID);
        product_description = resultp.get(MyProducts.PRODUCT_DESCRIPTION);

        // Capture ListView item click





        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
//////////////////////////

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_fullimage_dialog);
                dialog.setTitle(resultp.get(MyProducts.CROP_PRODUCT));

                // set the custom dialog components - text, image and button

                ImageView image = (ImageView) dialog.findViewById(R.id.full_product_image);

                imageLoader.DisplayImage(resultp.get(MyProducts.IMAGE), image);

                Button dialogButtonUpdate = (Button) dialog.findViewById(R.id.dialogButtonUpdate);
                Button dialogButtonDelete = (Button) dialog.findViewById(R.id.dialogButtonDelete);

                //set onClick Event on Update Button
                dialogButtonUpdate.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//write product category, product_name, product_id, product_description, quantity, unit_price to txt files






                        try{



                            //write my_product_category to my_product_category.txt file


                            FileOutputStream fOutCategory = context.openFileOutput("my_product_category.txt",context. MODE_PRIVATE);
                            OutputStreamWriter outputCategoryWriter=new OutputStreamWriter(fOutCategory);
                            outputCategoryWriter.write(resultp.get(MyProducts.CROP_CATEGORY));
                            outputCategoryWriter.close();


                            FileOutputStream fOutProduct = context.openFileOutput("my_product_name.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductWriter=new OutputStreamWriter(fOutProduct);
                            outputProductWriter.write(resultp.get(MyProducts.CROP_PRODUCT));
                            outputProductWriter.close();

                            FileOutputStream fOutProductId = context.openFileOutput("my_product_id.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductIdWriter=new OutputStreamWriter(fOutProductId);
                            outputProductIdWriter.write(resultp.get(MyProducts.PRODUCT_ID));
                            outputProductIdWriter.close();

                            FileOutputStream fOutProductDescription = context.openFileOutput("my_product_description.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductDescriptionWriter=new OutputStreamWriter(fOutProductDescription);
                            outputProductDescriptionWriter.write(resultp.get(MyProducts.PRODUCT_DESCRIPTION));
                            outputProductDescriptionWriter.close();

                            FileOutputStream fOutProductQuantity = context.openFileOutput("my_product_quantity.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductQuantityWriter=new OutputStreamWriter(fOutProductQuantity);
                            outputProductQuantityWriter.write(resultp.get(MyProducts.QUANTITY));
                            outputProductQuantityWriter.close();

                            FileOutputStream fOutProductPrice = context.openFileOutput("my_product_price.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductPriceWriter=new OutputStreamWriter(fOutProductPrice);
                            outputProductPriceWriter.write(resultp.get(MyProducts.UNIT_PRICE));
                            outputProductPriceWriter.close();

                            FileOutputStream fOutProductImage = context.openFileOutput("my_product_image.txt", context.MODE_PRIVATE);
                            OutputStreamWriter outputProductImageWriter=new OutputStreamWriter(fOutProductImage);
                            outputProductImageWriter.write(resultp.get(MyProducts.IMAGE));
                            outputProductImageWriter.close();





                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(context, UpdateMyProduct.class);
                        context.startActivity(intent);


                    }
                });

                dialogButtonDelete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                /////////////////////




            }
        });
        return itemView;
    }
}



