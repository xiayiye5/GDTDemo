package com.qq.e.union.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.comm.constants.LoadAdParams;
import com.qq.e.union.demo.adapter.PosIdArrayAdapter;

import com.qq.e.comm.util.AdError;

import java.util.regex.Pattern;

/**
 * @author tysche
 */

public class SplashADActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
  private Spinner spinner;
  private EditText posIdEdt;

  private PosIdArrayAdapter arrayAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_ad);
    posIdEdt = findViewById(R.id.posId);

    findViewById(R.id.splashADPreloadButton).setOnClickListener(this);
    findViewById(R.id.splashADDemoButton).setOnClickListener(this);
    findViewById(R.id.splashFetchAdOnly).setOnClickListener(this);

    spinner = findViewById(R.id.id_spinner);
    arrayAdapter = new PosIdArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.splash_ad));
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(arrayAdapter);
    spinner.setOnItemSelectedListener(this);

  }

  private String getPosID() {
    String posId = ((EditText) findViewById(R.id.posId)).getText().toString();
    return TextUtils.isEmpty(posId) ? PositionId.SPLASH_POS_ID : posId;
  }

  private boolean needLogo() {
    return ((CheckBox) findViewById(R.id.checkBox)).isChecked();
  }

  private boolean customSkipBtn(){
    return ((CheckBox)findViewById(R.id.checkCustomSkp)).isChecked();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.splashADPreloadButton:
        SplashAD splashAD = new SplashAD(this, getPosID(), null);
        LoadAdParams params = new LoadAdParams();
        params.setLoginAppId("testAppId");
        params.setLoginOpenid("testOpenId");
        params.setUin("testUin");
        splashAD.setLoadAdParams(params);
        splashAD.preLoad();
        break;
      case R.id.splashADDemoButton:
        startActivity(getSplashActivityIntent());
        break;
      case R.id.splashFetchAdOnly:
        Intent intent = getSplashActivityIntent();
        intent.putExtra("load_ad_only", true);
        startActivity(intent);
        break;
    }
  }

  private Intent getSplashActivityIntent() {
    Intent intent = new Intent(SplashADActivity.this, SplashActivity.class);
    intent.putExtra("pos_id", getPosID());
    intent.putExtra("need_logo", needLogo());
    intent.putExtra("need_start_demo_list", false);
    intent.putExtra("custom_skip_btn", customSkipBtn());
    return intent;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    arrayAdapter.setSelectedPos(position);
    posIdEdt.setText(getResources().getStringArray(R.array.splash_ad_value)[position]);
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
}
