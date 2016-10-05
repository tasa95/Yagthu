package com.app.android.yagthu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.models.Grades;

import java.util.ArrayList;

/**
 * Object: This adapter displays a list of Score's student
 * Used by: FragmentProfile
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class AdapterSemesterScore extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Grades> datas;

    public AdapterSemesterScore(Context context_, ArrayList<Grades> array_) {
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
    public Grades getItem(int position) {
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
        Grades grade = datas.get(position);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_score, parent, false);

            viewHolder.container = (LinearLayout) view.findViewById(R.id.item_container_score);
            viewHolder.subject = (TextView) view.findViewById(R.id.item_subject_score);
            viewHolder.trainer = (TextView) view.findViewById(R.id.item_trainer_score);
            viewHolder.coef = (TextView) view.findViewById(R.id.item_coefficient_score);
            viewHolder.etcs = (TextView) view.findViewById(R.id.item_etcs_score);
            viewHolder.cc1 = (TextView) view.findViewById(R.id.item_cc_one_score);
            viewHolder.cc2 = (TextView) view.findViewById(R.id.item_cc_two_score);
            viewHolder.test = (TextView) view.findViewById(R.id.item_test_score);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (grade.getName().equalsIgnoreCase("matières")) {
            viewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
            viewHolder.trainer.setVisibility(View.GONE);
        } else {
            viewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.background));
            viewHolder.trainer.setVisibility(View.VISIBLE);
            viewHolder.trainer.setText(grade.getProfessorName());
        }

        viewHolder.subject.setText(grade.getName());
        viewHolder.coef.setText(grade.getCoef());
        viewHolder.etcs.setText(grade.getEcts());
        viewHolder.cc1.setText(grade.getCc1());
        viewHolder.cc2.setText(grade.getCc2());
        viewHolder.test.setText(grade.getTest());

        return view;
    }

}
