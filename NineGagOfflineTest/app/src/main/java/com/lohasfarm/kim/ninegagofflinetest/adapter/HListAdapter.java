package com.lohasfarm.kim.ninegagofflinetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lohasfarm.kim.ninegagofflinetest.R;
import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;

import java.util.ArrayList;

/**
 * Created by kim on 2015-11-22.
 */
public class HListAdapter extends BaseAdapter{
    Context _context;
    LayoutInflater _inflater;
    ArrayList<Gag> _arrayList;
    int _layout;

    public HListAdapter(Context context, int layout, ArrayList<Gag> arrayList) {
        _context = context;
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        _arrayList = arrayList;
        _layout = layout;
    }

    public void appendToList(ArrayList<Gag> input) {
        _arrayList.addAll(input);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return _arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(_layout, parent, false);
        }

        ImageView gagImage = (ImageView) convertView.findViewById(R.id.horizontal_image);
        TextView gagTitle = (TextView) convertView.findViewById(R.id.horizontal_text);

        switch(_arrayList.get(position).get_image()) {
            case 0:
                gagImage.setImageResource(R.drawable.gag0);
                break;
            case 1:
                gagImage.setImageResource(R.drawable.gag1);
                break;
            case 2:
                gagImage.setImageResource(R.drawable.gag2);
                break;
            case 3:
                gagImage.setImageResource(R.drawable.gag3);
                break;
            case 4:
                gagImage.setImageResource(R.drawable.gag4);
                break;
            case 5:
                gagImage.setImageResource(R.drawable.gag5);
                break;
            case 6:
                gagImage.setImageResource(R.drawable.gag6);
                break;
            case 7:
                gagImage.setImageResource(R.drawable.gag7);
                break;
            case 8:
                gagImage.setImageResource(R.drawable.gag8);
                break;
            case 9:
                gagImage.setImageResource(R.drawable.gag9);
                break;
        }

        gagTitle.setText(((Gag) _arrayList.get(position)).get_title());

        return convertView;
    }
}
