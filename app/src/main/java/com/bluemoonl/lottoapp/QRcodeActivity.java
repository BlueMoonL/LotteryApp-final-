package com.bluemoonl.lottoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRcodeActivity extends AppCompatActivity {


    TextView randomLottoNumber;
    TextView qrcodeButton;
    ImageView qrcode_button;

    WebView wv;
    TextView et;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_qrcode);

        qrcodeButton = findViewById(R.id.qrcodeButton);
        randomLottoNumber = findViewById(R.id.randomLottoNumber);
        randomLottoNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomLottoNumber.setEnabled(false);
                qrcodeButton.setEnabled(true);

                Intent intent = new Intent(getApplicationContext(), LottoMainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        qrcode_button = findViewById(R.id.qrcode_button);
        qrcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = et.getText().toString();

                if(!address.startsWith("http://")) {
                    address = "http://" + address;
                }

                integrator = new IntentIntegrator(QRcodeActivity.this);
                //바코드 안의 텍스트
                integrator.setPrompt("바코드를 선에 맞춰주세요");
                //바코드 인식시 소리 여부
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.setOrientationLocked(false);
                //바코드 스캐너 시작
                integrator.initiateScan();
            }
        });

        wv = findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(QRcodeActivity.this,"로딩 끝", Toast.LENGTH_SHORT).show();

            }
        });

        et = findViewById(R.id.et);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //키보드 숨기기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                //qr코드를 읽어 EditText에 입력해줌
                et.setText(result.getContents());

                wv.loadUrl(et.getText().toString());

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
