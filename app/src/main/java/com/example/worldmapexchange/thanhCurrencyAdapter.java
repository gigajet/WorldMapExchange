package com.example.worldmapexchange;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caverock.androidsvg.SVGImageView;

import java.util.List;

public class thanhCurrencyAdapter extends ArrayAdapter<CurrencyInfo> {

    public thanhCurrencyAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyInfo> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        SVGImageView im;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CurrencyInfo currencyInfo = getItem(position);
        thanhCurrencyAdapter.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new thanhCurrencyAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(R.layout.thanh_item_list_view, parent, false);
            viewHolder.im = convertView.findViewById(R.id.svgimageitem);
            viewHolder.name = convertView.findViewById(R.id.nameitem);
            viewHolder.code = convertView.findViewById(R.id.codenameitem);
            //viewHolder.im.setScaleX(1);
            //viewHolder.im.setScaleY(1);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (thanhCurrencyAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.im.setImageAsset(currencyInfo.src);
        viewHolder.code.setText(currencyInfo.code);
        viewHolder.name.setText(currencyInfo.name);

        return convertView;
    }
}
