package com.oliveoa.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;

import java.util.Timer;
import java.util.TimerTask;

public class AboutusActivity extends AppCompatActivity {

    private ImageView mArrowImageView,mArrowImageView1;
    private LinearLayout btn_appintrolayout,btn_teamintrolayout,text_appintrolayout,text_teamintrolayout;

    private long duration = 350;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;
    private String TAG = "xujun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initView();
    }
    private void initView(){
       ImageView back =(ImageView)findViewById(R.id.info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutusActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//        btn_appintrolayout = (LinearLayout) findViewById(R.id.title_appintro);
//        btn_teamintrolayout = (LinearLayout) findViewById(R.id.title_teamintro);
//        text_appintrolayout = (LinearLayout) findViewById(R.id.content_appintro);
//        text_appintrolayout = (LinearLayout) findViewById(R.id.content_appintro);
//        mArrowImageView = (ImageView) findViewById(R.id.turn_over_icon);
//        mArrowImageView1 = (ImageView)findViewById(R.id.turn_over_icon1);
//
//        btn_appintrolayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rotateArrow();
//                collapse(text_appintrolayout);
//            }
//        });
//        //mNumberTextView.setBackgroundResource(R.drawable.circle);
//        //Drawable circleShape = createCircleShape(Color.BLACK);
//        //mNumberTextView.setBackgroundDrawable(circleShape);
//
//
//        btn_teamintrolayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rotateArrow1();
//                collapse(text_teamintrolayout);
//            }
//        });

//    }


    /**
     * 若使用这个方法，强制layoutParams must be RelativeLayout.LayoutParams，防止在某些情况下出现错误
     *
     * //@param view
     */
//    public void setContent(View view) {
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//
//        if (layoutParams == null) {
//            layoutParams = new RelativeLayout.LayoutParams(
//                    LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        }
//
//        if (!(layoutParams instanceof RelativeLayout.LayoutParams)) {
//            throw new IllegalStateException("layoutParams must be RelativeLayout.LayoutParams ");
//        }
//
//        view.setLayoutParams(layoutParams);
//        mContentRelativeLayout.addView(view);
//    }

    /**
     *   旋转第一个箭头
     */
//    public void rotateArrow() {
//        int degree = 0;
//        if (mArrowImageView.getTag() == null || mArrowImageView.getTag().equals(true)) {
//            mArrowImageView.setTag(false);
//            degree = -180;
//            expand(text_appintrolayout);
//        } else {
//            degree = 0;
//            mArrowImageView.setTag(true);
//            collapse(text_appintrolayout);
//        }
//        mArrowImageView.animate().setDuration(duration).rotation(degree);
//    }
    /**
     *   旋转第二个箭头
     */
//    public void rotateArrow1() {
//        int degree = 0;
//        if (mArrowImageView1.getTag() == null || mArrowImageView1.getTag().equals(true)) {
//            mArrowImageView1.setTag(false);
//            degree = -180;
//            expand(text_teamintrolayout);
//        } else {
//            degree = 0;
//            mArrowImageView1.setTag(true);
//            collapse(text_teamintrolayout);
//        }
//        mArrowImageView1.animate().setDuration(duration).rotation(degree);
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        parentWidthMeasureSpec = widthMeasureSpec;
//        parentHeightMeasureSpec = heightMeasureSpec;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//    }



//
//    // 展开
//    private void expand(final View view) {
//
//        view.setVisibility(View.VISIBLE);
//        int childWidthMode = getMode(parentWidthMeasureSpec);
//        int childHeightMode = getMode(parentHeightMeasureSpec);
//        view.measure(childWidthMode, childHeightMode);
//        final int measuredWidth = view.getMeasuredWidth();
//        //final int measuredHeight = view.getMeasuredHeight();
//
//        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.setDuration(duration);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float precent = animation.getAnimatedFraction();
//                int width = (int) (measuredWidth * precent);
//                setWidth(view, width);
//                if (precent == 1) {
//                    valueAnimator.removeAllUpdateListeners();
//                }
//            }
//        });
//        valueAnimator.start();
//
//    }
//
//    private int getMode(int parentMeasureSpec) {
//
//        if (parentMeasureSpec == View.MeasureSpec.EXACTLY) {
//            return View.MeasureSpec.AT_MOST;
//        } else if (parentMeasureSpec == View.MeasureSpec.AT_MOST) {
//            return View.MeasureSpec.AT_MOST;
//        } else {
//            return parentMeasureSpec;
//        }
//    }
//
//    private void setWidth(View view, int width) {
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.width = width;
//        view.setLayoutParams(layoutParams);
//        view.requestLayout();
//    }
//
//    // 折叠
//    private void collapse(final View view) {
//        //final int measuredHeight = view.getMeasuredHeight();
//        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.setDuration(duration);
//        final int viewMeasuredWidth = view.getMeasuredWidth();
//
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//                float precent = animation.getAnimatedFraction();
//                Log.i(TAG, "onAnimationUpdate: precent" + precent);
//                int width = (int) (viewMeasuredWidth - viewMeasuredWidth * precent);
//                setWidth(view, width);
////                动画执行结束的时候，设置View为View.GONE，同时移除监听器
//                if (precent == 1) {
//                    view.setVisibility(View.GONE);
//                    valueAnimator.removeAllUpdateListeners();
//                }
//            }
//        });
//        valueAnimator.start();
//    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitBy2Click();
//            return true;
//            //调用双击退出函数
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//    /**
//     * 双击退出函数
//     */
//    private static Boolean isESC = false;
//
//    private void exitBy2Click() {
//        Timer tExit ;
//        if (!isESC) {
//            isESC = true; // 准备退出
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isESC = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//        } else {
//            System.exit(0);
//        }
//    }
//}
