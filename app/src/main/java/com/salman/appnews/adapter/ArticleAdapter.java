/*
 * *
 *  * Created by Salman Saleem on 6/21/18 1:44 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 6/21/18 1:44 AM
 *
 */

package com.salman.appnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.salman.appnews.R;
import com.salman.appnews.entity.news.Article;
import com.salman.appnews.manager.PrefManager;
import com.salman.appnews.manager.StorageFavorites;
import com.salman.appnews.ui.activity.ArticleActivity;
import com.salman.appnews.ui.activity.VideoActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Salman Saleem on 24/02/2017.
 */

public class ArticleAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> articleList;
    private Context context;
    private Boolean shwocategorie = true;
    private Boolean savedList =  false;
    private InterstitialAd mInterstitialAd;

    public ArticleAdapter(List<Article> articleList, Context context, Boolean shwocategorie) {
        this.articleList = articleList;
        this.context = context;
        this.shwocategorie = shwocategorie;
    }
    public ArticleAdapter(List<Article> articleList, Context context, Boolean shwocategorie,Boolean savedList) {
        this.articleList = articleList;
        this.context = context;
        this.shwocategorie = shwocategorie;
        this.savedList=savedList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1: {
                View v1 = inflater.inflate(R.layout.article_item, parent, false);
                viewHolder = new ArticleAdapter.ArticleHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_native_ads, parent, false);
                viewHolder = new ArticleAdapter.ViewHolderNative_main(v2);
                break;
            }
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder_parent,final int position) {
        switch (holder_parent.getItemViewType()) {
            case 1 : {
                final ArticleAdapter.ArticleHolder holder = (ArticleAdapter.ArticleHolder) holder_parent;
                //holder.text_View_title_article_item.setText(articleList.get(position).getTitle());
                holder.text_view_time_article_item.setText(articleList.get(position).getCreated());
                holder.text_view_content_text_view_time_article_item.setText(articleList.get(position).getTitle());
                Picasso.with(context).load(articleList.get(position).getImage()).placeholder(R.drawable.image6409).into(holder.image_view_article_item);

                if (this.shwocategorie==true){
                    holder.text_view_categoryarticle_item.setText(articleList.get(position).getCategory());
                }else{
                    holder.text_view_categoryarticle_item.setVisibility(View.GONE);
                }
                if (articleList.get(position).getType().equals("article")){
                    holder.imge_view_time_video.setVisibility(View.GONE);
                }else{
                    holder.imge_view_time_video.setVisibility(View.VISIBLE);
                }
                final StorageFavorites storageFavorites= new StorageFavorites(context);
                List<Article> ormArticles = storageFavorites.loadFavorites();
                Boolean exist = false;
                if (ormArticles==null){
                    ormArticles= new ArrayList<>();
                }
                for (int i = 0; i <ormArticles.size() ; i++) {
                    if (ormArticles.get(i).getId().equals(articleList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist == false) {
                    holder.image_view_save_article_item.setImageResource(R.drawable.ic_marker);
                } else {
                    holder.image_view_save_article_item.setImageResource(R.drawable.ic_bookmark);
                }
                holder.image_view_save_article_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Article> ormArticles = storageFavorites.loadFavorites();
                        Boolean exist = false;
                        if (ormArticles==null){
                            ormArticles= new ArrayList<>();
                        }
                        for (int i = 0; i <ormArticles.size() ; i++) {
                            if (ormArticles.get(i).getId().equals(articleList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Article> audios= new ArrayList<Article>();
                            for (int i = 0; i < ormArticles.size(); i++) {
                                audios.add(ormArticles.get(i));
                            }
                            audios.add(articleList.get(position));
                            storageFavorites.storeAudio(audios);
                            holder.image_view_save_article_item.setImageResource(R.drawable.ic_bookmark);
                        }else{
                            ArrayList<Article> new_favorites= new ArrayList<Article>();
                            for (int i = 0; i < ormArticles.size(); i++) {
                                if (!ormArticles.get(i).getId().equals(articleList.get(position).getId())){
                                    new_favorites.add(ormArticles.get(i));
                                }
                            }
                            if (savedList==true){
                                holder.card_view_article_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeAudio(new_favorites);
                            holder.image_view_save_article_item.setImageResource(R.drawable.ic_marker);
                        }
                    }
                });
                mInterstitialAd = new InterstitialAd(context);
                mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_full_screen));
                requestNewInterstitial();

                holder.card_view_article_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Intent intent;
                        if (articleList.get(position).getType().equals("article")) {
                            intent = new Intent(context, ArticleActivity.class);

                        }else{
                            intent = new Intent(context, VideoActivity.class);
                            intent.putExtra("video_article",articleList.get(position).getVideo());
                        }
                        intent.putExtra("id_article",articleList.get(position).getId()+"");
                        intent.putExtra("title_article",articleList.get(position).getTitle());
                        intent.putExtra("image_article",articleList.get(position).getImage());
                        intent.putExtra("created_article",articleList.get(position).getCreated());
                        intent.putExtra("content_article",articleList.get(position).getContent());
                        intent.putExtra("category_article",articleList.get(position).getCategory());

                        if (articleList.get(position).getComment()==true){
                            intent.putExtra("comment_article","true");
                        }else{
                            intent.putExtra("comment_article","false");
                        }
                        if (context.getResources().getString(R.string.AD_MOB_ENABLED_FULL_SCREEN).equals("true")){

                            if (mInterstitialAd.isLoaded()) {
                                if (check()){
                                    mInterstitialAd.show();
                                }else{
                                    holder.card_view_article_item.getContext().startActivity(intent);
                                }
                            } else {
                                holder.card_view_article_item.getContext().startActivity(intent);
                                mInterstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {
                                        requestNewInterstitial();
                                        holder.card_view_article_item.getContext().startActivity(intent);
                                    }
                                });
                            }
                            mInterstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    requestNewInterstitial();
                                    holder.card_view_article_item.getContext().startActivity(intent);
                                }
                            });

                        }else{
                            holder.card_view_article_item.getContext().startActivity(intent);
                        }
                    }
                });
            }
            break;
            case 2: {
                ///ADS
                break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {
        private CardView    card_view_article_item;
        private ImageView   image_view_article_item;
        private TextView    text_View_title_article_item;
        private TextView    text_view_time_article_item;
        private TextView    text_view_content_text_view_time_article_item;
        private ImageView   image_view_save_article_item;
        private TextView    text_view_categoryarticle_item;
        private ImageView   imge_view_time_video;

        public ArticleHolder(View itemView) {
            super(itemView);
            this.card_view_article_item=(CardView) itemView.findViewById(R.id.card_view_article_item);
            this.image_view_article_item=(ImageView) itemView.findViewById(R.id.image_view_article_item);
            this.text_View_title_article_item=(TextView) itemView.findViewById(R.id.text_View_title_article_item);
            this.text_view_time_article_item=(TextView) itemView.findViewById(R.id.text_view_time_article_item);
            this.text_view_content_text_view_time_article_item=(TextView) itemView.findViewById(R.id.text_view_content_text_view_time_article_item);
            this.image_view_save_article_item= (ImageView) itemView.findViewById(R.id.image_view_save_article_item);
            this.text_view_categoryarticle_item=(TextView) itemView.findViewById(R.id.text_view_categoryarticle_item);
            this.imge_view_time_video=(ImageView) itemView.findViewById(R.id.imge_view_time_video);
        }

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    public boolean check(){
        PrefManager prf = new PrefManager(context);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        if (prf.getString("LAST_DATE_ADS").equals("")) {
            prf.setString("LAST_DATE_ADS", strDate);
        } else {
            String toyBornTime = prf.getString("LAST_DATE_ADS");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date oldDate = dateFormat.parse(toyBornTime);
                System.out.println(oldDate);

                Date currentDate = new Date();

                long diff = currentDate.getTime() - oldDate.getTime();
                long seconds = diff / 1000;

                if (seconds > Integer.parseInt(context.getResources().getString(R.string.AD_MOB_TIME_FULL_AUTO))) {
                    prf.setString("LAST_DATE_ADS", strDate);
                    return  true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }
    public static class ViewHolderNative_main extends RecyclerView.ViewHolder {
        public NativeExpressAdView adView;
        VideoController mVideoController;

        public ViewHolderNative_main(View view) {
            super(view);
            adView = (NativeExpressAdView) itemView.findViewById(R.id.adView);

            // Set its video options.
            adView.setVideoOptions(new VideoOptions.Builder()

                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            mVideoController = adView.getVideoController();
            mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Log.d(LOG_TAG, "Video playback is finished.");
                    super.onVideoEnd();
                }
            });

            // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
            // loading.
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    adView.setVisibility(View.VISIBLE);

                }
            });
            adView.loadAd(new AdRequest.Builder()
                    .build());
        }
    }
    public int getItemViewType(int position) {
        return articleList.get(position).getViewType();
    }

}
