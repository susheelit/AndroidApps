package com.aob.aobsalesman.activities.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.aob.aobsalesman.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(getResources().getString(R.string.youtube_API_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        try {
                            youTubePlayer.loadVideo(getIntent().getExtras().getString("url"));
                            //youTubePlayer.loadVideo("spXUVIcMnCA");
                         //  youTubePlayer.cueVideo("spXUVIcMnCA");
                        }catch (Exception e){
                                Toast.makeText(VideoPlayerActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        //  youTubePlayer.loadVideo("3LiubyYpEUk");
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+getIntent().getExtras().getString("url"))));
                        finish();
                    }
                });

    }
}
