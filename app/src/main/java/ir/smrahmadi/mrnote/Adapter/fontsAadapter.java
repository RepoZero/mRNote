package ir.smrahmadi.mrnote.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.smrahmadi.mrnote.R;

/**
 * Created by cloner on 8/22/17.
 */

public class fontsAadapter extends BaseAdapter {



    private ArrayList<String> fontsName = new ArrayList<String>();
    private ArrayList<Typeface> fonts = new ArrayList<Typeface>();
    Context context;
    private static LayoutInflater inflater=null;


    public fontsAadapter(Context context, ArrayList<String> fontsName , ArrayList<Typeface> fonts  ) {
        // TODO Auto-generated constructor stub

        context=context;
        this.context = context;
        this.fontsName =fontsName;
        this.fonts=fonts;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return fontsName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_font, null);
        holder.textView=(TextView) rowView.findViewById(R.id.item_font_name);
        holder.textView.setTypeface(fonts.get(position));
        holder.textView.setText(fontsName.get(position));



        return rowView;

    }


}
