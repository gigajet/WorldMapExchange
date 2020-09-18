package com.example.worldmapexchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caverock.androidsvg.SVGImageView;

import java.lang.annotation.Target;
import java.util.List;

public class TargetAdapter extends ArrayAdapter<AllObject> {

    private static class ViewHolder
    {
        TextView name;
        TextView code;
    }

    public TargetAdapter(@NonNull Context context, int resource, @NonNull List<AllObject> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AllObject target=getItem(position);
        TargetAdapter.ViewHolder viewHolder;

        if (convertView==null) {
            viewHolder = new TargetAdapter.ViewHolder();
            convertView= LayoutInflater.from(this.getContext()).inflate(R.layout.target_item_list_view, parent, false);
            viewHolder.name = convertView.findViewById(R.id.nameitem);
            viewHolder.code = convertView.findViewById(R.id.codenameitem);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(TargetAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(target.name);
        viewHolder.code.setText(target.code);
        return convertView;
    }
}
