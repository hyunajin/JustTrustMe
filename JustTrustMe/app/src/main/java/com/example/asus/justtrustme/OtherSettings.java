package com.example.asus.justtrustme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class OtherSettings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_settings);

        toolbar = findViewById(R.id.toolbar);
        listview = findViewById(R.id.listview_OS);
        listview.setOnItemClickListener(this);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_OS);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            case R.id.logout:
                Intent intent2 = new Intent(OtherSettings.this, Logout.class);
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
            Intent intent = new Intent(OtherSettings.this, BookMark.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(OtherSettings.this, RegisterContacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_finding_a_way) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(OtherSettings.this, FindingWay.class);
            startActivity(intent);
        }else if (id == R.id.nav_notice){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(OtherSettings.this, Notice.class);
            startActivity(intent);
        }else if (id == R.id.nav_faq){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(OtherSettings.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("Other Settings","you Clicked now : " + i);
        if (i==0){
            Toast.makeText(getApplicationContext(),"메시지 내용 수정 눌림",Toast.LENGTH_SHORT).show();
        }else if (i==1){
            Toast.makeText(getApplicationContext(),"롱터치 눌림",Toast.LENGTH_SHORT).show();
        }else if (i==2){
            Toast.makeText(getApplicationContext(),"2019.09",Toast.LENGTH_SHORT).show();
        }
    }
}
