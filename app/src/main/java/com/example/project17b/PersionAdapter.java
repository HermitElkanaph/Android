package com.example.project17b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class PersonAdapter extends ArrayAdapter<Person> {
    private int resourceId;
    public PersonAdapter(Context context, int textViewResourceId, List<Person> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    /* public PersonAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> objects){
         super(context,textViewResourceId,objects);
         resourceId=textViewResourceId;
     }*/
    public View getView(int position, View convertView, ViewGroup parent){
        Person person=getItem(position);

        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();

            viewHolder.icon=(TextView) view.findViewById(R.id.phone_name);
            viewHolder.text=(TextView) view.findViewById(R.id.phone_phone);

            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.icon.setText(person.getName());
        viewHolder.text.setText( person.getPhone());

        return  view;
    }
    class ViewHolder{
        TextView text;
        TextView text1;
        TextView icon;


    }
}

