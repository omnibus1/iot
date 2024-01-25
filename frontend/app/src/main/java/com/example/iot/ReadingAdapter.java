package com.example.iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;

import org.w3c.dom.Text;

import java.util.List;

class ReadingAdapter extends BaseAdapter {
    public List<ReadingBasic> readings;
    LayoutInflater inflater;

    ReadingAdapter(List<ReadingBasic> readings, Context ctx){
        this.readings = readings;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return readings.size();
    }

    @Override
    public Object getItem(int position) {
        return readings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = this.inflater.inflate(R.layout.activity_reading_element, null);
        TextView serialNumber = convertView.findViewById(R.id.serial_number);
        TextView reading = convertView.findViewById(R.id.reading);
        TextView lastReadDateTime = convertView.findViewById(R.id.last_reading_datetime);

        serialNumber.setText(this.readings.get(position).getDevice_sn());
        reading.setText(this.readings.get(position).getLast_reading());
        lastReadDateTime.setText(this.readings.get(position).getLast_reading_datetime().substring(0,(this.readings.get(position).getLast_reading_datetime().length()-2)));

        return convertView;
    }
}
