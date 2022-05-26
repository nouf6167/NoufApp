package com.nouf.noufapp;

import java.util.ArrayList;

public interface ResponseListener {
    void onSuccess(String response, ArrayList<StudentObject> records);
    void onFailed(String error);
}
