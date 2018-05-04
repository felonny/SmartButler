package com.yuchen.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuchen.smartbutler.R;
import com.yuchen.smartbutler.utils.L;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.ui
 * 文件名: VideoActivity
 * Created by tangyuchen on 18/5/3.
 * 描述: TODO
 */

public class VideoActivity extends BaseActivity {

    private VideoView videoView;
    private String url ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.vidio_view);
        getData();
        playVideo();
        videoView.start();
    }

    private void getData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }
    private void playVideo() {


        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        //videoView.setVideoPath(path);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //设置快进的倍速
                mediaPlayer.setPlaybackSpeed(1.0f);
                //设置缓冲大小
                mediaPlayer.setBufferSize(512 * 1024);
            }
        });

    }
}
