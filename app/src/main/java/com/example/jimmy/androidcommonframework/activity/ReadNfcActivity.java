package com.example.jimmy.androidcommonframework.activity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.view.View;

/**
 * Created by wangxibin
 * Date: 2022/9/27
 */
public class ReadNfcActivity extends BaseNfcActivity implements View.OnClickListener {

    private TextView mNfcText;
    private ImageView back;
    private LinearLayout linTxt;
    private ImageView readPic;
    private TextView systemTime;
    private Button bt;
    private LinearLayout linBt;
    private String currentTime;

    private Tag mTag;

    @Override
    protected void initView() {
        back = findViewById(R.id.back);
        readPic = findViewById(R.id.readPic);
        linTxt = findViewById(R.id.linTxt);
        mNfcText = findViewById(R.id.nfcTxt);
        systemTime = findViewById(R.id.systemTime);
        linBt = findViewById(R.id.linBt);
        bt = findViewById(R.id.bt);
        back.setOnClickListener(this);
        bt.setOnClickListener(this);

        resolveIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (mNfcAdapter != null) {
            if (mNfcAdapter.isEnabled()) {
                resolveIntent(getIntent());
            }
        }
    }

    /**
     * 初次判断卡片的类型
     *
     * @param intent
     */
    private void resolveIntent(Intent intent) {
        NdefMessage[] msgs = NfcUtil.getNdefMsg(intent); //解析nfc标签中的数据

        if (msgs == null) {
            Toast.makeText(ReadNfcActivity.this, "非NFC启动", Toast.LENGTH_SHORT).show();
        } else {
            String id = NfcUtil.readNFCId(NfcUtil.getNFCTag(intent));
            Log.e("读取数据 >>>", "nfcID:" + id);
            setNFCMsgView(id, msgs);
        }
    }

    /**
     * 显示扫描后的信息
     *
     * @param ndefMessages ndef数据
     */
    @SuppressLint("SetTextI18n")
    private void setNFCMsgView(String tag, NdefMessage[] ndefMessages) {
        if (ndefMessages == null || ndefMessages.length == 0) {
            return;
        }

//        mNfcText.setText("Payload:" + new String(ndefMessages[0].getRecords()[0].getPayload()) + "\n");
        Log.e("ndef数据 >>>>", "setNFCMsgView ndefMessages:  " + ndefMessages.length);
        Log.e("ndef数据 >>>>", "setNFCMsgView Payload:  " + new String(ndefMessages[0].getRecords()[0].getPayload()));
        readPic.setVisibility(View.GONE);
        linTxt.setVisibility(View.VISIBLE);
        linBt.setVisibility(View.VISIBLE);

        //将数据信息存储到本地
        try {
            FileOutputStream fos = new FileOutputStream(getExternalFilesDir(null) + "/NFC读取.txt", true);
            OutputStreamWriter ost = new OutputStreamWriter(fos);

            NdefMessage msg = ndefMessages[0];
            List<ParsedNdefRecord> records = NdefMessageParser.parse(msg);
            final int size = records.size();
            Log.e("ndef数据 >>>>", "setNFCMsgView records:  " + size);
            for (int i = 0; i < size; i++) {
                ParsedNdefRecord record = records.get(i);
                String math = record.toString();
                Log.e("ndef数据 >>>>", "setNFCMsgView: math" + math);
                String viewText = record.getViewText();
                String TagStr = "55AA" + viewText;
                mNfcText.setText("Tag ID (hex): " + tag + "\n"
                        + "message：" + TagStr);

                currentTime = TimeFormatUtils.getCurrentTime();
                systemTime.setText("当前系统时间：" + currentTime);
                Log.e("扫描后的数据信息 >>>>", "setNFCMsgView: " + record.getViewText());

                ost.write("Tag ID(hex)：" + record.getViewText());
                ost.write("当前系统时间：" + currentTime);
                ost.write("\n");
                ost.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //字符序列转换为16进制字符串
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int createViews() {
        return R.layout.activity_read_nfc;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt:
                mNfcText.setText("");
                systemTime.setText("");
                finish();
                break;
        }
    }
}
}
