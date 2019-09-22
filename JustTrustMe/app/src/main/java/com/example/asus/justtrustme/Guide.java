package com.example.asus.justtrustme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
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
    static String start3,start4,dest3,dest4;

    private MapView mMapView;
    LinearLayout mapView_linear;
    private CameraManager mCameraManager;
    private String mCameraId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean inrow = false, inWGS84_LOGT = false, inWGS84_LAT = false;
        String WGS84_LOGT = null, WGS84_LAT = null;


        Intent in = getIntent();
        start3 = in.getStringExtra("start1");
        start4 = in.getStringExtra("start2");
        dest3 = in.getStringExtra("dest1");
        dest4 = in.getStringExtra("dest2");
        Double s3 = Double.parseDouble(start3);
        Double s4 = Double.parseDouble(start4);
        Double d3 = Double.parseDouble(dest3);
        Double d4 = Double.parseDouble(dest4);


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

        MapPolyline polyline1 = new MapPolyline();
        polyline1.setTag(1000);
        polyline1.setLineColor(Color.argb(128, 255, 0, 0));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(s3,s4));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(d3,d4));

        mMapView.addPolyline(polyline1);

        try {
            URL url = new URL("https://openapi.gg.go.kr/CCTV?" + "Key=e53374de6b754ee79694f43f2af5b965" + "&pIndex=&pSize=20"); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("WGS84_LOGT")) { //mapy 만나면 내용을 받을수 있게 하자
                            inWGS84_LOGT = true;
                        }
                        if (parser.getName().equals("WGS84_LAT")) { //mapy 만나면 내용을 받을수 있게 하자
                            inWGS84_LAT = true;
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때

                        if (inWGS84_LOGT) { //isMapy이 true일 때 태그의 내용을 저장.
                            WGS84_LOGT = parser.getText();
                            sublong.add(WGS84_LOGT);
                            inWGS84_LOGT = false;
                        }
                        if (inWGS84_LAT) { //isMapy이 true일 때 태그의 내용을 저장.
                            WGS84_LAT = parser.getText();
                            sublat.add(WGS84_LAT);
                            inWGS84_LAT = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("row")) {
                            inrow = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
        }
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("Custom Marker");
        customMarker.setTag(1);
        for (int i = 0; i < sublat.size(); i++) {
            customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(sublat.get(i)), Double.valueOf(sublong.get(i))));
            customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // CCTV 마커 모양
            customMarker.setCustomImageResourceId(R.drawable.custommarker);
            customMarker.setCustomImageAnchor(0.5f, 1.0f);
            customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mMapView.addPOIItem(customMarker);



            MapPolyline polyline = new MapPolyline();
            polyline.setTag(1000);
            polyline.setLineColor(Color.argb(128, 204, 255, 255));
            for (int j = 0; j < sublat.size(); j++) {
                double x = Double.valueOf(sublat.get(j));
                double y = Double.valueOf(sublong.get(j));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(x, y));
                mMapView.addPolyline(polyline);
                mMapView.fitMapViewAreaToShowPolyline(polyline);
            }

        }



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