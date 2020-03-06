package e.j_enn.repa.boundary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import e.j_enn.repa.R;
import e.j_enn.repa.control.SearchDBManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    Toolbar toolbar;
    ImageView advancedBtn;
    String email;
    ImageView searchButton;
    EditText searchField;
    private SearchDBManager databaseHelper;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Browse");
        email = getArguments().getString("email");
        initObject();

    }

    private void initObject() {
        databaseHelper = new SearchDBManager(getActivity());
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //get the button view
        advancedBtn = (ImageView) getView().findViewById(R.id.advancedsearchbtn);
        //set a onclick listener for when the button gets clicked
        advancedBtn.setOnClickListener(new OnClickListener() {
            //start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), AdvancedSearchActivity.class).putExtra("email", email);
                startActivity(mainIntent);
            }
        });

        searchField = (EditText) getView().findViewById(R.id.searchBarTextField);
        searchButton = (ImageView) getView().findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), searchField.getText().toString(), Toast.LENGTH_LONG).show();
                if(databaseHelper.checkSearchProperty(searchField.getText().toString())){
                    //Toast.makeText(getActivity(), "Found", Toast.LENGTH_LONG).show();
                    String text = searchField.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "browse");
                    bundle.putString("search", text);

                    startActivity(new Intent(getActivity(), ResultActivity.class).putExtra("email", email).putExtras(bundle));
                } else {
                    startActivity(new Intent(getActivity(), NoResultFound.class));
                }
            }
        });
    }
}
