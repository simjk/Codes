package e.j_enn.repa.boundary;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import e.j_enn.repa.R;
import e.j_enn.repa.control.PropertyRecyclerAdapter;
import e.j_enn.repa.control.SearchDBManager;
import e.j_enn.repa.entity.Property;


/**
 * A simple {@link Fragment} subclass.
 */

//Search Result Found
public class ResultFoundFragment extends Fragment {

    Toolbar toolbar;
    private List<Property> listProperty;
    private SearchDBManager databaseHelper;
    private PropertyRecyclerAdapter propertyRecyclerAdapter;
    String housing_Unit;
    String address;
    String property_Type;
    String sell_Price;
    String text;
    private RecyclerView recyclerViewUsers;


    public ResultFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_found, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Browse");

        //Toast.makeText(getActivity(), getArguments().getString("from"), Toast.LENGTH_SHORT).show();
        if (getArguments().getString("from").equalsIgnoreCase("browse")) {
            text = getArguments().getString("search");
        } else {
            housing_Unit = getArguments().getString("housing_Unit");
            address = getArguments().getString("town_Area");
            property_Type = getArguments().getString("property_Type");
            sell_Price = getArguments().getString("sell_Price");
        }

        recyclerViewUsers = (RecyclerView) getActivity().findViewById(R.id.recyclerViewUsers);


        listProperty = new ArrayList<>();
        propertyRecyclerAdapter = new PropertyRecyclerAdapter(listProperty);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(propertyRecyclerAdapter);

        databaseHelper = new SearchDBManager(getActivity());

        getDataFromSQLite();

    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listProperty.clear();
                if (getArguments().getString("from").equalsIgnoreCase("browse")) {
                    listProperty.addAll(databaseHelper.getSearchProperty(text));
                } else
                    listProperty.addAll(databaseHelper.getSelectedProperty(housing_Unit, address, property_Type, sell_Price));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                propertyRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
