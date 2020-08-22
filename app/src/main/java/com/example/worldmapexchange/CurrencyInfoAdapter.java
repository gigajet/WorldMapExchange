package com.example.worldmapexchange;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.caverock.androidsvg.SVGImageView;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CurrencyInfoAdapter extends ArrayAdapter<CurrencyInfo> {
    public CurrencyInfoAdapter(Context context, ArrayList<CurrencyInfo> currencyInfoArrayList)
    {
        super(context, 0, currencyInfoArrayList);
    }

    private static class ViewHolder
    {
        TextView txtName;
        SVGImageView im;
        TextView resText;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CurrencyInfo currencyInfo = getItem(position);
        ViewHolder viewHolder;



        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(R.layout.item_layout1, parent, false);
            viewHolder.txtName = (TextView)convertView.findViewById(R.id.textViewName);
            viewHolder.im = (SVGImageView)convertView.findViewById(R.id.imageView);
            viewHolder.resText = (TextView)convertView.findViewById(R.id.resText);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(currencyInfo.code + "(" + currencyInfo.name + ")");
        viewHolder.im.setImageAsset("image/"+currencyInfo.src);
        viewHolder.im.setScaleX(1);
        viewHolder.im.setScaleY(1);
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                //TextView cur = (TextView)MainActivity.getInstance().findViewById(R.id.convertedCurrency);
//                String txt = ((TextView)v.findViewById(R.id.textViewName)).getText().toString().substring(0, 3);
//                //cur.setText(txt);
//
//            }
//        });
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.CEILING);
        nf.setGroupingUsed(true);
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumIntegerDigits(56);
        viewHolder.resText.setText(nf.format(currencyInfo.value));
        return convertView;
    }
}
