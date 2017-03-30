package com.lecloud.lecloudsdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.lecloud.common.base.util.Logger;
import com.lecloud.common.cde.LeCloud;
import com.uesugi.mumen.R;

public class MainActivity extends Activity {

	private EditText etUUID;
	private EditText etVUID;
	private EditText etLiveId;
	private EditText etActiveId;
	private RadioButton rb1;
	private RadioButton rb2;

	String uuid = "487c884e76";
	String vuid="e5a4fb751e";
	
	String liveId = "201507153000063";//"201504213000012";//"201501193000003";// "201412183000004";
	// private NativeCrashHandler crashHandler;

	String actvieId = "A2015120200868";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e("MainActivity", "onCreate");
		LeCloud.init(getApplicationContext());
		setContentView(R.layout.main);
		initView();
		Button live = (Button) findViewById(R.id.live);
		Button vod = (Button) findViewById(R.id.vod);
		Button downlaodList = (Button) findViewById(R.id.downoadList_btn);
		live.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LiveActivity.class);
//				Intent intent = new Intent(MainActivity.this, MutlLiveActivity.class);
				intent.putExtra("liveID", etLiveId.getText().toString().trim());
				if (rb2.isChecked()) {
					intent.putExtra("isHLS", true);
				} else {
					intent.putExtra("isHLS", false);
				}
				startActivity(intent);
			}
		});

		vod.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, VODActivity.class);
				intent.putExtra("uu", etUUID.getText().toString().trim());
				intent.putExtra("vu", etVUID.getText().toString().trim());
				startActivity(intent);
			}
		});
		downlaodList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
				startActivity(intent);
			}
		});

		Button activeLive = (Button) findViewById(R.id.activity_live_btn);
		activeLive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MutlLiveActivity.class);
				intent.putExtra("activityId", etActiveId.getText().toString().trim());
				startActivity(intent);
			}
		});
		
	}

	private void initView() {
		etLiveId = (EditText) findViewById(R.id.et_liveID);
		etUUID = (EditText) findViewById(R.id.et_uuid);
		etVUID = (EditText) findViewById(R.id.et_vuid);
		etUUID.setText(uuid);
		etVUID.setText(vuid);
		etLiveId.setText(liveId);
		rb1 = (RadioButton) findViewById(R.id.rb_1);
		rb2 = (RadioButton) findViewById(R.id.rb_2);
		rb1.setChecked(true);
		etActiveId = (EditText) findViewById(R.id.et_activityID);
		etActiveId.setText(actvieId);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Logger.e("MainActivity", "onNewIntent");
		super.onNewIntent(intent);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		DownloadCenter.destoryDownloadService(this);
		LeCloud.destory();
	}

}
