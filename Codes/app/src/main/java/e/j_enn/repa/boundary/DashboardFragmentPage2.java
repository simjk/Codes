package e.j_enn.repa.boundary;

import android.app.AlertDialog;
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

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import e.j_enn.repa.R;
import e.j_enn.repa.boundary.DashboardFragment;
import e.j_enn.repa.control.GraphCalculation;
import e.j_enn.repa.control.HttpHandler;

public class DashboardFragmentPage2 extends Fragment {
    private String TAG = DashboardFragment.class.getSimpleName();
    Toolbar toolbar;
    GraphView graph;
    GraphView graph1;
    private Button hdbbutton;
    private ProgressDialog pDialog;
    public static String pastfilteraverage = "0";
    public static String pastaverage = "0";
    ArrayList<String> town = new ArrayList<String>();
    ArrayList<String> year = new ArrayList<String>();
    ArrayList<String> year1 = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> price1 = new ArrayList<String>();
    ArrayList<String> floor_area = new ArrayList<String>();
    ArrayList<String> averageperyear = new ArrayList<String>();
    ArrayList<String> averageperyear1 = new ArrayList<String>();
    ArrayList<String> eachyear = new ArrayList<String>();
    ArrayList<String> eachyear1 = new ArrayList<String>();
    GraphCalculation gc = new GraphCalculation();
    private ImageView button;
    String spinnertext1 = "";
    String spinnertext2 = "";
    public DashboardFragmentPage2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboardpage2, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Dashboard");

        new GetResults2().execute();

        hdbbutton = getActivity().findViewById(R.id.hdb);
        hdbbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new DashboardFragment());
                fr.commit();
            }
        });

        button = getActivity().findViewById(R.id.filter);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.customfilterpopupcondo, null);
                //mBuilder.setTitle("Filter Dashboard Results");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner4);
                final Spinner mSpinner2 = (Spinner) mView.findViewById(R.id.spinner5);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.condo_array));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                mBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a town area...")
                                && !mSpinner2.getSelectedItem().toString().equalsIgnoreCase("Choose a floor area...")
                                ) {
//                            Toast.makeText(getActivity(), mSpinner.getSelectedItem() + ","
//                                            + mSpinner2.getSelectedItem()
//                                            .toString(),
//                                    Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();

                            spinnertext1 = mSpinner.getSelectedItem().toString();
                            spinnertext2 = mSpinner2.getSelectedItem().toString();

                        }
                        graph.removeAllSeries();
                        eachyear1 = gc.calculateAverageFilterResalePriceCondo(year1, averageperyear1, price, town, floor_area, spinnertext1, spinnertext2);
                        double totalfilteraverageprice = gc.calculateTotalFilterResalePriceCondo(year1, price, town, floor_area, spinnertext1, spinnertext2);
                        String currenttotalfilteraverage = Integer.toString((int)totalfilteraverageprice);
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
                        TextView percentfilter = (TextView) getActivity().findViewById(R.id.textView10);
                        TextView percentfilter1 = (TextView) getActivity().findViewById(R.id.textView14);

                        if(Integer.parseInt(currenttotalfilteraverage) >=  Integer.parseInt(pastfilteraverage)) {
                            currentredarrow.setVisibility(View.INVISIBLE);
                            currenetgreenarrow.setVisibility(View.VISIBLE);
                        } else {
                            currentredarrow.setVisibility(View.VISIBLE);
                            currenetgreenarrow.setVisibility(View.INVISIBLE);
                        }
                        float percentfilterchange = 0;
                        float percentfilterchange1 = 0;
                        if(Integer.parseInt(averageperyear1.get(3)) >= Integer.parseInt(averageperyear1.get(2))) {
                            percentfilterchange = ((Float.parseFloat(averageperyear1.get(3)) - Float.parseFloat(averageperyear1.get(2))) / Float.parseFloat(averageperyear1.get(3))) *100;
                            greenarrowfilter2018.setVisibility(View.VISIBLE);
                            redarrowfilter2018.setVisibility(View.INVISIBLE);
                        } else if (Integer.parseInt(averageperyear1.get(3)) < Integer.parseInt(averageperyear1.get(2))) {
                            percentfilterchange = ((Float.parseFloat(averageperyear1.get(2)) - Float.parseFloat(averageperyear1.get(3))) / Float.parseFloat(averageperyear1.get(2))) *100;
                            redarrowfilter2018.setVisibility(View.VISIBLE);
                            greenarrowfilter2018.setVisibility(View.INVISIBLE);
                        }
                        if(Integer.parseInt(averageperyear1.get(2)) >= Integer.parseInt(averageperyear1.get(1))) {
                            percentfilterchange1 = ((Float.parseFloat(averageperyear1.get(2)) - Float.parseFloat(averageperyear1.get(1))) / Float.parseFloat(averageperyear1.get(2))) *100;
                            redarrowfilter2017.setVisibility(View.INVISIBLE);
                            greenarrowfilter2017.setVisibility(View.VISIBLE);
                        } else if (Integer.parseInt(averageperyear1.get(2)) < Integer.parseInt(averageperyear1.get(1))) {
                            percentfilterchange1 = ((Float.parseFloat(averageperyear1.get(1)) - Float.parseFloat(averageperyear1.get(2))) / Float.parseFloat(averageperyear1.get(1))) *100;
                            redarrowfilter2017.setVisibility(View.VISIBLE);
                            greenarrowfilter2017.setVisibility(View.INVISIBLE);
                        }
                        DecimalFormat df=new DecimalFormat("#0.0");
                        String formattedfilterpercentchange = df.format(percentfilterchange);
                        String formattedfilterpercentchange1 = df.format(percentfilterchange1);
                        percentfilter.setText(formattedfilterpercentchange + "%");
                        percentfilter1.setText(formattedfilterpercentchange1 + "%");
                        currentfilterprice.setText("\nS$" +currenttotalfilteraverage);
                        LastfilterResalePrice.setText("Last: S$" + pastfilteraverage);
                        current2018filterprice.setText("S$" + averageperyear1.get(3));
                        current2017filterprice.setText("S$" + averageperyear1.get(2));
                        pastfilteraverage = currenttotalfilteraverage;


                        plotfiltergraph();
                        averageperyear1.clear();
                        eachyear1.clear();
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

    private class GetResults2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getActivity().getAssets().open("Condo.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            if (loadJSONFromAsset() != null) {
                try {
                    JSONObject jsonObj = new JSONObject(loadJSONFromAsset());

                    // Getting JSON Array node
                    JSONArray records = jsonObj.getJSONArray("results");

                    // looping through All Records
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject c = records.getJSONObject(i);


                        String sale_date = c.getString("Sale Date");
                        String floor_area_sqm = c.getString("Area (sqm)");
                        String transacted_price = c.getString("Transacted Price ($)");
                        String planning_area = c.getString("Planning Area");

                        String uppertown = planning_area.toUpperCase();
                        year.add(sale_date);
                        year1.add(sale_date);
                        price.add(transacted_price);
                        price1.add(transacted_price);
                        town.add(uppertown);
                        floor_area.add(floor_area_sqm);

                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            eachyear = gc.calculateAverageResalePriceCondo(year, averageperyear, price);
            plotgraph();

            year.clear();
            eachyear.clear();


            double totalaverage = gc.calculateTotalAverageResalePriceCondo(price1);
            String currenttotalaverage = Integer.toString((int)totalaverage);
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

            if(Integer.parseInt(currenttotalaverage) >=  Integer.parseInt(pastaverage)) {
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
            if(Integer.parseInt(averageperyear.get(3)) >= Integer.parseInt(averageperyear.get(2))) {
                percentchange = ((Float.parseFloat(averageperyear.get(3)) - Float.parseFloat(averageperyear.get(2))) / Float.parseFloat(averageperyear.get(3))) *100;
                redarrow2018.setVisibility(View.INVISIBLE);
                greenarrow2018.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(averageperyear.get(3)) < Integer.parseInt(averageperyear.get(2))) {
                percentchange = (Float.parseFloat(averageperyear.get(2)) - Float.parseFloat(averageperyear.get(3)) / Float.parseFloat(averageperyear.get(2))) *100;
                redarrow2018.setVisibility(View.VISIBLE);
                greenarrow2018.setVisibility(View.INVISIBLE);
            }

            if(Integer.parseInt(averageperyear.get(2)) >= Integer.parseInt(averageperyear.get(1))) {
                percentchange1 = ((Float.parseFloat(averageperyear.get(2)) - Float.parseFloat(averageperyear.get(1))) / Float.parseFloat(averageperyear.get(2))) *100;
                redarrow2017.setVisibility(View.INVISIBLE);
                greenarrow2017.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(averageperyear.get(2)) < Integer.parseInt(averageperyear.get(1))) {
                percentchange1 = ((Float.parseFloat(averageperyear.get(1)) - Float.parseFloat(averageperyear.get(2))) / Float.parseFloat(averageperyear.get(1))) *100;
                redarrow2017.setVisibility(View.VISIBLE);
                greenarrow2017.setVisibility(View.INVISIBLE);
            }
            DecimalFormat df=new DecimalFormat("#0.0");
            String formattedpercentchange = df.format(percentchange);
            String formattedpercentchange1 = df.format(percentchange1);
            percent.setText(formattedpercentchange + "%");
            percent1.setText(formattedpercentchange1 + "%");
            currentprice.setText("\nS$" +currenttotalaverage);
            LastResalePrice.setText("Last: S$" + pastaverage);
            eightResalePrice.setText("S$" + averageperyear.get(3));
            sevenResalePrice.setText("S$" + averageperyear.get(2));
            price1.clear();
            averageperyear.clear();
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
            series.setColor(Color.rgb(255,205,210));
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
            graph.getGridLabelRenderer().setNumHorizontalLabels(4);

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
        series.setColor(Color.rgb(255,205,210));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.getDataPointsRadius();
        GridLabelRenderer gridLabel = graph1.getGridLabelRenderer();

        graph1.addSeries(series);
        graph1.setTitle(spinnertext1 + "   " + spinnertext2 + " Sqm");
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
