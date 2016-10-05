package com.app.android.yagthu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.models.Attendance;

import java.util.ArrayList;

/**
 * Object: This adapter displays a list of Attendance's student
 * Used by: FragmentProfile
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class AdapterAttendanceScore extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Attendance> datas;

    public AdapterAttendanceScore(Context context_, ArrayList<Attendance> array_) {
        this.context = context_;
        this.datas = array_;
    }

    protected class ViewHolder {
        protected LinearLayout container;
        protected TextView subject, trainer, coef,
                etcs, cc1, cc2, test;
        protected TextView date, type, justified;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Attendance getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Attendance attendance = datas.get(position);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_attendance, parent, false);

            viewHolder.container = (LinearLayout) view.findViewById(R.id.item_container_attendance);
            viewHolder.date = (TextView) view.findViewById(R.id.item_date_attendance);
            viewHolder.subject = (TextView) view.findViewById(R.id.item_subject_attendance);
            viewHolder.type = (TextView) view.findViewById(R.id.item_type_attendance);
            viewHolder.justified = (TextView) view.findViewById(R.id.item_jutified_attendance);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (attendance.getCourseName().equalsIgnoreCase("matières")) {
            viewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        } else {
            viewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.background));
        }

        viewHolder.container = (LinearLayout) view.findViewById(R.id.item_container_attendance);
        viewHolder.date.setText(attendance.getDateStart());
        viewHolder.subject.setText(attendance.getCourseName());
        viewHolder.type.setText(attendance.getType());
        viewHolder.justified.setText(attendance.getJustified());

        return view;
    }

}
