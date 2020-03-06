package e.j_enn.repa.boundary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

import e.j_enn.repa.R;
import e.j_enn.repa.boundary.ChatFragment;
import e.j_enn.repa.boundary.FavListFragment;
import e.j_enn.repa.boundary.SettingsFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    Toolbar toolbar;
    private List<Fragment> fragments;
    private ImageView favList;
    private ImageView settings;
    private ImageView chats;

    //private NavigationView settingTab;
    //private ImageView favListTab;


    private FavListFragment favListFragment;
    private SettingsFragment settingsFragment;
    private ChatFragment chatFragment;

    public MeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        favListFragment = new FavListFragment();
        settingsFragment = new SettingsFragment();
        chatFragment = new ChatFragment();
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Profile");
        init();

    }

    public void init() {
        favListFragment = new FavListFragment();
        settingsFragment = new SettingsFragment();
        favList = (ImageView) getActivity().findViewById(R.id.favouriteListing);
        settings = (ImageView) getActivity().findViewById(R.id.settings);
        favList.setClickable(true);
        settings.setClickable(true);

        favList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //startActivity(new Intent(v.getContext(), FavListFragment.class));
                setFragment(favListFragment);
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
