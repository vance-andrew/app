package com.app.virtualcampus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.virtualcampus.Helper.AppConstants;
import com.app.virtualcampus.Helper.DBController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    DBController mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupUI(findViewById(R.id.main_view));

        mDB = new DBController(getApplicationContext());

        String build_id = getIntent().getStringExtra("build_id");
        String build_name = getIntent().getStringExtra("build_name");
        String picName = getIntent().getStringExtra("pic_name");

        Log.v(AppConstants.TAG , "build_id: " + build_id);
        Log.v(AppConstants.TAG , "picName: " + picName);

        RelativeLayout main_view = findViewById(R.id.main_view);
        loadImage(picName , main_view);

        final ArrayList<String> classes = mDB.getclasses(build_id);
        Log.w(AppConstants.TAG , "classes size: " + classes.size());

        TextView tv_buildingName = findViewById(R.id.tv_buildingName);
        tv_buildingName.setText(build_name);

        final EditText search_edittext = findViewById(R.id.search_edittext);

        final LinearLayout btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu menu = new PopupMenu(DetailActivity.this, v);
                for(int i=0 ;i < classes.size(); i++)
                {
                    menu.getMenu().add(classes.get(i).toString());
                }
                menu.show();

            }
        });

        ImageView btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mDB.search(search_edittext.getText().toString());

                if(cursor.getCount() > 0)
                {
                    if(cursor.moveToFirst())
                    {
                        String CLASS_ID = cursor.getString(cursor.getColumnIndex(DBController.CLASS_ID));
                        String NAME = cursor.getString(cursor.getColumnIndex(DBController.NAME));
                        String PIC = cursor.getString(cursor.getColumnIndex(DBController.PIC));
                        String BUILDING_ID = cursor.getString(cursor.getColumnIndex(DBController.BUILDING_ID));
                        String TIME = cursor.getString(cursor.getColumnIndex(DBController.TIME));
                        String FLOOR = cursor.getString(cursor.getColumnIndex(DBController.FLOOR));
                        String ROOM = cursor.getString(cursor.getColumnIndex(DBController.ROOM));

                        Log.w(AppConstants.TAG , "NAME: " + NAME + "  BUILDING_ID: " + BUILDING_ID);

                        Intent i = new Intent(DetailActivity.this , ClassActivity.class);
                        i.putExtra("CLASS_ID" , CLASS_ID);
                        i.putExtra("NAME" , NAME);
                        i.putExtra("PIC" , PIC);
                        i.putExtra("BUILDING_ID" , BUILDING_ID);
                        i.putExtra("TIME" , TIME);
                        i.putExtra("FLOOR" , FLOOR);
                        i.putExtra("ROOM" , ROOM);
                        startActivity(i);
                    }
                }
            }
        });

    }

    private void loadImage(String name , RelativeLayout main_view)
    {
        try {
            // get input stream
            InputStream ims = getAssets().open(name);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                main_view.setBackgroundDrawable(d);
            } else {
                main_view.setBackground(d);
            }

        }
        catch(IOException ex) {
            return;
        }
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(DetailActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){}

    }
}
