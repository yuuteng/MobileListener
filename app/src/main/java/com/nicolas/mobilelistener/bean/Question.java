package com.nicolas.mobilelistener.bean;

import java.io.Serializable;

/**
 * Created by Nikolas on 2015/9/15.
 */
public class Question implements Serializable {
    private int que_id;
    private String que_topic;
    private String ans_a;
    private String ans_b;
    private String ans_c;
    private String ans_d;
    private String ans_right;
    private String path;

    public String getQue_topic() {
        return que_topic;
    }

    public void setQue_topic(String que_topic) {
        this.que_topic = que_topic;
    }

    public String getAns_a() {
        return ans_a;
    }

    public void setAns_a(String ans_a) {
        this.ans_a = ans_a;
    }

    public String getAns_b() {
        return ans_b;
    }

    public void setAns_b(String ans_b) {
        this.ans_b = ans_b;
    }

    public String getAns_c() {
        return ans_c;
    }

    public void setAns_c(String ans_c) {
        this.ans_c = ans_c;
    }

    public String getAns_d() {
        return ans_d;
    }

    public void setAns_d(String ans_d) {
        this.ans_d = ans_d;
    }

    public String getAns_right() {
        return ans_right;
    }

    public void setAns_right(String ans_right) {
        this.ans_right = ans_right;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getQue_id() {
        return que_id;
    }

    public void setQue_id(int que_id) {
        this.que_id = que_id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "que_topic='" + que_topic + '\'' +
                ", ans_a='" + ans_a + '\'' +
                ", ans_b='" + ans_b + '\'' +
                ", ans_c='" + ans_c + '\'' +
                ", ans_d='" + ans_d + '\'' +
                ", ans_right='" + ans_right + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
