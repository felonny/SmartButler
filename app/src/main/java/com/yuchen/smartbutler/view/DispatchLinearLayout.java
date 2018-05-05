package com.yuchen.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 项目名: SmartButlers
 * 包名:  com.yuchen.smartbutler.view
 * 文件名: DispatchLinearLayout
 * Created by tangyuchen on 18/5/5.
 * 描述: 事件分发
 */

public class DispatchLinearLayout extends LinearLayout {

    private DispatcheKeyEventListener dispatcheKeyEventListener;

    public DispatcheKeyEventListener getDispatcheKeyEventListener() {
        return dispatcheKeyEventListener;
    }

    public void setDispatcheKeyEventListener(DispatcheKeyEventListener dispatcheKeyEventListener) {
        this.dispatcheKeyEventListener = dispatcheKeyEventListener;
    }

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static interface DispatcheKeyEventListener{
       boolean dispatcheKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空，说明调用了，去获取事件
        if(dispatcheKeyEventListener!=null){
            return dispatcheKeyEventListener.dispatcheKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
