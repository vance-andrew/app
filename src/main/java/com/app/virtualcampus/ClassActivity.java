package com.app.virtualcampus;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.virtualcampus.Helper.AppConstants;

import java.io.IOException;
import java.io.InputStream;

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        String CLASS_ID = getIntent().getStringExtra("CLASS_ID");
        String NAME = getIntent().getStringExtra("NAME");
        String PIC = getIntent().getStringExtra("PIC");
        String BUILDING_ID = getIntent().getStringExtra("BUILDING_ID");
        String TIME = getIntent().getStringExtra("TIME");
        String FLOOR = getIntent().getStringExtra("FLOOR");
        String ROOM = getIntent().getStringExtra("ROOM");

        Log.d(AppConstants.TAG , "NAME " + NAME + " PIC: " + PIC);

        RelativeLayout main_view = findViewById(R.id.main_view);
        loadImage(PIC , main_view);

        TextView name = findViewById(R.id.name);
        TextView time = findViewById(R.id.time);
        TextView room = findViewById(R.id.room);
        TextView floor = findViewById(R.id.floor);
        TextView building = findViewById(R.id.building);

        name.setText(NAME);
        time.setText("Time: " + TIME);
        room.setText("Room: " + ROOM);
        floor.setText(FLOOR + " Floor");
        building.setText("Building: " + BUILDING_ID);
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
}
