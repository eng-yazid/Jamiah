package com.example.yazid.jamiah;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yazid.jamiah.model.Jamiah;

import java.util.List;

/**
 * Created by yazid on 4/3/17.
 */

public class JamiahArrayAdapter extends ArrayAdapter<Jamiah>
{
    //private Jamiah jamiah;
    public JamiahArrayAdapter(Context context, List<Jamiah> objectsOfJamiah) {
        super(context, 0,objectsOfJamiah);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.jamiah_list_item, parent, false);
        }


        Jamiah currentJamiah = getItem(position);

        TextView amountTV = (TextView) listItemView.findViewById(R.id.amount_list_item);
        TextView startDateTV = (TextView) listItemView.findViewById(R.id.sd_list_item);
        TextView endDateTV = (TextView) listItemView.findViewById(R.id.ed_list_item);
        TextView remMonths = (TextView) listItemView.findViewById(R.id.remain_months_list_item);
        TextView personsTV = (TextView) listItemView.findViewById(R.id.persons_list_item);

        amountTV.setText(" "+currentJamiah.getAmount());
        startDateTV.setText(" "+currentJamiah.getStartDate().toString());
        endDateTV.setText(" "+currentJamiah.getEndDate().toString());
        remMonths.setText(" "+currentJamiah.getMonths());
        personsTV.setText(" "+currentJamiah.getNumberOfPersons());

        return listItemView;

    }


}
