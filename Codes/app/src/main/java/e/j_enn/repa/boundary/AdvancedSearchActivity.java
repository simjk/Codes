package e.j_enn.repa.boundary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import e.j_enn.repa.R;
import e.j_enn.repa.control.SearchDBManager;
import e.j_enn.repa.entity.Property;

import static android.widget.Toast.*;


public class AdvancedSearchActivity extends AppCompatActivity {
    private final AppCompatActivity activity = AdvancedSearchActivity.this;
    private SearchDBManager databaseHelper;
    private Property property;
    Toolbar toolbar;
    String email;
    Bundle extras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedsearch);

        //setTitle("Browse");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = ((TextView) findViewById(R.id.toolbar_title));
        tv.setText("Browse");

        initObject();
        initView();
    }

    private void initObject() {
        databaseHelper = new SearchDBManager(activity);
        property = new Property();
        extras = getIntent().getExtras();
        email = extras.getString("email");
    }

    private void initView() {
        findViewById(R.id.btnAdvancedSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchBar = (EditText) findViewById(R.id.searchBarTextField);
                Spinner housingUnit = (Spinner) findViewById(R.id.housingUnitSpinner);
                Spinner townArea = (Spinner) findViewById(R.id.townAreaSpinner);
                Spinner propertyType = (Spinner) findViewById(R.id.propertyTypeSpinner);
                Spinner sellPriceRange = (Spinner) findViewById(R.id.sellingPriceSpinner);

                String housing_Unit = housingUnit.getSelectedItem().toString();
                String town_Area = townArea.getSelectedItem().toString();
                String property_Type = propertyType.getSelectedItem().toString();
                String sell_Price = sellPriceRange.getSelectedItem().toString();


                boolean choice = (!housing_Unit.equalsIgnoreCase("Choose a housing unit...")
                        && !town_Area.equalsIgnoreCase("Choose a town area..")
                        && !property_Type.equalsIgnoreCase("Choose a property type...")
                        && !sell_Price.equalsIgnoreCase("Choose a price range..."));
                if (choice) {
                  //  Toast.makeText(activity, email + " : " + housing_Unit + " : " + town_Area + " : " + property_Type + " : " + sell_Price, Toast.LENGTH_SHORT).show();
                    if (databaseHelper.checkProperty(housing_Unit, town_Area, property_Type, sell_Price)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("housing_Unit", housing_Unit);
                        bundle.putString("town_Area", town_Area);
                        bundle.putString("property_Type", property_Type);
                        bundle.putString("sell_Price", sell_Price);
                        bundle.putString("from", "advance");

                        startActivity(new Intent(activity, ResultActivity.class).putExtra("email", email).putExtras(bundle));
                        activity.finish();
                    } else {
                        startActivity(new Intent(activity, NoResultFound.class));
                        activity.finish();
                    }
                } else {

                    Toast.makeText(activity, "Please input all the preferences", LENGTH_SHORT).show();
                }
            }
        });

    }
}
