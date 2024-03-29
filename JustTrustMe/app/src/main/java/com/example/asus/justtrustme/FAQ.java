package com.example.asus.justtrustme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.Vector;

public class FAQ extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private ExpandableListView parentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_faq);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Vector<ParentData> data = new Vector<>();

        parentListView = (ExpandableListView) findViewById(R.id.parentListView);

        ParentData parentData1 = new ParentData("FAQ1");

        parentData1.child.add(new Child("안녕하세요 공지사항입니다."));

        data.add(parentData1);

        ParentData parentData2 = new ParentData("FAQ2");

        parentData2.child.add(new Child("호엥"));

        data.add(parentData2);

        ParentAdapter adapter = new ParentAdapter(this, data);

        parentListView.setAdapter(adapter);

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
                Intent intent1 = new Intent(FAQ.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(FAQ.this, Logout.class);
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
            Intent intent = new Intent(FAQ.this, FindingWay.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FAQ.this, RegisterContacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_book_mark){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FAQ.this, BookMark.class);
            startActivity(intent);
        }else if (id == R.id.nav_notice){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(FAQ.this, Notice.class);
            startActivity(intent);
        }
        return true;
    }
}
