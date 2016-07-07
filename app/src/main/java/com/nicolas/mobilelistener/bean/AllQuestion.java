package com.nicolas.mobilelistener.bean;

import java.util.List;

/**
 * Created by Nikolas on 2015/9/15.
 */
public class AllQuestion {
    private boolean message;
    private List<Question> result;

    public boolean getMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }
}
