/*
 * *
 *  * Created by Salman Saleem on 6/21/18 1:44 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 3/13/18 12:44 AM
 *
 */

package com.salman.appnews.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.salman.appnews.R;


public class BrowserActivity extends AppCompatActivity {

    private WebView web_view_content_main_1;
    private boolean doubleBackToExitPressedOnce;
    private SwipeRefreshLayout swipe_refresh_layout_main_1;
    private RelativeLayout activity_browser;
    private String link;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        initView();
        initAction();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_open_browser:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        web_view_content_main_1.goBack();
        // super.onBackPressed();


        Snackbar.make(activity_browser, getString(R.string.back_message), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private void initAction(){
        web_view_content_main_1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                BrowserActivity.this.setTitle(view.getTitle());
                getSupportActionBar().setTitle(view.getTitle());

                swipe_refresh_layout_main_1.setRefreshing(false);
            }
        });
        swipe_refresh_layout_main_1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout_main_1.setRefreshing(true);
                web_view_content_main_1.reload();

            }
        });

    }
    private void initView(){
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        web_view_content_main_1=(WebView) findViewById(R.id.web_view);
        swipe_refresh_layout_main_1=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_main_1);
        swipe_refresh_layout_main_1.setRefreshing(true);
        web_view_content_main_1.setWebViewClient(new WebViewClient());
        web_view_content_main_1.getSettings().setJavaScriptEnabled(true);
        web_view_content_main_1.loadUrl(link);

        activity_browser =(RelativeLayout) findViewById(R.id.activity_browser);
    }



}
