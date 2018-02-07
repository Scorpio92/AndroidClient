package ru.scorpio92.mpgp.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import ru.scorpio92.mpgp.R;
import ru.scorpio92.mpgp.data.entity.message.api.APIServersList;
import ru.scorpio92.mpgp.data.entity.message.api.APIServersListItem;

/**
 * Created by scorpio92 on 2/7/18.
 */

public class APIServersListAdapter extends BaseAdapter {

    private Context context;
    private APIServersList list;

    public APIServersListAdapter(Context context, APIServersList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return list.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.api_server_row, parent, false);
            holder = new ViewHolder();

            holder.header = rowView.findViewById(R.id.header);
            holder.desc = rowView.findViewById(R.id.desc);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        APIServersListItem listItem = (APIServersListItem) getItem(position);

        holder.header.setText(listItem.getName());
        holder.desc.setText(listItem.getAddress());

        return rowView;
    }

    private static class ViewHolder {
        private AppCompatTextView header, desc;
    }
}
