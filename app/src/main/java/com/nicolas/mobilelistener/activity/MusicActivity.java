package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.AllTest;
import com.nicolas.mobilelistener.bean.OneTest;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.OperatorService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class MusicActivity extends Activity implements Callback<AllTest>, AdapterView.OnItemClickListener {

    private ListView dataView;

    private OperatorService operatorSErvice;
    private RestAdapter restAdapter;
    private List<OneTest> allTest = new ArrayList<>();
    private MusicAdapter musicAdapter;
    private ProgressDialog loadingDialog;
    private View logoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclis_activityt);

        initView();

        restAdapter = ((ListenerApplication) getApplication()).getAdapter();
        operatorSErvice = restAdapter.create(OperatorService.class);
        operatorSErvice.getAllTest(StuIdHolder.userId, this);
    }

    private void initView() {
        dataView = (ListView) findViewById(R.id.music_listview);
        logoutView = findViewById(R.id.logout);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("正在加载题目");
        loadingDialog.show();

        musicAdapter = new MusicAdapter(allTest);
        dataView.setAdapter(musicAdapter);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MusicActivity.this, LoginActivity.class));
                SharedPreferences preferences = MusicActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("isLogined");
                editor.remove("stuId");
                editor.commit();
                finish();
            }
        });
        dataView.setOnItemClickListener(this);
    }

    @Override
    public void success(AllTest allTest, Response response) {
        loadingDialog.dismiss();
        Logger.d(allTest.getResult().toString());
        if (allTest.getMessage()) {
            this.allTest.addAll(allTest.getResult());
            musicAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "获取信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Logger.d(error.getMessage());
        loadingDialog.dismiss();
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        if (allTest.get(position).getIs_complete() == 0) {
            intent = new Intent(this, AnsActivity.class);
        } else {
            intent = new Intent(this, AllQueActivity.class);
        }
        intent.putExtra("test_id", allTest.get(position).getTest_id());
        intent.putExtra("test_topic", allTest.get(position).getTest_topic());
        startActivity(intent);
    }

    class MusicAdapter extends BaseAdapter {

        private List<OneTest> tests;
        private LayoutInflater inflate;

        public MusicAdapter(List<OneTest> tests) {
            this.tests = tests;
            inflate = MusicActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return tests.size();
        }

        @Override
        public OneTest getItem(int position) {
            return tests.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflate.inflate(R.layout.musiclist_listview_item, null);
            TextView nameView = (TextView) convertView.findViewById(R.id.hwname);
            nameView.setText(getItem(position).getTest_topic());
            ImageView markView = (ImageView) convertView.findViewById(R.id.hwmark);
            if (getItem(position).getIs_complete() == 1)
                markView.setBackgroundResource(R.drawable.homework_right);
            return convertView;
        }
    }
}
