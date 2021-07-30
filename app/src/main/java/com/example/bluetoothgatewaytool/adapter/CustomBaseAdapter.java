package com.example.bluetoothgatewaytool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.model.BaseModel;

import java.util.List;

/**
 * @author 章可政
 * @date 2021/7/21 1:26
 */
public  class CustomBaseAdapter<T extends BaseModel> extends BaseAdapter {
    protected int index=-1;
    protected LayoutInflater inflater;
    protected List<T> data;
    public CustomBaseAdapter(Context context, List<T> data) {
        this.data=data;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (data==null)return 0;
        return data.size();
    }

    @Override
    public T getItem(int position) {
        if (data==null)return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        final TextView scene_name;
        final ImageView dots;
        view = inflater.inflate(R.layout.scene_list_itme, parent, false);
        scene_name=view.findViewById(R.id.scene_name);
        dots=view.findViewById(R.id.dots);
        if (position==index){
            dots.setVisibility(View.VISIBLE);
            scene_name.setTextColor(Color.parseColor("#ff3993cf"));
        }else {
            dots.setVisibility(View.GONE);
        }
        T t = getItem(position);
        scene_name.setText(t.getName());
        return view;
    }
}
