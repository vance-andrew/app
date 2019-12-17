package com.app.virtualcampus;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.app.virtualcampus.Helper.AppConstants;
import com.app.virtualcampus.Helper.DBController;
import com.app.virtualcampus.Model.Building;
import com.app.virtualcampus.Model.Faculty;
import com.app.virtualcampus.Model.mClass;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    DBController mDB;
    ArrayList<Building> buldingsList = new ArrayList<>();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mDB = new DBController(getApplicationContext());

        boolean isDataAdded = prefs.getBoolean("isDataAdded" , false);
        if(!isDataAdded)
        {
            addDBdata();
            prefs.edit().putBoolean("isDataAdded" , true).commit();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        addBuildingMarkers();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i(AppConstants.TAG , "onMarkerClick: " + marker.getTitle() + "  tag: " + marker.getTag());

        Building clickedBuilding = buldingsList.get(Integer.parseInt(marker.getTag().toString()));
        Log.w(AppConstants.TAG , "clickedBuilding: " + clickedBuilding.getPic());

        Intent i = new Intent(MapsActivity.this , DetailActivity.class);
        i.putExtra("build_id" , clickedBuilding.getID());
        i.putExtra("build_name" , clickedBuilding.getName());
        i.putExtra("pic_name" , clickedBuilding.getPic());
        startActivity(i);
        return false;
    }


    public void addBuildingMarkers()
    {
        ArrayList<Marker> markers = new ArrayList<>();
        Cursor mCursor = mDB.getAllData(DBController.DB_TABLE_BUILDING);
        if(mCursor.getCount() > 0) {
            if (mCursor.moveToFirst())
            {
                do {
                    String BUILDING_ID = mCursor.getString(mCursor.getColumnIndex(DBController.BUILDING_ID));
                    String NAME = mCursor.getString(mCursor.getColumnIndex(DBController.NAME));
                    String LAT = mCursor.getString(mCursor.getColumnIndex(DBController.LAT));
                    String LNG = mCursor.getString(mCursor.getColumnIndex(DBController.LNG));
                    String PIC = mCursor.getString(mCursor.getColumnIndex(DBController.PIC));

                    Log.i(AppConstants.TAG, "NAME: " + NAME);

                    Building build  = new Building(BUILDING_ID , NAME , LAT , LNG , PIC);
                    buldingsList.add(build);
                } while (mCursor.moveToNext());

                for(int i=0; i < buldingsList.size(); i++)
                {
                    Building b1 = buldingsList.get(i);
                    LatLng bulding = new LatLng(Double.parseDouble(b1.getLat()), Double.parseDouble(b1.getLng()));
                    Marker mMarker = mMap.addMarker(new MarkerOptions().position(bulding).title(b1.getName()));
                    mMarker.setTag(i);
                    mMarker.showInfoWindow();
                    markers.add(mMarker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(b1.getLat()), Double.parseDouble(b1.getLng())),16));

                }
            }
        }
    }

    public void addDBdata()
    {
        Building building1 = new Building("1" , "BH Snell" , "44.6625462" , "-74.9988307" , "Snell Hall.png");
        Building building2 = new Building("2" , "Science Center" , "44.6635541" , "-75.0000177" , "Science Center.png");

        mDB.addBuilding(building1);
        mDB.addBuilding(building2);

        mClass c1 = new mClass("1" , "MTH201" , "10:00 AM" , "108" , "1st" , "1" , "(Inside Hallway) Snell Hall.jpg");
        mClass c2 = new mClass("2" , "CS201" , "11:00 AM" , "215" , "2nd" , "1" , "(Inside Hallway) Snell Hall.jpg");
        mClass c3 = new mClass("3" , "DLD301" , "12:30 AM" , "320" , "3rd" , "1" , "(Inside Hallway) Snell Hall.jpg");
        mClass c4 = new mClass("4" , "MTH201" , "110:00 AM" , "110" , "1st" , "2" , "(Inside Class) Science Center.jpg");
        mClass c5 = new mClass("5" , "CS201" , "11:00 AM" , "212" , "2nd" , "2" , "(Inside Class) Science Center.jpg");
        mClass c6 = new mClass("6" , "DLD301" , "10:30 AM" , "325" , "3rd" , "2" , "(Inside Class) Science Center.jpg");

        mDB.addClass(c1);
        mDB.addClass(c2);
        mDB.addClass(c3);
        mDB.addClass(c4);
        mDB.addClass(c5);
        mDB.addClass(c6);

        Faculty f1 = new Faculty("1" , "Allan" , "Professor" , "CS" , "CS301" , "201" , "2nd" , "1" , "Prof. Allen.jpg");
        Faculty f2 = new Faculty("2" , "Donald" , "Professor" , "IT" , "IT302" , "110" , "1st" , "2" , "Prof. Donald.jpg");
        Faculty f3 = new Faculty("3" , "Smith" , "Professor" , "SE" , "SE303" , "325" , "3rd" , "2" , "Prof. Smith.jpg");
        Faculty f4 = new Faculty("4" , "Clark" , "Professor" , "CS" , "CS304" , "109" , "1st" , "1" , "Prof. Clark.jpg");
        Faculty f5 = new Faculty("5" , "Mark" , "Professor" , "IT" , "IT305" , "214" , "2nd" , "2" , "Prof. Mark.jpg");

        mDB.addFaculty(f1);
        mDB.addFaculty(f2);
        mDB.addFaculty(f3);
        mDB.addFaculty(f4);
        mDB.addFaculty(f5);

    }


}
