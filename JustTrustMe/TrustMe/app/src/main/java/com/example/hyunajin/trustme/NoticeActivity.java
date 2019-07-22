package com.example.hyunajin.trustme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeLIst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

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
}
