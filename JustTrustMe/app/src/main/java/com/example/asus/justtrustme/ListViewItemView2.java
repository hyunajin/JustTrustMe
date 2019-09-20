package com.example.asus.justtrustme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewItemView2 extends LinearLayout {
    TextView textView;
    TextView textView2;

    public ListViewItemView2(Context context) {
        super(context);
        init(context);
    }

    public ListViewItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item_bookmark, this, true);            //activity_singer_item inflater 시킴

        textView = findViewById(R.id.detail1);
        textView2 = findViewById(R.id.detail2);
    }

    public void setStartAddr(String start_addr) {
        textView.setText(start_addr);
    }

    public void setDestinationAddr(String destination_addr) {
        textView2.setText(destination_addr);
    }
}
