package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.bean.StuIdHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwang on 15-9-17.
 */
public class RandListenActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener, AdapterView.OnItemClickListener {

    private TextView titleView;
    private TextView queTopicView;
    private TextView ansAView;
    private TextView ansBView;
    private TextView ansCView;
    private TextView ansDView;
    private ImageView playView;
    private ImageView rightA, rightB, rightC, rightD;
    private ImageView rightView;
    private View uploadView;
    private View flag1View, flag2View, flag3View, flag4View;
    private View allQueView;

    private int selectedColr = Color.parseColor("#0288D1");
    private List<Question> allQues;
    private int position;
    private MediaPlayer mMediaPlayer;
    private PopupWindow mPopupWindow;
    private View popView;
    private ListView dataView;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rand_listener_activity);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (mMediaPlayer == null) {
            Intent intent = getIntent();
            allQues = (ArrayList<Question>) intent.getSerializableExtra("all_que");
            position = intent.getIntExtra("position", 0);

            //初始化音频播放设置
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);

            //设置popupWindow内容
            popView = getLayoutInflater().inflate(R.layout.all_que_pop, null);
            dataView = (ListView) popView.findViewById(R.id.pop_data);
            dataView.setAdapter(new QuestionAdapter(allQues));
            dataView.setOnItemClickListener(this);
            //设置popupWindow属性
            mPopupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics()));
            mPopupWindow.setContentView(popView);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    isShow = false;
                }
            });
            updateQue();
        } else {
            mMediaPlayer.start();
        }
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.testtitle);
        queTopicView = (TextView) findViewById(R.id.que_topic);
        ansAView = (TextView) findViewById(R.id.que_ans_1);
        ansBView = (TextView) findViewById(R.id.que_ans_2);
        ansCView = (TextView) findViewById(R.id.que_ans_3);
        ansDView = (TextView) findViewById(R.id.que_ans_4);
        rightA = (ImageView) findViewById(R.id.right_1);
        rightB = (ImageView) findViewById(R.id.right_2);
        rightC = (ImageView) findViewById(R.id.right_3);
        rightD = (ImageView) findViewById(R.id.right_4);
        uploadView = findViewById(R.id.upload_ans);
        flag1View = findViewById(R.id.flag_1);
        flag2View = findViewById(R.id.flag_2);
        flag3View = findViewById(R.id.flag_3);
        flag4View = findViewById(R.id.flag_4);
        playView = (ImageView) findViewById(R.id.btnPlay);
        allQueView = findViewById(R.id.more_que);

        flag1View.setOnClickListener(this);
        flag2View.setOnClickListener(this);
        flag3View.setOnClickListener(this);
        flag4View.setOnClickListener(this);
        uploadView.setOnClickListener(this);
        playView.setOnClickListener(this);
        allQueView.setOnClickListener(this);
    }

    private void updateQue() {
        titleView.setText(allQues.get(position).getQue_topic());
        queTopicView.setText(allQues.get(position).getQue_topic());
        ansAView.setText(allQues.get(position).getAns_a());
        ansBView.setText(allQues.get(position).getAns_b());
        ansCView.setText(allQues.get(position).getAns_c());
        ansDView.setText(allQues.get(position).getAns_d());
        try {
            mMediaPlayer.setDataSource(StuIdHolder.recordBase + allQues.get(position).getPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_ans:
                showRightAns();
                break;
            case R.id.flag_1:
                resetColor(flag1View);
                break;
            case R.id.flag_2:
                resetColor(flag2View);
                break;
            case R.id.flag_3:
                resetColor(flag3View);
                break;
            case R.id.flag_4:
                resetColor(flag4View);
                break;
            case R.id.btnPlay:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                }
                break;
            case R.id.more_que:
                if (!isShow) {
                    isShow = true;
                    mPopupWindow.showAsDropDown(allQueView);
                }
                break;
        }
    }

    private void showRightAns() {
        String rightAns = allQues.get(position).getAns_right();
        if ("A".equals(rightAns)) {
            rightView = rightA;
        } else if ("B".equals(rightAns)) {
            rightView = rightB;
        } else if ("C".equals(rightAns)) {
            rightView = rightC;
        } else {
            rightView = rightD;
        }
        rightView.setImageResource(R.drawable.homework_right);
    }

    private void resetColor(View v) {
        clearColor();
        v.setBackgroundColor(selectedColr);
    }

    private void clearColor() {
        flag1View.setBackgroundColor(Color.TRANSPARENT);
        flag2View.setBackgroundColor(Color.TRANSPARENT);
        flag3View.setBackgroundColor(Color.TRANSPARENT);
        flag4View.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMediaPlayer.reset();
        this.position = position;
        isShow = false;
        mPopupWindow.dismiss();
        updateQue();
    }

    class QuestionAdapter extends BaseAdapter {

        private List<Question> questions;
        private LayoutInflater inflate;

        public QuestionAdapter(List<Question> questions) {
            this.questions = questions;
            inflate = RandListenActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public Question getItem(int position) {
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflate.inflate(R.layout.musiclist_listview_item, null);
            TextView nameView = (TextView) convertView.findViewById(R.id.hwname);
            nameView.setText(getItem(position).getQue_topic());
            ImageView markView = (ImageView) convertView.findViewById(R.id.hwmark);
            markView.setVisibility(View.GONE);
            return convertView;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
