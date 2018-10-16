package com.frankchang.rfidread_demo;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView tvShow;
    private NfcAdapter nfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findviews();
        chkNFC();
    }

    private void chkNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);  //對應裝置上 NFC
        if (nfcAdapter == null) {  //透過是否對應到裝置 NFC ，檢查裝置是否支援 NFC
            Toast.makeText(this, "本裝置不支援 NFC!", Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC 功能尚未開啟", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void findviews() {
        tvShow = findViewById(R.id.tvShow);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 取得 Intent 動作
        Intent intent = getIntent();
        String action = intent.getAction();
        // 如果發生 ACTION_TAG_DISCOVERED
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            // 取得 tag 資訊
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                tvShow.setText("沒有偵測到 tag");
            }else{
                // 顯示 TAG 資料
                String tagInfo = tag.toString() + "\n";
                tagInfo += "\nTag UUID: \n";
                byte[] tagId = tag.getId();
                tagInfo += "長度= " + tagId.length + "\n";
                for (int i = 0; i < tagId.length; i++) {
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";
                // 顯示 TAG 其他技術規範資料
                String[] techList = tag.getTechList();
                tagInfo += "\n技術規範資料\n";
                tagInfo += "長度= " + techList.length + "\n";
                for (int i = 0; i < techList.length; i++) {
                    tagInfo += techList[i] + "\n ";
                }
                tvShow.setText(tagInfo);
            }
        } else {
            // 非ACTION_TAG_DISCOVERED
        }

    }

}
