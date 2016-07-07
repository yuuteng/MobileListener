package com.nicolas.mobilelistener.bean;

import java.util.List;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class AllTest {
    private boolean message;
    private List<OneTest> result;

    public boolean getMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public List<OneTest> getResult() {
        return result;
    }

    public void setResult(List<OneTest> result) {
        this.result = result;
    }
}
