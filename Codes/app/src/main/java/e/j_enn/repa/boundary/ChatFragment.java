package e.j_enn.repa.boundary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import e.j_enn.repa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    Toolbar toolbar;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Chats");
       // initView();

    }


}
