package com.example.asus.justtrustme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import java.net.URISyntaxException;

public class Search extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    EditText search_EditText;
    ImageButton search_btn;
    final String URL_SCHEME = "http://map.kakao.com/link/search/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_Search);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        search_EditText = findViewById(R.id.search_EditText);
        search_btn = findViewById(R.id.search_btn);
        WebView webview_test = findViewById(R.id.webview_test);
        final WebView webView = webview_test;
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_EditText.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_EditText.getWindowToken(), 0);
                String search_string = search_EditText.getText().toString();
                String url = URL_SCHEME + search_string;
                WebSettings mWebSettings = webView.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);
            }
        });
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
                Intent intent1 = new Intent(Search.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(Search.this, Logout.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_book_mark) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Search.this, BookMarkActi.class);
            startActivity(intent);
        } else if (id == R.id.nav_register_contacts) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Search.this, RegisterContacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_notice) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Search.this, Notice.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Search.this, FAQ.class);
            startActivity(intent);
        } else if (id == R.id.nav_finding_a_way){
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(Search.this, FindingWay.class);
            startActivity(intent);
        }
        return true;
    }

    private class Webview_test_browser extends WebViewClient {
        /*
        public static final String INTENT_PROTOCOL_START = "daummaps:";
        public static final String INTENT_PROTOCOL_INTENT = "005515";
        public static final String INTENT_URI_START = "daummaps:";
        public static final String INTENT_FALLBACK_URL = "https://m.naver.com";
        public static final String URI_SCHEME_MARKET = "market://details?id=";
*/
        public static final String INTENT_PROTOCOL_START = "intent:";
        public static final String INTENT_PROTOCOL_INTENT = "#Intent;";
        public static final String INTENT_PROTOCOL_END = ";end;";
        public static final String GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            /* 1차 시도
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent != null) {
                    startActivity(intent);
                }
                return true;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            view.loadUrl(url);
            return false;
            */
            /* 2차 시도
            if (url.startsWith("daummaps:")){
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent2);
            } else{
                view.loadUrl(url);
            }
            return true;
            */
            /* test 3!!
            if (url.startsWith("daummaps:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("daummaps://search?q=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                view.loadUrl(url);
                return true;
            }
            return true;
            */
            /* 4차 시도
            public static final String INTENT_PROTOCOL_START = "daummaps:";
            public static final String INTENT_PROTOCOL_INTENT = "005515;";
            public static final String INTENT_PROTOCOL_END = ";end;";
            public static final String GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";
*/

            /*  kakao 측 해답
            if (url.startsWith(INTENT_PROTOCOL_START)) {
                final int customUrlStartIndex = INTENT_PROTOCOL_START.length();
                final int customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT);
                if (customUrlEndIndex < 0) {
                    return false;
                } else {
                    final String customUrl = url.substring(customUrlStartIndex, customUrlEndIndex);
                    try {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)));
                        Log.d("customUrl: ",customUrl);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();

                        final int packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length();
                        final int packageEndIndex = url.indexOf(INTENT_PROTOCOL_END);

                        final String packageName = url.substring(packageStartIndex, packageEndIndex < 0 ? url.length() : packageEndIndex);
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)));

                    }
                    return true;
                }
            } else {
                return false;
            }*/

            /*  5차 시도
            if (url != null && url.startsWith("daummaps://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url != null && url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }else{
            }
            view.loadUrl(url);
            return false;
            */
            /* 6차 시도
            try {
                if (url.startsWith("daummaps:")) {
                    Toast.makeText(Search.this, "what the fuck", Toast.LENGTH_SHORT).show();
                    Intent parseIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent lineIntent = new Intent(parseIntent.getAction());
                    lineIntent.setData(parseIntent.getData());
                    lineIntent.setPackage(parseIntent.getPackage());
                    startActivity(lineIntent);
                    return true;
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            } catch (Exception e) {
                Toast.makeText(Search.this, "what the fuck222", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return true;
            */
            /* 7차 시도도            if (url.toLowerCase().startsWith(INTENT_URI_START)) {
                Intent parsedIntent = null;
                try {
                    parsedIntent = Intent.parseUri(url, 0);
                    startActivity(parsedIntent);
                } catch (ActivityNotFoundException | URISyntaxException e) {
                    return doFallback(view, parsedIntent);
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }


        public boolean doFallback(WebView view, Intent parsedIntent) {
            if (parsedIntent == null) {
                return false;
            }

            String fallbackUrl = parsedIntent.getStringExtra(INTENT_FALLBACK_URL);
            if (fallbackUrl != null) {
                view.loadUrl(fallbackUrl);
                return true;
            }

            String packageName = parsedIntent.getPackage();
            if (packageName != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URI_SCHEME_MARKET + packageName)));
                return true;
            }
            return false;
        }
        */

            if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url != null && url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
            }
            view.loadUrl(url);
            return false;

        }
    }
}
