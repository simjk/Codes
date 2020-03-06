package e.j_enn.repa.boundary;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.content.pm.PackageManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import e.j_enn.repa.R;
import e.j_enn.repa.boundary.MeFragment;
import e.j_enn.repa.control.SearchDBManager;
import e.j_enn.repa.entity.Property;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellFragment extends Fragment {
    Toolbar toolbar;
    Spinner housing_unit, property_type, no_of_bed, no_of_bath, town_area;
    EditText tenure_value, floor_area, address, sell_price, description;
    Bitmap bitmapImage;
    ImageView imageView;
    Uri returnUri;
    private SearchDBManager databaseHelper;
    private Property property;
    String email;
    final int REQUEST_CODE_GALLERY = 999;

    public SellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Upload");

        email = getArguments().getString("email");

        view.findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                            , REQUEST_CODE_GALLERY);
                } else {
                    startGallery();
                }
            }
        });
        getActivity().findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initObject();

                if (SellEmpty()) {
                    postDataToSQLite();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    mBuilder.setTitle("Upload Success!");
                    mBuilder.setPositiveButton("Go!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MeFragment meFragment = new MeFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.main_frame, meFragment);
                            ft.commit();

                        }
                    });

                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            }
        });
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (requestCode == 1000) {
            returnUri = data.getData();
            bitmapImage = null;
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(returnUri);
                //bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                bitmapImage = BitmapFactory.decodeStream(inputStream);
                imageView = (ImageView) getActivity().findViewById(R.id.imageView);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapImage, 999, 999, false));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initObject() {
        housing_unit = getView().findViewById(R.id.spinner1); //housing_unit
        property_type = getView().findViewById(R.id.spinner2); //property_type
        town_area = getView().findViewById(R.id.spinner3); //town_area
        tenure_value = getView().findViewById(R.id.tenure_value);
        floor_area = getView().findViewById(R.id.floor_area);
        address = getView().findViewById(R.id.address);
        no_of_bed = getView().findViewById(R.id.no_of_bed); //no_of_bed
        no_of_bath = getView().findViewById(R.id.no_of_bath); //no_of_bath
        sell_price = getView().findViewById(R.id.sell_price);
        description = getView().findViewById(R.id.description_value);


        databaseHelper = new SearchDBManager(getActivity());
        property = new Property();
    }

    private void postDataToSQLite() {

        String capAddress = address.getText().toString().toUpperCase().trim();

        property.setProperty_Image(getBytes(bitmapImage));
        property.setHousing_unit(housing_unit.getSelectedItem().toString().trim());
        property.setProperty_type(property_type.getSelectedItem().toString().trim());
        property.setTown_area(town_area.getSelectedItem().toString().trim());
        property.setTenure(tenure_value.getText().toString().trim());
        property.setFloor_area(floor_area.getText().toString().trim());
        property.setAddress(capAddress);
        property.setNo_of_bed(no_of_bed.getSelectedItem().toString().trim());
        property.setNo_of_bath(no_of_bath.getSelectedItem().toString().trim());
        property.setPrice(sell_price.getText().toString().trim());
        property.setDescription(description.getText().toString().trim());
        property.setUser(email);

        databaseHelper.addProperty(property);
        emptyInputEditText();
        // Toast.makeText(getActivity(), "Upload Success.", Toast.LENGTH_LONG).show();

    }

    public boolean SellEmpty() {
        boolean result = true;
        TextView errorText;

        if (returnUri == null) {
            imageView = (ImageView) getView().findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.notfound);
            result = false;
        }

        if (housing_unit.getSelectedItem().toString().equalsIgnoreCase("Choose a housing unit...")) {
            errorText = (TextView) housing_unit.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Please choose your housing unit.");
            result = false;
        }

        if (property_type.getSelectedItem().toString().equalsIgnoreCase("Choose a property type...")) {
            errorText = (TextView) property_type.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Please choose your property.");
            result = false;
        }

        if (town_area.getSelectedItem().toString().equalsIgnoreCase("Choose a town area...")) {
            errorText = (TextView) town_area.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Please choose your area.");
            result = false;
        }

        if (tenure_value.getText().toString().isEmpty()) {
            tenure_value.requestFocus();
            tenure_value.setError("Tenure value cannot be empty.");
            result = false;
        }

        if (floor_area.getText().toString().isEmpty()) {
            floor_area.requestFocus();
            floor_area.setError("Floor area cannot be empty.");
            result = false;
        }

        if (address.getText().toString().isEmpty()) {
            address.requestFocus();
            address.setError("Address cannot be empty.");
            result = false;
        }

        if (sell_price.getText().toString().isEmpty()) {
            sell_price.requestFocus();
            sell_price.setError("Price cannot be empty.");
            result = false;
        }

        if (description.getText().toString().isEmpty()) {
            description.requestFocus();
            description.setError("Description cannot be empty.");
            result = false;
        }
        return result;
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void emptyInputEditText() {
        tenure_value.setText(null);
        floor_area.setText(null);
        address.setText(null);
        sell_price.setText(null);
        description.setText(null);
    }
}
