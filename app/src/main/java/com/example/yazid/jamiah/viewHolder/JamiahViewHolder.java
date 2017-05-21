package com.example.yazid.jamiah.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yazid.jamiah.R;
import com.example.yazid.jamiah.model.Jamiah;

import az.plainpie.PieView;

/**
 * Created by yazid on 5/16/17.
 */

public class JamiahViewHolder extends RecyclerView.ViewHolder {

    private TextView amountTV;
    private TextView startDataTV;
    private TextView endDateTV;
    private TextView remainingMonths;
    private PieView pieView;
    public JamiahViewHolder(View itemView) {
        super(itemView);
       // amountTV = (TextView) itemView.findViewById(R.id.jam_amount);
        startDataTV = (TextView) itemView.findViewById(R.id.jam_start_date);
        endDateTV = (TextView) itemView.findViewById(R.id.jam_end_date);
        remainingMonths = (TextView) itemView.findViewById(R.id.jam_remaining_months);
        pieView = (PieView) itemView.findViewById(R.id.pieView);
        pieView.setPercentageBackgroundColor(R.color.percentageTextColor);

    }

    public void bindToPost(Jamiah jamiah) {

        pieView.setInnerText(jamiah.getAmount()+"");
//        PieView.resolveSize(jamiah.getAmount())

        // amountTV.setText(jamiah.getAmount()+"-");
        String sDate = jamiah.getStartDate();
        String eDate = jamiah.getEndDate();
        startDataTV.setText(sDate);
        endDateTV.setText(eDate);
        remainingMonths.setText(jamiah.getMonths()+"");
    }


}
