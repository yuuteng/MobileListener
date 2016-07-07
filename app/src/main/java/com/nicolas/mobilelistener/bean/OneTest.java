package com.nicolas.mobilelistener.bean;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class OneTest {
    private String test_id;
    private String test_topic;
    private String pub_time;
    private String teacher_name;
    private int is_complete;

    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getTest_topic() {
        return test_topic;
    }

    public void setTest_topic(String test_topic) {
        this.test_topic = test_topic;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public String toString() {
        return "OneTest{" +
                "test_id='" + test_id + '\'' +
                ", test_topic='" + test_topic + '\'' +
                ", pub_time='" + pub_time + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                ", is_complete=" + is_complete +
                '}';
    }
}
