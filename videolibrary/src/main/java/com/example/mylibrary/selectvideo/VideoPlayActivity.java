package com.example.mylibrary.selectvideo;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.mylibrary.R;
import com.example.mylibrary.selectvideo.base.DefaultBaseActivity;
import com.example.mylibrary.selectvideo.utils.PictureMimeType;
import com.example.mylibrary.selectvideo.utils.SdkVersionUtils;
import com.gyf.barlibrary.ImmersionBar;


/**
 * @author：luck
 * @data：2017/8/28 下午11:00
 * @描述: 视频播放类
 */
public class VideoPlayActivity extends DefaultBaseActivity implements
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, View.OnClickListener {

    private int mPositionWhenPaused = -1;
    private String videoPath;

    private RelativeLayout actionbar;
    private ImageButton imgBtnBack;
    private MediaController mMediaController;
    private VideoView mVideoView;
    private ImageView imgPlay;
    private View barColor;


    @Override
    protected void initialize() {
        ImmersionBar.with(this).init();
        videoPath = getIntent().getStringExtra("videoPath");
        setContentView(R.layout.activity_video_play);
    }

    @Override
    protected void initView() {
        barColor = findViewById(R.id.barColor);
        imgPlay = (ImageView) findViewById(R.id.iv_play);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        imgBtnBack = (ImageButton) findViewById(R.id.img_back);
        barColor.setBackgroundColor(Color.parseColor("#000000"));
        mMediaController = new MediaController(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setMediaController(mMediaController);
        imgBtnBack.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
    }

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        // Play Video
        if (SdkVersionUtils.checkedAndroid_Q() && PictureMimeType.isContent(videoPath)) {
            mVideoView.setVideoURI(Uri.parse(videoPath));
        } else {
            mVideoView.setVideoURI(Uri.parse(videoPath));
        }
        mVideoView.start();
        super.onStart();
    }

    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMediaController = null;
        mVideoView = null;
        imgPlay = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        // Resume video player
        if (mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }

    @Override
    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (null != imgPlay) {
            imgPlay.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.iv_play) {
            mVideoView.start();
            imgPlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name)) {
                    return getApplicationContext().getSystemService(name);
                }
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    // video started
                    mVideoView.setBackgroundColor(Color.TRANSPARENT);
                    return true;
                }
                return false;
            }
        });
    }
}
