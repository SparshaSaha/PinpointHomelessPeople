package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sparsha on 7/3/17.
 */

public class List_Adapters {
}

class Homeless_list extends BaseAdapter{
    List<Homeless> home_list;
    LayoutInflater inflater;
    Context context;

    Homeless_list(Context con,List<Homeless> list)
    {
        home_list=list;
        context=con;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return home_list.size();
    }

    @Override
    public Object getItem(int i) {
        return home_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder
    {
        TextView name,age,others;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myview = inflater.inflate(R.layout.homeless_listview,null);
        Holder holder=new Holder();
        holder.name=(TextView)myview.findViewById(R.id.name);
        holder.age=(TextView)myview.findViewById(R.id.myage);
        holder.others=(TextView)myview.findViewById(R.id.others);

        holder.name.setText(home_list.get(i).name);
        holder.age.setText(home_list.get(i).age+"");
        holder.others.setText(home_list.get(i).other);



        return myview;
    }

    public void notify(List<Homeless> newlist)
    {
        home_list=newlist;
        notifyDataSetChanged();
    }
}



class Food_Distribution_list extends BaseAdapter
{
    List<FoodDistribution> foodlist;
    LayoutInflater layoutInflater;
    Context context;

    Food_Distribution_list(List<FoodDistribution> food_dis,Context con)
    {
        foodlist=food_dis;
        context=con;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Override
    public Object getItem(int i) {
        return foodlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder
    {
        TextView name;
        TextView quantity;
        TextView phone;
        TextView address;
        TextView type;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View myview= layoutInflater.inflate(R.layout.food_dist_listview,null);
        Holder holder=new Holder();
        holder.name= (TextView) myview.findViewById(R.id.name);
        holder.address=(TextView)myview.findViewById(R.id.address);
        holder.phone=(TextView)myview.findViewById(R.id.ph_no);
        holder.quantity=(TextView)myview.findViewById(R.id.quantity);
        holder.type=(TextView)myview.findViewById(R.id.mytype);

        holder.name.setText(foodlist.get(i).name_of_provider);
        holder.quantity.setText(foodlist.get(i).quantity+"");
        holder.address.setText(foodlist.get(i).address);
        holder.phone.setText(foodlist.get(i).phone_number);
        holder.type.setText(foodlist.get(i).veg_nonveg+"");

        return myview;
    }

    public void notify_myfunc(List<FoodDistribution> flist)
    {
        foodlist=flist;
        notifyDataSetChanged();
    }
}
