package cn.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Frank on 2016/7/14.
 */
public abstract class LBaseAdapter<E> extends BaseAdapter {

    private Context context;
    private List<E> dataSource = new ArrayList<E>();
    private View convertView;

    public LBaseAdapter(Context context, int layoutId) {
        this.context = context;
    }

    public LBaseAdapter(Context context, List<E> list) {
        this.context = context;
        dataSource = list;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createView();
        }

        bindView(convertView, position, dataSource.get(position));

        return convertView;
    }

    public void setDataSource(List<E> list) {
        dataSource = list;
        notifyDataSetChanged();
    }

    public void addData(E data) {
        this.dataSource.add(data);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        this.dataSource.remove(position);
        notifyDataSetChanged();
    }

    public void removeData(E data) {
        this.dataSource.remove(data);
    }

    protected abstract View createView();

    protected abstract void bindView(View convertView, int position, E data);

}
