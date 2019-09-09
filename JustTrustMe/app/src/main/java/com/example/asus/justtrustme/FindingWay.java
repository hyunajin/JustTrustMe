package com.example.asus.justtrustme;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;

//길찾기
public class FindingWay extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MapView.OpenAPIKeyAuthenticationResultListener, MapView.MapViewEventListener, MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    //추가한 부분
    private MapView mMapView;
    private MapPOIItem mCustomMarker;
    ImageButton tracker;
    MapReverseGeoCoder mReverseGeoCoder;
    LinearLayout address_linear, mapView_linear;
    TextView address_textView, starting_point_textView, dest_point_textView;
    Button starting_btn, dest_btn, find_way_btn, convert_btn;
    static String startPoint_latitude, startPoint_longitude, destPoint_latitude, destPoint_longitude;
    static double latitude;
    static double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_way);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_FW);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //추가한 부분
        address_linear = findViewById(R.id.address_linear);
        mapView_linear = findViewById(R.id.map_view);
        address_textView = findViewById(R.id.address_textView);
        starting_point_textView = findViewById(R.id.starting_point_textView);
        dest_point_textView = findViewById(R.id.dest_point_textView);
        starting_btn = findViewById(R.id.starting_point_btn);
        dest_btn = findViewById(R.id.dest_point_btn);
        find_way_btn = findViewById(R.id.find_way_btn);
        convert_btn = findViewById(R.id.convert_btn);
        MapLayout mapLayout = new MapLayout(this);
        mMapView = mapLayout.getMapView();
        mMapView.setDaumMapApiKey("be130753b2beba3d82df356ac0ccfc63");
        mMapView.setOpenAPIKeyAuthenticationResultListener(this);
        mMapView.setMapViewEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapLayout);

        //현재 위치 찾기
        tracker = findViewById(R.id.tracker_imageBtn);
        tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
        });
        //출발지 버튼 -> 마커 주소를 출발지로 설정
        starting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String markerAddress = address_textView.getText().toString();
                if (dest_point_textView.getText().toString().equals(markerAddress)) {
                    dest_point_textView.setText(R.string.destination);
                    dest_point_textView.setTextColor(getResources().getColor(R.color.warm_gray));
                }
                starting_point_textView.setText(markerAddress);
                starting_point_textView.setTextColor(getResources().getColor(R.color.black));
                //마커 찍은 좌표를 출발지 위경도로 설정
                startPoint_latitude = String.valueOf(latitude);
                startPoint_longitude = String.valueOf(longitude);
            }
        });
        //도착지 버튼 -> 마커 주소를 도착지로 설정
        dest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String markerAddress = address_textView.getText().toString();
                if (starting_point_textView.getText().toString().equals(markerAddress)) {
                    starting_point_textView.setText(R.string.starting_point);
                    starting_point_textView.setTextColor(getResources().getColor(R.color.warm_gray));
                }
                dest_point_textView.setText(markerAddress);
                dest_point_textView.setTextColor(getResources().getColor(R.color.black));
                //마커 찍은 좌표를 도착지 위경도로 설정
                destPoint_latitude = String.valueOf(latitude);
                destPoint_longitude = String.valueOf(longitude);

            }
        });
        //장소명으로 주소 검색하기 - 출발지
        starting_point_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(FindingWay.this, Search.class);
                startActivity(searchIntent);
            }
        });

        //장소명으로 주소 검색하기 - 도착지
        dest_point_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(FindingWay.this, Search.class);
                startActivity(searchIntent);
            }
        });
        //출발지와 도착지 모두 지정 후 길찾기 버튼 눌렀을 경우 카카오맵으로 도보 길찾기 진행
        // -> 이 부분을 맵뷰에 알고리즘 적용하여 변경해야 함
        find_way_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getStartingPoint = starting_point_textView.getText().toString();
                String getDestPoint = dest_point_textView.getText().toString();
                if (!getStartingPoint.equals("출발지") && !getDestPoint.equals("도착지")) {
                    String url = "daummaps://route?sp=" + startPoint_latitude + "," + startPoint_longitude + "&ep=" + destPoint_latitude + "," + destPoint_longitude + "&by=FOOT";
                    Intent findWayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(findWayIntent);
                }
            }
        });





        convert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!starting_point_textView.getText().toString().equals("출발지") && !dest_point_textView.getText().toString().equals("도착지")) {
                    String startPoint = starting_point_textView.getText().toString();
                    starting_point_textView.setText(dest_point_textView.getText().toString());
                    dest_point_textView.setText(startPoint);
                } else if (starting_point_textView.getText().toString().equals("출발지") && !dest_point_textView.getText().toString().equals("도착지")) {
                    starting_point_textView.setText(dest_point_textView.getText().toString());
                    starting_point_textView.setTextColor(Color.BLACK);
                    dest_point_textView.setText(R.string.destination);
                    dest_point_textView.setTextColor(Color.GRAY);
                } else if (!starting_point_textView.getText().toString().equals("출발지") && dest_point_textView.getText().toString().equals("도착지")) {
                    dest_point_textView.setText(starting_point_textView.getText().toString());
                    dest_point_textView.setTextColor(Color.BLACK);
                    starting_point_textView.setText(R.string.starting_point);
                    starting_point_textView.setTextColor(Color.GRAY);
                }
            }
        });
    }

    private void createCustomMarker(MapView mapView, MapPoint mapPoint) {
        mCustomMarker = new MapPOIItem();
        String name = "";
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(mapPoint);
        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        mCustomMarker.setCustomImageResourceId(R.drawable.img_marker);
        mCustomMarker.setCustomImageAutoscale(true);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(mCustomMarker);
        mapView.setMapCenterPoint(mapPoint, false);

        mReverseGeoCoder = new MapReverseGeoCoder("be130753b2beba3d82df356ac0ccfc63", mMapView.getMapCenterPoint(), FindingWay.this, FindingWay.this);
        mReverseGeoCoder.startFindingAddress();
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
                Intent intent1 = new Intent(FindingWay.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(FindingWay.this, Logout.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_book_mark) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FindingWay.this, BookMark.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FindingWay.this, RegisterContacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_notice) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FindingWay.this, Notice.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FindingWay.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i("CurrentLocationUpdate", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapCenterPoint.getMapPointGeoCoord();
        Log.i("mapViewCenterPoint", String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        mMapView.removeAllPOIItems();
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

        MapPoint marker_point = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        createCustomMarker(mMapView, marker_point);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i("onMapViewDragStarted", String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i("onMapViewDragStarted", String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i("onMapViewDragEnded", String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        mReverseGeoCoder = new MapReverseGeoCoder("be130753b2beba3d82df356ac0ccfc63", mMapView.getMapCenterPoint(), FindingWay.this, FindingWay.this);
        mReverseGeoCoder.startFindingAddress();
        Log.i("MapViewMoveFinished", String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        latitude = mapPointGeo.latitude;
        longitude = mapPointGeo.longitude;
    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }

    private void onFinishReverseGeoCoding(String result) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 6);
        mapView_linear.setLayoutParams(lp);
        address_linear.setVisibility(View.VISIBLE);
        address_textView.setText(result);
    }
}
