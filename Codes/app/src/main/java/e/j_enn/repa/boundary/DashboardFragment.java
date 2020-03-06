package e.j_enn.repa.boundary;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import e.j_enn.repa.R;
import e.j_enn.repa.control.GraphCalculation;
import e.j_enn.repa.control.HttpHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private String TAG = DashboardFragment.class.getSimpleName();
    private static String url = ("https://data.gov.sg/api/action/datastore_search?resource_id=1b702208-44bf-4829-b620-4615ee19b57c&limit=99999");
    Toolbar toolbar;
    ArrayList<HashMap<String, String>> recordList;
    Dialog myDialog;
    private ProgressDialog pDialog;
    GraphView graph;
    GraphView graph1;
    String spinnertext1 = "";
    String spinnertext2 = "";
    String spinnertext3 = "";
    public static String pastfilteraverage = "0";
    public static String pastaverage = "0";
    ArrayList<String> flattype = new ArrayList<String>();
    ArrayList<String> floorarea = new ArrayList<String>();
    ArrayList<String> year = new ArrayList<String>();
    ArrayList<String> year1 = new ArrayList<String>();
    ArrayList<String> year2 = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> price1 = new ArrayList<String>();
    ArrayList<String> townarea = new ArrayList<String>();
    ArrayList<String> averageperyear = new ArrayList<String>();
    ArrayList<String> averageperyear1 = new ArrayList<String>();
    ArrayList<String> eachyear = new ArrayList<String>();
    ArrayList<String> eachyear1 = new ArrayList<String>();
    TextView errorText,errorText1,errorText2;

    GraphCalculation gc = new GraphCalculation();
    private ImageView button;
    private Button condobutton;

    //private Button hdbbutton;
    //private Paint paint;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Dashboard");

        //hdbbutton = getActivity().findViewById(R.id.hdb);

        recordList = new ArrayList<>();
        myDialog = new Dialog(getActivity());

        new GetResults().execute();

        condobutton = getActivity().findViewById(R.id.condo);
        condobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new DashboardFragmentPage2());
                fr.commit();
            }
        });
        button = getActivity().findViewById(R.id.filter);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.customfilterpopup, null);
                //mBuilder.setTitle("Filter");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner1);
                final Spinner mSpinner2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Spinner mSpinner3 = (Spinner) mView.findViewById(R.id.spinner3);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.town_array));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                mBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a town area...")
                                && !mSpinner2.getSelectedItem().toString().equalsIgnoreCase("Choose a room...")
                                && !mSpinner3.getSelectedItem().toString().equalsIgnoreCase("Choose a floor area...")
                                ) {
//                            Toast.makeText(getActivity(), mSpinner.getSelectedItem() + ","
//                                            + mSpinner2.getSelectedItem() + ","
//                                            + mSpinner3.getSelectedItem()
//                                            .toString(),
//                                    Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();

                            spinnertext1 = mSpinner.getSelectedItem().toString();
                            spinnertext2 = mSpinner2.getSelectedItem().toString();
                            spinnertext3 = mSpinner3.getSelectedItem().toString();
                            graph.removeAllSeries();
                            eachyear1 = gc.calculateFilterResalePrice(year1, averageperyear1, price, townarea, flattype, floorarea, spinnertext1, spinnertext2, spinnertext3);
                            double totalfilteraverageprice = gc.calculateTotalFilterResalePrice(year1, price, townarea, flattype, floorarea, spinnertext1, spinnertext2, spinnertext3);

                            TextView currentfilterprice = (TextView) getActivity().findViewById(R.id.textView2);
                            TextView LastfilterResalePrice = (TextView) getActivity().findViewById(R.id.textView3);
                            TextView current2018filterprice = (TextView) getActivity().findViewById(R.id.textView9);
                            TextView current2017filterprice = (TextView) getActivity().findViewById(R.id.textView13);
                            ImageView currentredarrow = (ImageView) getActivity().findViewById(R.id.imageView5);
                            ImageView currenetgreenarrow = (ImageView) getActivity().findViewById(R.id.imageView3);
                            ImageView redarrowfilter2018 = (ImageView) getActivity().findViewById(R.id.imageView7);
                            ImageView greenarrowfilter2018 = (ImageView) getActivity().findViewById(R.id.chatButton);
                            ImageView redarrowfilter2017 = (ImageView) getActivity().findViewById(R.id.imageView8);
                            ImageView greenarrowfilter2017 = (ImageView) getActivity().findViewById(R.id.imageView4);
                            String currenttotalfilteraverage = Integer.toString((int) totalfilteraverageprice);
                            TextView percentfilter = (TextView) getActivity().findViewById(R.id.textView10);
                            TextView percentfilter1 = (TextView) getActivity().findViewById(R.id.textView14);

                            if (Integer.parseInt(currenttotalfilteraverage) >= Integer.parseInt(pastfilteraverage)) {
                                currentredarrow.setVisibility(View.INVISIBLE);
                                currenetgreenarrow.setVisibility(View.VISIBLE);
                            } else {
                                currentredarrow.setVisibility(View.VISIBLE);
                                currenetgreenarrow.setVisibility(View.INVISIBLE);
                            }
                            float percentfilterchange = 0;
                            float percentfilterchange1 = 0;
                            if (Integer.parseInt(averageperyear1.get(3)) >= Integer.parseInt(averageperyear1.get(2))) {
                                percentfilterchange = ((Float.parseFloat(averageperyear1.get(3)) - Float.parseFloat(averageperyear1.get(2))) / Float.parseFloat(averageperyear1.get(3))) * 100;
                                greenarrowfilter2018.setVisibility(View.VISIBLE);
                                redarrowfilter2018.setVisibility(View.INVISIBLE);
                            } else if (Integer.parseInt(averageperyear1.get(3)) < Integer.parseInt(averageperyear1.get(2))) {
                                percentfilterchange = ((Float.parseFloat(averageperyear1.get(2)) - Float.parseFloat(averageperyear1.get(3))) / Float.parseFloat(averageperyear1.get(2))) * 100;
                                redarrowfilter2018.setVisibility(View.VISIBLE);
                                greenarrowfilter2018.setVisibility(View.INVISIBLE);
                            }

                            if (Integer.parseInt(averageperyear1.get(2)) >= Integer.parseInt(averageperyear1.get(1))) {
                                percentfilterchange1 = ((Float.parseFloat(averageperyear1.get(2)) - Float.parseFloat(averageperyear1.get(1))) / Float.parseFloat(averageperyear1.get(2))) * 100;
                                redarrowfilter2017.setVisibility(View.INVISIBLE);
                                greenarrowfilter2017.setVisibility(View.VISIBLE);
                            } else if (Integer.parseInt(averageperyear1.get(2)) < Integer.parseInt(averageperyear1.get(1))) {
                                percentfilterchange1 = ((Float.parseFloat(averageperyear1.get(1)) - Float.parseFloat(averageperyear1.get(2))) / Float.parseFloat(averageperyear1.get(1))) * 100;
                                redarrowfilter2017.setVisibility(View.VISIBLE);
                                greenarrowfilter2017.setVisibility(View.INVISIBLE);
                            }
                            DecimalFormat df = new DecimalFormat("#0.0");
                            String formattedfilterpercentchange = df.format(percentfilterchange);
                            String formattedfilterpercentchange1 = df.format(percentfilterchange1);
                            percentfilter.setText(formattedfilterpercentchange + "%");
                            percentfilter1.setText(formattedfilterpercentchange1 + "%");
                            currentfilterprice.setText("\nS$" + currenttotalfilteraverage);
                            current2018filterprice.setText("S$" + averageperyear1.get(3));
                            current2017filterprice.setText("S$" + averageperyear1.get(2));
                            LastfilterResalePrice.setText("Last: S$" + pastfilteraverage);
                            pastfilteraverage = currenttotalfilteraverage;


                            plotfiltergraph();
                            averageperyear1.clear();
                            eachyear1.clear();
                        } else {
                            errorText = (TextView) mSpinner.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);
                            errorText.setText("Please choose your town area.");

                            errorText1 = (TextView) mSpinner2.getSelectedView();
                            errorText1.setError("");
                            errorText1.setTextColor(Color.RED);
                            errorText1.setText("Please choose your room type.");

                            errorText2 = (TextView) mSpinner3.getSelectedView();
                            errorText2.setError("");
                            errorText2.setTextColor(Color.RED);
                            errorText2.setText("Please choose your floor area.");
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                positiveButton.setLayoutParams(positiveButtonLL);

                positiveButton.setTextColor(getResources().getColor(R.color.white));
                positiveButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.pink));
            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetResults extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node

                    JSONObject result = jsonObj.getJSONObject("result");
                    JSONArray records = result.getJSONArray("records");

                    // looping through All Records
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject c = records.getJSONObject(i);


                        String town = c.getString("town");
                        String flat_type = c.getString("flat_type");
                        String flat_model = c.getString("flat_model");
                        String floor_area_sqm = c.getString("floor_area_sqm");
                        String street_name = c.getString("street_name");
                        String resale_price = c.getString("resale_price");
                        String month = c.getString("month");
                        String remaining_lease = c.getString("remaining_lease");
                        String lease_commence_date = c.getString("lease_commence_date");
                        String storey_range = c.getString("storey_range");
                        String _id = c.getString("_id");
                        String block = c.getString("block");

                        year.add(month);
                        year1.add(month);
                        year2.add(month);
                        price.add(resale_price);
                        price1.add(resale_price);
                        townarea.add(town);
                        flattype.add(flat_type);
                        floorarea.add(floor_area_sqm);

                        // tmp hash map for single contact
                        HashMap<String, String> record = new HashMap<>();

                        // adding each child node to HashMap key => value
                        record.put("town", town);
                        record.put("flat_type", flat_type);
                        record.put("flat_model", flat_model);
                        record.put("floor_area_sqm", floor_area_sqm);
                        record.put("street_name", street_name);
                        record.put("resale_price", resale_price);
                        record.put("month", month);
                        record.put("remaining_lease", remaining_lease);
                        record.put("lease_commence_date", lease_commence_date);
                        record.put("storey_range", storey_range);
                        record.put("_id", _id);
                        record.put("block", block);


                        // adding contact to contact list
                        recordList.add(record);
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
           /* ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, recordList,
                    R.layout.list_item, new String[]{"month", "town", "resale_price"
            }, new int[]{R.id.month, R.id.town,
                    R.id.resale_price});

            lv.setAdapter(adapter);*/

            eachyear = gc.calculateAverageResalePrice(year, averageperyear, price);
            plotgraph();

            year.clear();
            eachyear.clear();
            averageperyear.clear();
            double totalaverage = gc.calculateTotalAverageResalePrice(price1);
            double totaleightaverage = gc.calculate2018TotalAverageResalePrice(price1, year2);
            double totalsevenaverage = gc.calculate2017TotalAverageResalePrice(price1, year2);
            double totalsixaverage = gc.calculate2016TotalAverageResalePrice(price1, year2);
            String currenttotalaverage = Integer.toString((int) totalaverage);
            String currenttotaleightaverage = Integer.toString((int) totaleightaverage);
            String currenttotalsevenaverage = Integer.toString((int) totalsevenaverage);
            String currenttotalsixaverage = Integer.toString((int) totalsixaverage);

            TextView currentprice = (TextView) getActivity().findViewById(R.id.textView2);
            TextView LastResalePrice = (TextView) getActivity().findViewById(R.id.textView3);
            TextView eightResalePrice = (TextView) getActivity().findViewById(R.id.textView9);
            TextView sevenResalePrice = (TextView) getActivity().findViewById(R.id.textView13);
            ImageView redarrow = (ImageView) getActivity().findViewById(R.id.imageView5);
            ImageView greenarrow = (ImageView) getActivity().findViewById(R.id.imageView3);
            ImageView redarrow2018 = (ImageView) getActivity().findViewById(R.id.imageView7);
            ImageView greenarrow2018 = (ImageView) getActivity().findViewById(R.id.chatButton);
            ImageView redarrow2017 = (ImageView) getActivity().findViewById(R.id.imageView8);
            ImageView greenarrow2017 = (ImageView) getActivity().findViewById(R.id.imageView4);
            TextView percent = (TextView) getActivity().findViewById(R.id.textView10);
            TextView percent1 = (TextView) getActivity().findViewById(R.id.textView14);

            if (Integer.parseInt(currenttotalaverage) >= Integer.parseInt(pastaverage)) {
                pastaverage = currenttotalaverage;
                redarrow.setVisibility(View.INVISIBLE);
                greenarrow.setVisibility(View.VISIBLE);
            } else {
                pastaverage = currenttotalaverage;
                redarrow.setVisibility(View.VISIBLE);
                greenarrow.setVisibility(View.INVISIBLE);
            }
            float percentchange = 0;
            float percentchange1 = 0;
            if (Integer.parseInt(currenttotaleightaverage) >= Integer.parseInt(currenttotalsevenaverage)) {
                percentchange = ((Float.parseFloat(currenttotaleightaverage) - Float.parseFloat(currenttotalsevenaverage)) / Float.parseFloat(currenttotaleightaverage)) * 100;
                redarrow2018.setVisibility(View.INVISIBLE);
                greenarrow2018.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(currenttotaleightaverage) < Integer.parseInt(currenttotalsevenaverage)) {
                percentchange = ((Float.parseFloat(currenttotalsevenaverage) - Float.parseFloat(currenttotaleightaverage)) / Float.parseFloat(currenttotalsevenaverage)) * 100;
                redarrow2018.setVisibility(View.VISIBLE);
                greenarrow2018.setVisibility(View.INVISIBLE);
            }

            if (Integer.parseInt(currenttotalsevenaverage) >= Integer.parseInt(currenttotalsixaverage)) {
                percentchange1 = ((Float.parseFloat(currenttotalsevenaverage) - Float.parseFloat(currenttotalsixaverage)) / Float.parseFloat(currenttotalsevenaverage)) * 100;
                redarrow2017.setVisibility(View.INVISIBLE);
                greenarrow2017.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(currenttotalsevenaverage) < Integer.parseInt(currenttotalsixaverage)) {
                percentchange1 = ((Float.parseFloat(currenttotalsixaverage) - Float.parseFloat(currenttotalsevenaverage)) / Float.parseFloat(currenttotalsixaverage)) * 100;
                redarrow2017.setVisibility(View.VISIBLE);
                greenarrow2017.setVisibility(View.INVISIBLE);
            }

            DecimalFormat df = new DecimalFormat("#0.0");
            String formattedpercentchange = df.format(percentchange);
            String formattedpercentchange1 = df.format(percentchange1);
            percent.setText(formattedpercentchange + "%");
            percent1.setText(formattedpercentchange1 + "%");
            currentprice.setText("\nS$" + currenttotalaverage);
            eightResalePrice.setText("S$" + currenttotaleightaverage);
            sevenResalePrice.setText("S$" + currenttotalsevenaverage);
            LastResalePrice.setText("Last: S$" + pastaverage);
            price1.clear();
            year2.clear();


        }
    }

    private void plotgraph() {
        graph = (GraphView) getView().findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (int i = 0; i < eachyear.size(); i++) {
            Integer x = Integer.parseInt(eachyear.get(i));
            Integer y = Integer.parseInt(averageperyear.get(i));
            DataPoint point = new DataPoint(x, y);
            series.appendData(point, true, eachyear.size());
        }
        series.setColor(Color.rgb(255, 205, 210));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.getDataPointsRadius();
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();

        graph.addSeries(series);
        graph.setTitle("AVERAGE RESALE PRICE");
        gridLabel.setHorizontalAxisTitle("YEAR");
        gridLabel.setVerticalAxisTitle("RESALE PRICE ($S)");
        gridLabel.setVerticalAxisTitleTextSize(30);
        gridLabel.setPadding(45);
        gridLabel.setTextSize(35);
        gridLabel.setLabelsSpace(10);
        graph.getViewport().setYAxisBoundsManual(false);
        graph.getViewport().setXAxisBoundsManual(false);
        // graph.getViewport().setMinX(2015);
        //graph.getViewport().setMaxX(2018);
        //graph.getViewport().setMinY(100000.0);
        // graph.getViewport().setMaxY(200000.0);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        //graph.getGridLabelRenderer().setNumVerticalLabels(4);

        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().reloadStyles();
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void plotfiltergraph() {
        graph1 = (GraphView) getView().findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (int i = 0; i < eachyear1.size(); i++) {
            Integer x = Integer.parseInt(eachyear1.get(i));
            Integer y = Integer.parseInt(averageperyear1.get(i));
            DataPoint point = new DataPoint(x, y);
            series.appendData(point, true, eachyear1.size());
        }
        series.setColor(Color.rgb(255, 205, 210));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.getDataPointsRadius();
        GridLabelRenderer gridLabel = graph1.getGridLabelRenderer();

        graph1.addSeries(series);
        graph1.setTitle(spinnertext1 + "   " + spinnertext2 + "   " + spinnertext3 + " Sqm");
        gridLabel.setHorizontalAxisTitle("YEAR");
        gridLabel.setVerticalAxisTitle("RESALE PRICE ($S)");
        gridLabel.setVerticalAxisTitleTextSize(30);
        gridLabel.setPadding(45);
        gridLabel.setTextSize(35);
        gridLabel.setLabelsSpace(10);
        graph1.getViewport().setYAxisBoundsManual(false);
        graph1.getViewport().setXAxisBoundsManual(false);
        graph1.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph1.getGridLabelRenderer().setHighlightZeroLines(false);
        graph1.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph1.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph1.getGridLabelRenderer().reloadStyles();
        graph1.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
