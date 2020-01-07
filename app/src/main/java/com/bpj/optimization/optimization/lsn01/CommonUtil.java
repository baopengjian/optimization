package com.bpj.optimization.optimization.lsn01;

import android.content.Context;

/**
 * Created by Ray on 2020-1-7.
 */
public class CommonUtil {

    private static CommonUtil instance;
    private Context context;

    private CommonUtil(Context context) {
        this.context = context;
    }

    public static CommonUtil getInstance(Context context){
        if(instance == null){
            instance = new CommonUtil(context);
        }
        return instance;
    }
}
