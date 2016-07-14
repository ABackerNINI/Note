package Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abacker.note.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 11059 on 2016/7/13 0013.
 */
public class ListItemAdapter extends BaseAdapter {
    private Cursor cursor = null;
    private LayoutInflater layoutInflater = null;
    private Context context;
    private ArrayList<Object> idlist;

    public ListItemAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        idlist = new ArrayList<Object>();
        layoutInflater = LayoutInflater.from(context);

        if(cursor != null && cursor.getCount()>0) {
            idlist.removeAll(idlist);
            idlist.add(cursor.getString(0));
            while (cursor.moveToNext())
                idlist.add(cursor.getString(0));
        }
    }

    public int getIdByPosition(int position) {
        return Integer.parseInt((String) idlist.get(position));
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_list_item, parent, false);
            System.out.println("绘制"+position);
        }
        cursor.moveToPosition(position);
        ((TextView) convertView.findViewById(R.id.item_title)).setText(cursor.getString(1));
        ((TextView) convertView.findViewById(R.id.item_creation_time)).setText(cursor.getString(2));

        return convertView;
    }

    public void close() {
        cursor.close();
        idlist.clear();
    }
}
