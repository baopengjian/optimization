package com.bpj.optimization.optimization.lsn06;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Ray on 2020-1-22.
 */
public class Card {

    public int x;
    public int width;
    public int height;
    public Bitmap bitmap;

    public Card(Resources res, int resId, int x) {
        this.bitmap = BitmapFactory.decodeResource(res,resId);
        this.x = x;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }
}
