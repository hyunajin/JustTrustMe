package com.example.asus.justtrustme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class ParentAdapter extends BaseExpandableListAdapter {

    private static final int PARENT_ROW = R.layout.activity_parent_row;
    private static final int CHILD_ROW = R.layout.activity_child;
    private Context context;
    private Vector<ParentData> data;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public ParentAdapter(Context context, Vector<ParentData> data){
        this.data = data;
        this.context = context;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public View getGroupView(int groupPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.activity_parent_row, parent, false);
            viewHolder.title = (TextView) v.findViewById(R.id.faq_title);
            viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            viewHolder.iv_image.setBackgroundResource(R.drawable.sort_down);
        }else{
            viewHolder.iv_image.setBackgroundResource(R.drawable.sort_up);
        }

        viewHolder.title.setText(data.get(groupPosition).getTitle());

        return v;

    }



    @Override

    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.activity_child, null);
            viewHolder.content = (TextView) v.findViewById(R.id.faq_content);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.content.setText(data.get(groupPosition).child.get(childPosition).getContent());

        return v;

    }

    @Override

    public int getGroupCount() {

        return data.size();

    }



    @Override

    public int getChildrenCount(int groupPosition) {

        return data.get(groupPosition).child.size();

    }



    @Override

    public Object getGroup(int groupPosition) {

        return data.get(groupPosition);

    }



    @Override

    public Object getChild(int groupPosition, int childPosition) {

        return data.get(groupPosition).child.get(childPosition);

    }



    @Override

    public long getGroupId(int groupPosition) {

        return groupPosition;

    }



    @Override

    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;

    }



    @Override

    public boolean hasStableIds() {

        return true;

    }



    @Override

    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;

    }

    class ViewHolder {
        public ImageView iv_image;
        public TextView title;
        public TextView content;
    }
}
