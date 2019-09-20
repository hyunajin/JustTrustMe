package com.example.asus.justtrustme;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;


//즐겨찾기
public class BookMarkActi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    String strMemNo = "1";                //로그인한 회원 번호 넣어야 함!!
    static String selectedBookmarkItem;
    ListViewAdapter adapter;
    ListView listView;
    Button btn_bookmark_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        listView = findViewById(R.id.listView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_BM);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_bookmark_delete = findViewById(R.id.btn_bookmark_delete);
        btn_bookmark_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBookmarkItem = "1";
                new DeleteTaskBookmark().execute();
            }
        });
        new SelectTaskBookmark().execute();
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
                Intent intent1 = new Intent(BookMarkActi.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(BookMarkActi.this, Logout.class);
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
            Intent intent = new Intent(BookMarkActi.this, FindingWay.class);
            startActivity(intent);
        } else if (id == R.id.nav_book_mark){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(BookMarkActi.this, BookMarkActi.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(BookMarkActi.this, RegisterContacts.class);
            startActivity(intent);
        }else if (id == R.id.nav_notice){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(BookMarkActi.this, Notice.class);
            startActivity(intent);
        }else if (id == R.id.nav_faq){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(BookMarkActi.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }

    //select
    //회원 번호로 즐겨찾기 select
    class SelectTaskBookmark extends AsyncTask<Void,Void,Void> {
        Bookmark[] bookmarks;
        public String ip = getResources().getString(R.string.ip_address);
        String serverIp = "http://" + ip + "/justtrustmeDB/select/selectByMemNoBookmark.jsp?mem_no=";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d("SelectTask","회원 번호로 즐겨찾기 Select 처리 ---------------------");
                URL url = new URL(serverIp+strMemNo);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                Gson gson = new Gson();
                bookmarks = gson.fromJson(isr,Bookmark[].class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ListViewAdapter();
            for (Bookmark m : bookmarks) {
                adapter.addItem(new ListViewItem2(m.getBookmark_start_addr(), m.getBookmark_destination_addr()));
            }
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    class ListViewAdapter extends BaseAdapter {
        ArrayList<ListViewItem2> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ListViewItem2 item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //각각의 아이템의 view를 만들어주는 작업
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ListViewItemView2 view = new ListViewItemView2(getApplicationContext());
            ListViewItem2 item = items.get(position);
            view.setStartAddr(item.getStr_bookmark_start_addr());
            view.setDestinationAddr(item.getStr_bookmark_destination_addr());
            return view;
        }
    }

    class DeleteTaskBookmark extends AsyncTask<Void, Void, Void> {
        Bookmark[] bookmarks;
        public String ip = getResources().getString(R.string.ip_address);
        String serverIp = "http://" + ip + "/justtrustmeDB/delete/deleteByBookmarkNo.jsp?bookmark_no=";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(serverIp + selectedBookmarkItem);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                Gson gson = new Gson();
                bookmarks = gson.fromJson(isr, Bookmark[].class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new SelectTaskBookmark().execute();
        }
    }
}
