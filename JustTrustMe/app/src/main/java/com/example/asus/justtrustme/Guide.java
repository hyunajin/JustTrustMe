package com.example.asus.justtrustme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//즐겨찾기
public class Guide extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private MediaPlayer mp;
    Button Siren;

    private Button mImageButtonFlash;
    private boolean mFlashOn;

    private CameraManager mCameraManager;
    private String mCameraId;
    ImageButton bm_btn;
    static String getLoginMemNo="1",getBookmark_start_addr,getBookmark_destination_addr;          //로그인한 사람의 번호 저장 필요!!
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        /*final ImageButton Bookmark = (ImageButton)findViewById(R.id.bm_btn);
        Bookmark.setOnClickListener(new View.OnClickListener(){

        });*/
        Intent intent = getIntent();
        getBookmark_start_addr = intent.getStringExtra("bookmark_start_addr");
        getBookmark_destination_addr = intent.getStringExtra("bookmark_destination_addr");

        mp =  MediaPlayer.create(Guide.this, R.raw.siren);
        mp.setLooping( true );
        final Button Siren = (Button)findViewById(R.id.siren_btn);
        bm_btn = findViewById(R.id.bm_btn);
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

        bm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bm_btn.setImageResource(R.drawable.likey);
                //여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!즐겨찾기 insert 수정중!!!
                dataBookmarkAppend();
            }
        });
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
            Intent intent = new Intent(Guide.this, Bookmark.class);
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


    public void dataBookmarkAppend(){
        String bookmark_start_lat = "126.733500";     //임의의 값 지정했음
        String bookmark_start_lon = "37.340389";     //임의의 값 지정했음
        String bookmark_destination_lat = "126.7331214";     //임의의 값 지정했음
        String bookmark_destination_lon = "37.3420975";     //임의의 값 지정했음
        String bookmark_start_addr = getBookmark_start_addr;     //임의의 값 지정했음
        String bookmark_destination_addr = getBookmark_destination_addr;     //임의의 값 지정했음
        String mem_no = getLoginMemNo;

        if(bookmark_start_lat!=null && bookmark_start_lat.trim().length()!=0 && bookmark_start_lon!=null
                && bookmark_start_lon.trim().length()!=0 && bookmark_destination_lat!=null
                && bookmark_destination_lat.trim().length()!=0 && bookmark_destination_lon!=null
                && bookmark_destination_lon.trim().length()!=0 && bookmark_start_addr!=null
                && bookmark_start_addr.trim().length()!=0&& bookmark_destination_addr!=null
                && bookmark_destination_addr.trim().length()!=0 && mem_no!=null && mem_no.trim().length()!=0) {
            new Guide.InsertTask().execute(bookmark_start_lat.trim(),bookmark_start_lon.trim(),bookmark_destination_lat.trim(),bookmark_destination_lon.trim(),bookmark_start_addr.trim(),bookmark_destination_addr.trim(),mem_no.trim());
            Log.d("InsertDB", "저장호출");
        }else{
            Log.d("InsertDB","저장호출못함");
        }
    }
    class InsertTask extends AsyncTask<String,String,String> {
        public String ip = getResources().getString(R.string.ip_address);
        String serverIp = "http://" + ip + "/justtrustmeDB/insert/insertBookmark.jsp";

        @Override
        protected String doInBackground(String... params) {
            String bookmark_start_lat = params[0];
            String bookmark_start_lon = params[1];
            String bookmark_destination_lat = params[2];
            String bookmark_destination_lon = params[3];
            String bookmark_start_addr = params[4];
            String bookmark_destination_addr = params[5];

            try {
                String data  = "bookmark_start_lat=" + URLEncoder.encode(bookmark_start_lat, "UTF-8");
                data += "&bookmark_start_lon" + "=" + URLEncoder.encode(bookmark_start_lon, "UTF-8");
                data += "&bookmark_destination_lat" + "=" + URLEncoder.encode(bookmark_destination_lat, "UTF-8");
                data += "&bookmark_destination_lon" + "=" + URLEncoder.encode(bookmark_destination_lon, "UTF-8");
                data += "&bookmark_start_addr" + "=" + URLEncoder.encode(bookmark_start_addr, "UTF-8");
                data += "&bookmark_destination_addr" + "=" + URLEncoder.encode(bookmark_destination_addr, "UTF-8");
                data += "&mem_no" + "=" + URLEncoder.encode(getLoginMemNo, "UTF-8");

                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null){
                    sb.append(line);
                    break;
                }
                Log.d("InsertTask","여기~~~~~~~~~~ 저장완료");
                Log.d("Data",data);
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Log.d("InsertTask","후처리 ---------------------");
        }
    }
}