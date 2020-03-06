package e.j_enn.repa.boundary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import e.j_enn.repa.R;

public class FavListFragment extends Fragment{
    Toolbar toolbar;
    private List<Fragment> fragments;
    private ImageView property;
    private ImageView settings;
    //private NavigationView settingTab;
    //private ImageView favListTab;


    private MeFragment meFragment;
    private SettingsFragment settingsFragment;

    public FavListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        meFragment = new MeFragment();
        settingsFragment = new SettingsFragment();
        return inflater.inflate(R.layout.fragment_favlist, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Profile");
        init();

    }

    public void init() {
        meFragment = new MeFragment();
        settingsFragment = new SettingsFragment();
        property = (ImageView) getActivity().findViewById(R.id.propertyImage);
        settings = (ImageView) getActivity().findViewById(R.id.settings);
        property.setClickable(true);
        settings.setClickable(true);

        property.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //startActivity(new Intent(v.getContext(), FavListFragment.class));
                setFragment(meFragment);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setFragment(settingsFragment);
                //return true;

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}