package com.bpj.optimization.optimization.lsn06;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bpj.optimization.optimization.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2020-1-22.
 */
public class CardsView extends View {

    //图片与图片之间的间距
    private int mCardSpacing = 150;
    //图片与左侧距离的记录
    private int mCardLeft = 10;

    private List<Card> mDroidCards = new ArrayList<>();

    private Paint paint = new Paint();

    public CardsView(Context context) {
        super(context);
        initCards();
    }

    public CardsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCards();
    }

    public CardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCards();
    }

    /**
     * 初始化卡片集合
     */
    protected void initCards() {
        Resources res = getResources();
        mDroidCards.add(new Card(res, R.drawable.alex, mCardLeft));

        mCardLeft += mCardSpacing;
        mDroidCards.add(new Card(res, R.drawable.claire, mCardLeft));

        mCardLeft += mCardSpacing;
        mDroidCards.add(new Card(res, R.drawable.kathryn, mCardLeft));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Card c : mDroidCards) {
            drawCard(canvas, c);
        }
        invalidate();
    }

    private void drawCard(Canvas canvas, Card c) {
        canvas.drawBitmap(c.bitmap, c.x, 0f, paint);
    }


}
