package com.example.pcconfighelpercoursework.utils;

import android.content.res.Resources;

import com.example.pcconfighelpercoursework.R;

public class AdvisorForAssembleClass {
    static Resources resources;

    public AdvisorForAssembleClass(Resources resources) {
        AdvisorForAssembleClass.resources = resources;
    }

    static void adviceForTask(String task){
        if(task.equals(resources.getString(R.string.task_video_edit))){

        }
    }
}
