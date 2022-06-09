package com.example.mylibrary.selectvideo;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.mylibrary.R;
import com.example.mylibrary.selectvideo.adapter.SelectVideoActivity_BottomListDialogAdapter;
import com.example.mylibrary.selectvideo.base.DefaultBaseActivity;
import com.example.mylibrary.selectvideo.bean.Video;
import com.example.mylibrary.selectvideo.dialog.BottomListDialog;
import com.example.mylibrary.selectvideo.inteface.AbstructProvider;
import com.example.mylibrary.selectvideo.permission.PermissionCode;
import com.example.mylibrary.selectvideo.permission.PermissionUtil;
import com.example.mylibrary.selectvideo.provider.VideoProvider;
import com.example.mylibrary.selectvideo.utils.AdapterUtils;
import com.example.mylibrary.selectvideo.utils.DoubleUtils;
import com.example.mylibrary.selectvideo.utils.ScreenUtil;
import com.example.mylibrary.selectvideo.utils.StatusBarHeightUtil;
import com.gyf.barlibrary.ImmersionBar;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SelectVideoActivity extends DefaultBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_CODE = 8888;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Map<String, List<Video>> AllList;
    private RelativeLayout actionbar, layoutSelectVideo;
    private ImageView img_album_arrow, recordVideoImg;
    private TextView tvSelectVideo;
    protected int width, height;
    private int titleColor, tag, recordTime = 60;
    // 录制的视频的质量
    private int recordQuality = 1;
    private View barColor;

    @Override
    protected void initialize() {
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_select_video);
        titleColor = getIntent().getIntExtra("titleColor", Color.parseColor("#000000"));
        tag = getIntent().getIntExtra("tag", 10006);
        recordTime = getIntent().getIntExtra("recordTime", 60);
        recordQuality = getIntent().getIntExtra("recordQuality", 1);
    }

    @Override
    protected void initView() {
        width = ScreenUtil.getScreenWidth(context);
        height = ScreenUtil.getStatusHeight(context);
        barColor = findViewById(R.id.barColor);
        recordVideoImg = (ImageView) findViewById(R.id.recordVideoImg);
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        actionbar.setBackgroundColor(titleColor);
        barColor.setBackgroundColor(titleColor);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.Gray6, R.color.Gray6, R.color.Gray6, R.color.Gray6);
        startRefreshing(swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.layout_select_video).setOnClickListener(this);

        img_album_arrow = (ImageView) findViewById(R.id.img_album_arrow);
        layoutSelectVideo = (RelativeLayout) findViewById(R.id.layout_select_video);
        tvSelectVideo = (TextView) findViewById(R.id.tv_select_video);
        recordVideoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissionCode = new String[3];
                permissionCode[0] = Manifest.permission.CAMERA;
                permissionCode[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                permissionCode[2] = Manifest.permission.RECORD_AUDIO;
                if (Build.VERSION.SDK_INT >= 23) {
                    boolean hasPermission = PermissionUtil.checkAndRequestPermissionsInActivity(SelectVideoActivity.this, permissionCode);
                    if (hasPermission) {
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        // 设置视频录制的最长时间
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, recordTime);
                        // 设置视频录制的画质
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, recordQuality);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionCode.REQUEST_CAMERA:
                boolean hasGrantPermission = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        hasGrantPermission = false;
                    }
                }
                if (hasGrantPermission) {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    // 设置视频录制的最长时间
                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, recordTime);
                    // 设置视频录制的画质
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, recordQuality);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    // Permission Denied
                    Toast.makeText(SelectVideoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.initData();
        new initVideosThread().start();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            finish();

        } else if (i == layoutSelectVideo.getId()) {
            if (bottomListDialog != null) {
                bottomListDialog.show();
                img_album_arrow.setSelected(true);
            }

        }
    }

    private BottomListDialog bottomListDialog;
    private Adapter adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AppConstant.WHAT.SUCCESS:
                    stopRefreshing(swipeRefreshLayout);
                    adapter = new Adapter(R.layout.adapter_select_video_item2, (List<Video>) msg.obj);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
                        @Override
                        public void onSimpleItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                            Log.d("onSimpleItemChildClick", view.getId() + "");
                        }

                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.d("onItemClick", view.getId() + "");
                            super.onItemClick(adapter, view, position);
                            Video video = (Video) adapter.getItem(position);
                            int viewId = view.getId();
                            if (viewId == R.id.img_play_video) {
                                try {
                                    if (!DoubleUtils.isFastDoubleClick()) {
                                        Intent intent = new Intent(SelectVideoActivity.this, VideoPlayActivity.class);
                                        String videoPath = video.getPath();
                                        intent.putExtra("videoPath", videoPath);
                                        intent.putExtra("isExternalPreviewVideo", true);
                                        SelectVideoActivity.this.startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(SelectVideoActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                                }
                            } else if (viewId == R.id.tv_video_select) {
                                view.setVisibility(View.VISIBLE);
                                Intent i = new Intent();
                                String videoPath = video.getPath();
                                i.putExtra("videoPath", videoPath);
                                setResult(RESULT_OK, i);
                                SelectVideoActivity.this.finish();
                            }
                        }
                    });

                    final SelectVideoActivity_BottomListDialogAdapter bottomListDialogAdapter = new SelectVideoActivity_BottomListDialogAdapter(activity, AllList);

                    bottomListDialog = new BottomListDialog.Builder(activity
                            , bottomListDialogAdapter,
                            height - actionbar.getHeight() - StatusBarHeightUtil.getStatusBarHeight(context)
                    ).setOnItemClickListener(new BottomListDialog.OnItemClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            dialog.dismiss();
                            String album = (String) bottomListDialogAdapter.getAllList().keySet().toArray()[which];
                            adapter.setNewData(bottomListDialogAdapter.getAllList().get(album));
                            tvSelectVideo.setText(album);
                            img_album_arrow.setSelected(false);
                        }
                    }).create();
                    break;

                case AppConstant.WHAT.FAILURE:
                    stopRefreshing(swipeRefreshLayout);
                    break;
            }
        }
    };


    class initVideosThread extends Thread {
        @Override
        public void run() {
            super.run();
            AbstructProvider provider = new VideoProvider(activity);
            List<Video> list = (List<Video>) provider.getList();

            List<Video> templist = new ArrayList<>();
            AllList = new HashMap<>();

            //我需要可以查看所有视频 所以加了这样一个文件夹名称
            AllList.put(" " + getResources().getString(R.string.all_video), list);

            //主要是读取文件夹的名称 做分文件夹的展示

            for (Video video : list) {
                String album = video.getAlbum();
                if (TextUtils.isEmpty(album)) {
                    album = "Camera";
                }

                if (AllList.containsKey(album)) {
                    AllList.get(album).add(video);
                } else {
                    templist = new ArrayList<>();
                    templist.add(video);
                    AllList.put(album, templist);
                }
            }

            //在子线程读取好数据后使用handler 更新
            if (list == null || list.size() == 0) {
                Message message = new Message();
                message.what = AppConstant.WHAT.FAILURE;
                mHandler.sendMessage(message);
            } else {
                Message message = new Message();
                message.what = AppConstant.WHAT.SUCCESS;
                message.obj = list;
                mHandler.sendMessage(message);
            }
        }
    }

    @Override
    public void onRefresh() {
        super.initData();
        new initVideosThread().start();
    }

    protected void startRefreshing(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    protected void stopRefreshing(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    class Adapter extends BaseQuickAdapter<Video, BaseViewHolder> {


        public Adapter(int layoutResId, List<Video> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, Video item) {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(item.getDuration()),
                    TimeUnit.MILLISECONDS.toMinutes(item.getDuration()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(item.getDuration())),
                    TimeUnit.MILLISECONDS.toSeconds(item.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(item.getDuration())));
            helper.setText(R.id.text_duration, hms);
            float itemWidth = ScreenUtil.getScreenWidth(context);
            ImageView imgVideo = AdapterUtils.getAdapterView(helper.getConvertView(), R.id.img_video);
            int width = (int) ((itemWidth - 4) / 3);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
            imgVideo.setLayoutParams(layoutParams);
            helper.addOnClickListener(R.id.img_play_video);
            helper.addOnClickListener(R.id.tv_video_select);
            Glide.with(context)
                 .asBitmap()
                 .load(Uri.fromFile(new File(item.getPath())))
                 .into(imgVideo);
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == 123) {
            super.initData();
            new initVideosThread().start();
        }
    }
}
