package com.fourthstatelab.pinpointhomelesspeople;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import com.fourthstatelab.pinpointhomelesspeople.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fourthstatelab.pinpointhomelesspeople.Utility.fredoka;
import static java.security.AccessController.getContext;

public class NavigationCentre extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static Context con;
    static Activity activity;
    static int off = 0;

    TextView appName;
    FloatingActionButton fb_ViewMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_centre);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        con = this;
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Utility.setStatusBar(getWindow(), con);
        appName = (TextView) findViewById(R.id.Home_appName);
        appName.setTypeface(fredoka);
        fb_ViewMap = (FloatingActionButton) findViewById(R.id.viewMap);
        fb_ViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = tabLayout.getSelectedTabPosition();
                switch (selection) {
                    case 0:
                        startActivity(new Intent(con, ViewMap.class));
                        break;
                    case 1:
                        startActivity(new Intent(con, ViewMapFoodWastage.class));
                        break;
                }
            }
        });

        try {
            off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (off == 0) {
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__centre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_Share) {
            PackageManager pm = getPackageManager();
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "https://play.google.com/store/apps/details?id=com.fourthstatelabs.zchedule&hl=en";

                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (Exception e) {
                Toast.makeText(con, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public LocationManager loc_manager;
        public Location best_known_loc = null;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation_centre, container, false);


            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_navigation_centre, container, false);
                    final FloatingActionButton fabutton = (FloatingActionButton) rootView.findViewById(R.id.fab);
                    ListView food_donate_view = (ListView) rootView.findViewById(R.id.listview);

                    food_donate_view.setOnDetectScrollListener(new OnDetectScrollListener() {
                        @Override
                        public void onUpScrolling() {
                            fabutton.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onDownScrolling() {
                            fabutton.setVisibility(View.GONE);
                        }
                    });

                    fabutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(con, My_Map.class);
                            intent.putExtra("type", "food donate");
                            con.startActivity(intent);
                            activity.finish();
                        }
                    });


                    DatabaseReference dataref;
                    dataref = FirebaseDatabase.getInstance().getReference();
                    Food_Distribution_list food_dist_list = new Food_Distribution_list(Data_holder.Food_distribution, con);
                    food_donate_view.setAdapter(food_dist_list);
                    get_food_list(dataref, food_dist_list);


                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_navigation_centre, container, false);
                    ListView homelessView = (ListView) rootView.findViewById(R.id.listview);
                    final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
                    homelessView.setOnDetectScrollListener(new OnDetectScrollListener() {
                        @Override
                        public void onUpScrolling() {

                            fab.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onDownScrolling() {
                            fab.setVisibility(View.GONE);
                        }
                    });
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(con, My_Map.class);
                            intent.putExtra("type", "homeless");
                            con.startActivity(intent);
                            activity.finish();
                        }
                    });

                    DatabaseReference data_ref;
                    data_ref = FirebaseDatabase.getInstance().getReference();
                    Homeless_list home_list = new Homeless_list(con, Data_holder.Homeless_list);
                    homelessView.setAdapter(home_list);

                    get_homeless_list(data_ref, home_list);
                    break;


            }

            return rootView;
        }

        public void get_homeless_list(DatabaseReference data_ref, final Homeless_list homelessView) {
            LocationManager locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
            final Location myloc;
            if (ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            try {
                off = Settings.Secure.getInt(con.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            if (off != 0) {
                myloc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                data_ref.child("Homeless").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Data_holder.Homeless_list = new ArrayList<Homeless>();
                        homelessView.notify(Data_holder.Homeless_list);

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Location_Data location_data = new Location_Data();
                            location_data = data.child("loc_data").getValue(Location_Data.class);
                            Location homeless_loc = new Location("homeless_loc");
                            homeless_loc.setLatitude(location_data.latitude);
                            homeless_loc.setLongitude(location_data.longitude);

                            float distance = homeless_loc.distanceTo(myloc) / 1000;
                            if (distance < 10.0) {


                                Homeless homeless = new Homeless();
                                homeless.name = data.child("name").getValue(String.class);
                                homeless.downvotes = data.child("downvotes").getValue(Integer.class);
                                homeless.upvotes = data.child("upvotes").getValue(Integer.class);
                                homeless.age = data.child("age").getValue(Integer.class);
                                homeless.tagged_by = data.child("tagged_by").getValue(String.class);
                                homeless.other = data.child("other").getValue(String.class);
                                homeless.gender = data.child("gender").getValue(String.class);
                                homeless.loc_data = data.child("loc_data").getValue(Location_Data.class);
                                homeless.id=data.child("id").getValue(Long.class);
                                Data_holder.Homeless_list.add(homeless);

                            }


                        }
                        Collections.reverse(Data_holder.Homeless_list);
                        homelessView.notify(Data_holder.Homeless_list);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(con, "Please turn on Location", Toast.LENGTH_SHORT).show();
            }
        }


        public void get_food_list(DatabaseReference dataref, final Food_Distribution_list foodview) {

            dataref.child("FoodDonate").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Data_holder.Food_distribution = new ArrayList<FoodDistribution>();
                    foodview.notify_myfunc(Data_holder.Food_distribution);
                    LocationManager locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);


                    try {
                        off = Settings.Secure.getInt(con.getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (off != 0) {
                        if (ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location myloc;
                        myloc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            FoodDistribution food_dist = new FoodDistribution();
                            Location_Data locdata=data.child("loc_data").getValue(Location_Data.class);
                            Location food_loc=new Location("food_loc");
                            food_loc.setLatitude(locdata.latitude);
                            food_loc.setLongitude(locdata.longitude);
                            float distance=food_loc.distanceTo(myloc)/1000;



                            if(distance<10.0) {
                                food_dist.veg_nonveg = data.child("veg_nonveg").getValue(Integer.class);
                                food_dist.name_of_provider = data.child("name_of_provider").getValue(String.class);
                                food_dist.address = data.child("address").getValue(String.class);
                                food_dist.quantity = data.child("quantity").getValue(Integer.class);
                                food_dist.phone_number = data.child("phone_number").getValue(String.class);
                                food_dist.loc_data = data.child("loc_data").getValue(Location_Data.class);
                                Data_holder.Food_distribution.add(food_dist);
                            }

                        }
                        Collections.reverse(Data_holder.Food_distribution);
                        foodview.notify_myfunc(Data_holder.Food_distribution);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Help Me";
                case 1:
                    return "Feed Me";

            }
            return null;
        }
    }
}
