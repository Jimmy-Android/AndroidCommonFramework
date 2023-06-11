package com.example.jimmy.androidcommonframework.activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.jimmy.androidcommonframework.R;

/**
 * Created by wangxibin
 * Date: 2022/9/27
 */
public class WelcomeNfcActivity extends BaseNfcActivity implements View.OnClickListener {

    private ImageView nfc;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void initView() {
        nfc = findViewById(R.id.nfc);
        nfc.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected int createViews() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nfc:
                if (!ifNFCUse()) {
                    Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                    startActivity(setNfc);
                } else {
                    startActivity(new Intent(this, ReadNfcActivity.class));
                }
                break;
        }
    }

    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    protected Boolean ifNFCUse() {
        if (mNfcAdapter == null) {
            Toast.makeText(this, "当前设备不支持NFC！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
