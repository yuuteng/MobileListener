package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.QuestionService;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class AnsActivity extends Activity implements Callback<AllQuestion>, View.OnClickListener, MediaPlayer.OnPreparedListener {

    private TextView titleView;
    private View nextView;
    private TextView queTopicView;
    private TextView ansAView;
    private TextView ansBView;
    private TextView ansCView;
    private TextView ansDView;
    private ImageView rightA, rightB, rightC, rightD;
    private ImageView rightView;
    private View uploadView;
    private View flag1View, flag2View, flag3View, flag4View;

    private ProgressDialog loadingView;

    private int selectedColr = Color.parseColor("#0288D1");

    private RestAdapter restAdapter;
    private QuestionService questionService;
    private List<Question> allQues;
    private int currentIndex = 0;
    private String currentAns = "";

    private MediaPlayer mMediaPlayer;

    private CheckAnsCallBack checkAnsCallBack;
    private String testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ans_activity);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (mMediaPlayer == null) {
            restAdapter = ((ListenerApplication) getApplication()).getAdapter();
            questionService = restAdapter.create(QuestionService.class);
            //初始化音频播放设置
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);

            Intent intent = getIntent();
            testId = intent.getStringExtra("test_id");
            String testTopic = intent.getStringExtra("test_topic");
            titleView.setText(testTopic);
            questionService.getQueById(testId, this);
            checkAnsCallBack = new CheckAnsCallBack();
        } else {
            mMediaPlayer.start();
        }
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.txtLogin);
        nextView = findViewById(R.id.next_que);
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
//        playView = (ImageView) findViewById(R.id.btnPlay);

        loadingView = new ProgressDialog(this);
        loadingView.setMessage("正在从服务器生成题目");
        loadingView.show();

        nextView.setOnClickListener(this);
        flag1View.setOnClickListener(this);
        flag2View.setOnClickListener(this);
        flag3View.setOnClickListener(this);
        flag4View.setOnClickListener(this);
        uploadView.setOnClickListener(this);
//        playView.setOnClickListener(this);
    }

    @Override
    public void success(AllQuestion questions, Response response) {
        loadingView.dismiss();
        if (questions.getMessage()) {
            allQues = questions.getResult();
            updateQue(currentIndex);
        } else {
            Toast.makeText(this, "题目获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show();
        loadingView.dismiss();
    }

    private void updateQue(int pos) {
        Question currentQue = allQues.get(pos);
        queTopicView.setText(currentQue.getQue_topic());
        ansAView.setText(currentQue.getAns_a());
        ansBView.setText(currentQue.getAns_b());
        ansCView.setText(currentQue.getAns_c());
        ansDView.setText(currentQue.getAns_d());
        try {
            mMediaPlayer.setDataSource(StuIdHolder.recordBase + currentQue.getPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_que:
                nextView.setVisibility(View.GONE);
                rightView.setImageBitmap(null);
                clearColor();
                updateQue(++currentIndex);
                break;
            case R.id.upload_ans:
                if ("".equals(currentAns)) {
                    Toast.makeText(this, "请选择答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (++currentIndex >= allQues.size()) {
                    questionService.completeTest(StuIdHolder.userId, testId, new Callback<Map<String, Object>>() {
                        @Override
                        public void success(Map<String, Object> stringObjectMap, Response response) {
                        }
                        @Override
                        public void failure(RetrofitError error) {
                        }
                    });
                }
                showRightAns();
                mMediaPlayer.reset();
                questionService.checkQueAns(allQues.get(currentIndex).getQue_id(), currentAns, StuIdHolder.userId, checkAnsCallBack);
                break;
            case R.id.flag_1:
                resetColor(flag1View);
                currentAns = "A";
                break;
            case R.id.flag_2:
                resetColor(flag2View);
                currentAns = "B";
                break;
            case R.id.flag_3:
                resetColor(flag3View);
                currentAns = "C";
                break;
            case R.id.flag_4:
                resetColor(flag4View);
                currentAns = "D";
                break;
//            case R.id.btnPlay:
//                break;
        }
    }

    private void showRightAns() {
        String rightAns = allQues.get(currentIndex).getAns_right();
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

    public void handler() {
        if (currentIndex < allQues.size()) {
            nextView.setVisibility(View.VISIBLE);
        } else if (currentIndex == allQues.size() - 1) {
            Toast.makeText(this, "您已完成该试卷所有题目", Toast.LENGTH_SHORT).show();
        }
    }

    class CheckAnsCallBack implements Callback<Map<String, Object>> {
        @Override
        public void failure(RetrofitError error) {
            Logger.d(error.getMessage());
            AnsActivity.this.handler();
        }

        @Override
        public void success(Map<String, Object> strings, Response response) {
            AnsActivity.this.handler();
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