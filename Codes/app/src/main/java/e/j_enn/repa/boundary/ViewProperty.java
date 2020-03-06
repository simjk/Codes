package e.j_enn.repa.boundary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import e.j_enn.repa.R;
import e.j_enn.repa.control.SearchDBManager;
import e.j_enn.repa.entity.Property;

public class ViewProperty extends AppCompatActivity {

    Toolbar toolbar;
    Property property;
    private SearchDBManager databaseHelper;
    Bundle bundle;
    ImageView likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //getSupportActionBar().hide(); //Hide ActionBar
        //toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        TextView tv = ((TextView) findViewById(R.id.toolbar_title));
        tv.setText("View");

        initView();
        setPropertyValues();

        likeButton = (ImageView) findViewById(R.id.whiteheart);
        likeButton.setTag(R.drawable.whiteheart);

//        likeButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                likeButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.favourite));
//                Toast.makeText(getApplicationContext(), "Added to favourite list", Toast.LENGTH_LONG).show();
//            }
//        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) likeButton.getTag();
                if (tag == R.drawable.whiteheart) {
                    likeButton.setImageResource(R.drawable.redheart);
                    likeButton.setTag(R.drawable.redheart);
                    Toast.makeText(getApplicationContext(), "Added to favourite list", Toast.LENGTH_LONG).show();
                }

                if(tag == R.drawable.redheart){
                    likeButton.setImageResource(R.drawable.whiteheart);
                    likeButton.setTag(R.drawable.whiteheart);
                    Toast.makeText(getApplicationContext(), "Removed from favourite list", Toast.LENGTH_LONG).show();
                }

            }
        });

//        likeButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                likeButton.setBackgroundResource(R.drawable.redheart);
//                Toast.makeText(getApplicationContext(), "Added to favourite list", Toast.LENGTH_LONG).show();
//            }
//        });


        findViewById(R.id.chatButton).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //setContentView(R.layout.fragment_chat);
                //TextView tv = ((TextView) findViewById(R.id.toolbar_title));
                //tv.setText("Chats");
                /*
                FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new ChatFragment());
                fr.commit();
                */
                startActivity(new Intent(ViewProperty.this, ChatActivity.class));
            }
        });


    }

    private void initView() {
        databaseHelper = new SearchDBManager(this);
        bundle = getIntent().getExtras();
    }

    private void setPropertyValues() {

        String housingUnit = bundle.get("housingUnit").toString();
        String address = bundle.get("address").toString();
        String townArea = bundle.get("townArea").toString();
        String propertyType = bundle.get("propertyType").toString();
        String price = bundle.get("price").toString();
        String tenure = bundle.get("tenure").toString();
        String floorArea = bundle.get("floorArea").toString();
        String bed = bundle.get("bed").toString();
        String bath = bundle.get("bath").toString();
        String description = bundle.get("description").toString();
        String user = bundle.get("user").toString();

        byte[] listProperty = databaseHelper.getSelectedImage(bundle);


        //Toast.makeText(this, "image" + listProperty, Toast.LENGTH_LONG).show();
        ImageView viewImage = findViewById(R.id.view_image);
        Bitmap bitMapImage = getImage(listProperty);
        //viewImage.setImageBitmap(bitMapImage);
        viewImage.setImageBitmap(Bitmap.createScaledBitmap(bitMapImage, 999, 999, false));

        AppCompatTextView housing_Unit = findViewById(R.id.housingUnit1);
        housing_Unit.setText(housingUnit);

        AppCompatTextView address1 = findViewById(R.id.address_1);
        address1.setText(address);

        AppCompatTextView property_Type = findViewById(R.id.propertyType1);
        property_Type.setText(propertyType);

        AppCompatTextView price_v = findViewById(R.id.price1);

        if (!price.isEmpty()) {
            double amount = Double.parseDouble(price);
            DecimalFormat formatter = new DecimalFormat("#,###");
            price_v.setText("S$" + formatter.format(amount));
        } else
            price_v.setText("S$0");

        AppCompatTextView tenure_v = findViewById(R.id.tenure_v1);
        tenure_v.setText(tenure);

        AppCompatTextView town_Area = findViewById(R.id.townarea1);
        town_Area.setText(townArea);

        AppCompatTextView floor_area = findViewById(R.id.floorarea1);
        floor_area.setText(floorArea);

        TextView bed_v = findViewById(R.id.bed_no);
        bed_v.setText(bed);

        TextView bath_v = findViewById(R.id.bath_no);
        bath_v.setText(bath);

        AppCompatTextView description_v = findViewById(R.id.descr1);
        description_v.setText(description);

        AppCompatTextView user_v = findViewById(R.id.user1);
        user_v.setText(user);

    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
