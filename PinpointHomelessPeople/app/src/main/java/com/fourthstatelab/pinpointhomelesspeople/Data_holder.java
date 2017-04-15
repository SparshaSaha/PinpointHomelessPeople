package com.fourthstatelab.pinpointhomelesspeople;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sparsha on 15/4/17.
 */

public class Data_holder {
    public static List<Homeless> Homeless_list=new ArrayList<>();
    public static List<FoodDistribution> Food_distribution=new ArrayList<>();
}

class Login_credentials
{
    String email_id;
    String password;
}

class Location_Data
{
    public double latitude;
    public double longitude;
}

class Homeless
{
    public String name;
    public String gender;
    public int age;
    public String other;
    public Location_Data loc_data;
    public String tagged_by;
    public int downvotes;
    public int upvotes;
    public String pic_data_url;
}

class FoodDistribution
{
    public String name_of_provider;
    public double quantity;
    public String phone_number;
    public String address;
    public int veg_nonveg;
    public Location_Data loc_data;

}