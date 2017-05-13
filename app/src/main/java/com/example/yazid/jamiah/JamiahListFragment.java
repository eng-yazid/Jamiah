package com.example.yazid.jamiah;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yazid.jamiah.model.Jamiah;

import java.util.ArrayList;

public class JamiahListFragment extends ListFragment {

    public static final String LOG_TAG = JamiahListFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Jamiah j;
        final ArrayList<Jamiah> jamiahs = new ArrayList<Jamiah>();
        //j = (Jamiah) getIntent().getSerializableExtra("jamiah");
        //jamiahs.add(j);
      //  ListView JamiahListView = (ListView) findViewById(R.id.list);
        //final JamiahArrayAdapter adapter = new JamiahArrayAdapter(this, jamiahs);
      //  JamiahListView.setAdapter(adapter);
      // View root=
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
