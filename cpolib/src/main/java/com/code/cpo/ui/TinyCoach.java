package com.code.cpo.ui;

import android.animation.Animator;
import android.app.Application;
import android.app.Service;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.cpo.fps.Calculation;
import com.code.cpo.fps.FPSConfig;
import com.code.cpo.http.HttpTime;
import com.code.cpo.utils.DensityUtil;
import com.code.cpolib.R;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class TinyCoach {
    private FPSConfig fpsConfig;
    private TextView fps_value;
    private View rootView, fps_lay, indication;
    private WindowManager windowManager;
    private int shortAnimationDuration = 200, longAnimationDuration = 700;
    private RecyclerView http_list;
    private HttpAdapter adapter;
    private List<HttpTime> httpTimes = new ArrayList<>();
    // detect double tap so we can hide tinyDancer
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // hide but don't remove view
            hide(false);
            return super.onDoubleTap(e);
        }
    };

    public TinyCoach(Application context, FPSConfig config) {
        fpsConfig = config;
        initShowView(context);
    }

    private void initShowView(Application context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.cpolib_view, null);
        fps_lay = rootView.findViewById(R.id.fps_lay);
        http_list = rootView.findViewById(R.id.http_list);
        http_list.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HttpAdapter(context, httpTimes);
        http_list.setAdapter(adapter);
        indication = rootView.findViewById(R.id.indication);
        //create meter view
        fps_value = rootView.findViewById(R.id.fps_value);

        //set initial fps value....might change...
        fps_value.setText((int) fpsConfig.refreshRate + "");

        // grab window manager and add view to the window
        windowManager = (WindowManager) rootView.getContext().getSystemService(Service.WINDOW_SERVICE);

//        int minWidth = rootView.getLineHeight()
//                + meterView.getTotalPaddingTop()
//                + meterView.getTotalPaddingBottom()
//                + (int) meterView.getPaint().getFontMetrics().bottom;


        addViewToWindow(context, rootView);
        fps_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visable = http_list.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                http_list.setVisibility(visable);
                showAnimation(indication, visable == View.VISIBLE ? 0 : 180, visable == View.VISIBLE ? 180 : 360);
            }
        });
    }

    public void showAnimation(View view, int startDegrees, int degrees) {
        float centerX = view.getWidth() / 2.0f;
        float centerY = view.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(startDegrees, degrees, centerX, centerY);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(200);
        //动画执行完毕后是否停在结束时的角度上
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    private void addViewToWindow(Application context, View view) {

        int permissionFlag = PermissionCompat.getFlag();

        WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                DensityUtil.dip2px(context, fpsConfig.width),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                permissionFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        paramsF.alpha = fpsConfig.alpha;
        // configure starting coordinates
        if (fpsConfig.xOrYSpecified) {
            paramsF.x = fpsConfig.startingXPosition;
            paramsF.y = fpsConfig.startingYPosition;
            paramsF.gravity = FPSConfig.DEFAULT_GRAVITY;
        } else if (fpsConfig.gravitySpecified) {
            paramsF.x = 0;
            paramsF.y = 0;
            paramsF.gravity = fpsConfig.startingGravity;
        } else {
            paramsF.gravity = FPSConfig.DEFAULT_GRAVITY;
            paramsF.x = fpsConfig.startingXPosition;
            paramsF.y = fpsConfig.startingYPosition;
        }

        // add view to the window
        windowManager.addView(view, paramsF);

        // create gesture detector to listen for double taps
        GestureDetector gestureDetector = new GestureDetector(view.getContext(), simpleOnGestureListener);

        // attach touch listener
        view.setOnTouchListener(new DancerTouchListener(paramsF, windowManager, gestureDetector));
        // disable haptic feedback
        view.setHapticFeedbackEnabled(false);

        // show the meter
        show();
    }

    public void updateFps(FPSConfig fpsConfig, List<Long> dataSet) {

        List<Integer> droppedSet = Calculation.getDroppedSet(fpsConfig, dataSet);
        AbstractMap.SimpleEntry<Calculation.Metric, Long> answer = Calculation.calculateMetric(fpsConfig, dataSet, droppedSet);

        if (answer.getKey() == Calculation.Metric.BAD) {
            fps_value.setBackgroundResource(R.drawable.fpsmeterring_bad);
        } else if (answer.getKey() == Calculation.Metric.MEDIUM) {
            fps_value.setBackgroundResource(R.drawable.fpsmeterring_medium);
        } else {
            fps_value.setBackgroundResource(R.drawable.fpsmeterring_good);
        }

        fps_value.setText(answer.getValue() + "");
    }

    public void destroy() {
        rootView.setOnTouchListener(null);
        hide(true);
    }

    public void show() {
        rootView.setAlpha(0f);
        rootView.setVisibility(View.VISIBLE);
        rootView.animate()
                .alpha(1f)
                .setDuration(longAnimationDuration)
                .setListener(null);
    }

    public void hide(final boolean remove) {
        rootView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rootView.setVisibility(View.GONE);
                        if (remove) {
                            windowManager.removeView(rootView);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

    }

    public void updateHttp(HttpTime httpTime) {
        httpTimes.add(0, httpTime);
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            http_list.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
