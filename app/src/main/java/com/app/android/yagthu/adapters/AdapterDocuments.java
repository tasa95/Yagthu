package com.app.android.yagthu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.android.yagthu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Object: This adapter displays a list of Documents
 * Used by: FragmentDocumentsList, ...
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class AdapterDocuments extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<HashMap<String, Object>> datas;

    public AdapterDocuments(Context context_, ArrayList<HashMap<String, Object>> array_) {
        this.context = context_;
        this.datas = array_;
    }

    protected class ViewHolder {
        protected TextView titleDocument, dateDocument;
        protected TextView titleChildDocument, dateChildDocument;
        protected ImageView arrowGroup;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_document_parent, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.arrowGroup = (ImageView) view.findViewById(R.id.item_arrow_document);
            viewHolder.titleDocument = (TextView) view.findViewById(R.id.item_title_document);
            viewHolder.dateDocument = (TextView) view.findViewById(R.id.item_date_document);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Set arrow on group
        if (isExpanded) {
            viewHolder.arrowGroup.setImageResource(R.drawable.icon_remove);
        } else {
            viewHolder.arrowGroup.setImageResource(R.drawable.icon_add);
        }

        // Get items in section
        List<String> datasInSection = new ArrayList<String>();
        datasInSection = (ArrayList) datas.get(groupPosition).get("DATAS");

        viewHolder.titleDocument.setText(datas.get(groupPosition).get("SECTION").toString());
        viewHolder.dateDocument
                .setText("Dernière modification: " + datas.get(groupPosition).get("LAST_MODIFIED_DATE").toString()
                        + " (" + datasInSection.size() + " documents)");

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList) datas.get(groupPosition).get("DATAS")).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return datas.get(groupPosition).get("DATAS");
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_document_child, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleChildDocument = (TextView) view.findViewById(R.id.item_title_document);
//            viewHolder.dateChildDocument = (TextView) view.findViewById(R.id.item_date_document);
//            viewHolder.dateChildDocument.setVisibility(View.GONE);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.titleChildDocument.setText(
                ((ArrayList) datas.get(groupPosition).get("DATAS")).get(childPosition).toString());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

