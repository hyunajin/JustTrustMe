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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeLIst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_notice);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        noticeListView = (ListView)findViewById(R.id.noticeListView);
        noticeLIst = new ArrayList<Notice>();

        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));
        noticeLIst.add(new Notice("공지사항입니다.","김현준","2019-07-10"));

        adapter = new NoticeListAdapter(getApplicationContext(), noticeLIst);
        noticeListView.setAdapter(adapter);
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
                Intent intent1 = new Intent(NoticeActivity.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(NoticeActivity.this, Logout.class);
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
            Intent intent = new Intent(NoticeActivity.this, FindingWay.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(NoticeActivity.this, RegisterContacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_book_mark){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(NoticeActivity.this, BookMark.class);
            startActivity(intent);
        }else if (id == R.id.nav_faq){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(NoticeActivity.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }
}
