/*
 * *
 *  * Created by Salman Saleem on 6/21/18 1:44 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 3/13/18 12:47 AM
 *
 */

package com.salman.appnews.ui.activity;

import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.salman.appnews.R;
import com.salman.appnews.adapter.ArticleAdapter;
import com.salman.appnews.entity.news.Article;
import com.salman.appnews.manager.StorageFavorites;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {
    private List<Article> articleList=new ArrayList<>();
    private RelativeLayout activity_saved;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycle_view_saved;
    private SwipeRefreshLayout swipe_refreshl_saved;
    private ArticleAdapter articlesAdapter;
    private Toolbar myToolbar;
    private ImageView imageView_empty_saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        initView();
        initAction();
        getArticle();
    }


    public void initAction(){
        this.swipe_refreshl_saved.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getArticle();
            }
        });
    }

    private void getArticle() {

        swipe_refreshl_saved.setRefreshing(true);
        final StorageFavorites storageFavorites= new StorageFavorites(getApplicationContext());
        List<Article> ormArticles = storageFavorites.loadFavorites();




        if (ormArticles==null){
            ormArticles= new ArrayList<>();
        }
        if (ormArticles.size()!=0){
            articleList.clear();
            for (int i=0;i<ormArticles.size();i++){
                Article a= new Article();
                a = ormArticles.get(i) ;
                articleList.add(a);
            }
            articlesAdapter.notifyDataSetChanged();
            imageView_empty_saved.setVisibility(View.GONE);
            recycle_view_saved.setVisibility(View.VISIBLE);
        }else{
            imageView_empty_saved.setVisibility(View.VISIBLE);
            recycle_view_saved.setVisibility(View.GONE);
        }

        swipe_refreshl_saved.setRefreshing(false);

    }
    public void initView(){

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_saved));
        this.activity_saved=(RelativeLayout) findViewById(R.id.activity_saved);

        this.linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recycle_view_saved=(RecyclerView) findViewById(R.id.recycle_view_saved);
        this.swipe_refreshl_saved=(SwipeRefreshLayout) findViewById(R.id.swipe_refreshl_saved);
        this.imageView_empty_saved=(ImageView) findViewById(R.id.imageView_empty_saved);

        articlesAdapter=new ArticleAdapter(articleList,getApplicationContext(),true,true);
        recycle_view_saved.setHasFixedSize(true);
        recycle_view_saved.setAdapter(articlesAdapter);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view_saved.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
