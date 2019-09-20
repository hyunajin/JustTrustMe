package com.example.asus.justtrustme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

//즐겨찾기
public class Guide extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private MediaPlayer mp;
    Button Siren;

    private Button mImageButtonFlash;
    private boolean mFlashOn;

    private MapView mMapView;
    LinearLayout mapView_linear;
    private CameraManager mCameraManager;
    private String mCameraId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StrictMode.enableDefaults();
        MapView mMapView = new MapView(this);
        mMapView.setDaumMapApiKey("059bfe5929581aee44c86a3c7271c7d8");
        mMapView.setMapType(MapView.MapType.Standard);
        ArrayList<String> sublat = new ArrayList<String>();
        ArrayList<String> sublong = new ArrayList<String>();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.321478, 127.097414);
        mMapView.setMapCenterPoint(mapPoint, true);
        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mMapView);


        mp =  MediaPlayer.create(Guide.this, R.raw.siren);
        mp.setLooping( true );
        final Button Siren = (Button)findViewById(R.id.siren_btn);

        Siren.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mp != null) {
                    if (mp.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.seekTo(0);
                        mp.start();
                    }
                }
            }
        });

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(getApplicationContext(),"There is no camera flash. |n The app will finish!",Toast.LENGTH_SHORT).show();

            delayedFinish();
            return;
        }
        mCameraManager =(CameraManager)getSystemService(CAMERA_SERVICE);

        mImageButtonFlash = findViewById(R.id.flash_btn);
        mImageButtonFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashlight();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_BM);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void delayedFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3500);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashlight() {
        if (mCameraId == null){
            try{
                for(String id : mCameraManager.getCameraIdList()){
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if(flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                        mCameraId = id;
                        break;
                    }
                }
            }catch (CameraAccessException e){
                mCameraId = null;
                e.printStackTrace();
                return;
            }
        }
        mFlashOn = !mFlashOn;

        try{
            mCameraManager.setTorchMode(mCameraId, mFlashOn);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.other_settings:
                Intent intent1 = new Intent(Guide.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(Guide.this, Logout.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_finding_a_way){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Guide.this, FindingWay.class);
            startActivity(intent);
        } else if (id == R.id.nav_book_mark){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Guide.this, BookMark.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Guide.this, RegisterContacts.class);
            startActivity(intent);
        }else if (id == R.id.nav_notice){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Guide.this, Notice.class);
            startActivity(intent);
        }else if (id == R.id.nav_faq){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Guide.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }
}