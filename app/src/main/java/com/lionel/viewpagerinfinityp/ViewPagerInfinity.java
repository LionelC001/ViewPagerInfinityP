package com.lionel.viewpagerinfinityp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

public class ViewPagerInfinity extends FrameLayout implements TimerHelper.ITimerHelperListener {
    private final Context context;
    private ViewPager viewPager;
    private LinearLayout indicator;
    private List<String> data;
    private int dataSize;
    private TimerHelper bannerTimerHelper;
    private int currentPagePos = 1;


    public ViewPagerInfinity(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        inflate(context, R.layout.layout_view_pager_infinity, this);

        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.layoutIndicator);
    }


    public void showData(@NonNull List<String> data) {
        this.data = data;
        addLoopPage();
        setIndicator();
        setAutoScroll();

        StoreDetailBannerViewPagerAdapter adapter = new StoreDetailBannerViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);
        viewPager.addOnPageChangeListener(adapter);
    }

    private void addLoopPage() {
        data.add(0, data.get(data.size() - 1));
        data.add(data.get(1));
        this.dataSize = data.size();
    }

    private void setAutoScroll() {
        if (bannerTimerHelper == null && dataSize - 2 > 1) {
            bannerTimerHelper = new TimerHelper((Activity) context, this);
            bannerTimerHelper.startTimerTask(3000, 3000);
        }
    }

    @Override
    public void onTimeIsUp() {
        viewPager.setCurrentItem(currentPagePos + 1, true);
    }

    private void setIndicator() {
        float density = context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) density * 16, (int) density * 16);
        params.setMarginEnd((int) density * 8);

        for (int i = 0; i < dataSize; i++) {
            ImageView dot = new ImageView(context);
            if (i == 0 || i == dataSize - 1) {  // hide the first and the last dot
                dot.setVisibility(GONE);
            } else if (i == 1) {
                dot.setBackgroundResource(R.drawable.bg_indicator_selected);
            } else {
                dot.setBackgroundResource(R.drawable.bg_indicator_no_selected);
            }
            indicator.addView(dot, params);
        }
    }

    public void onDestroy() {
        if (bannerTimerHelper != null) {
            bannerTimerHelper.cancelTimer();
        }
    }


    private class StoreDetailBannerViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        StoreDetailBannerViewPagerAdapter() {
        }

        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, container, false);
            ImageView imgView = view.findViewById(R.id.img);
            Glide.with(context)
                    .load(data.get(position))
                    .into(imgView);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }


        @Override
        public void onPageScrollStateChanged(int state) {
            if (state != ViewPager.SCROLL_STATE_IDLE) return;

            if (currentPagePos == 0) {
                viewPager.setCurrentItem(dataSize - 2, false);
            } else if (currentPagePos == dataSize - 1) {
                viewPager.setCurrentItem(1, false);
            }
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int pos) {
            updateIndicate(pos);
            currentPagePos = pos;
        }

        private void updateIndicate(int pos) {
            View oldSelectedDot = indicator.getChildAt(currentPagePos);
            View newSelectedDot;
            if (pos == 0) {
                newSelectedDot = indicator.getChildAt(dataSize - 1);
            } else if (pos == dataSize - 1) {
                newSelectedDot = indicator.getChildAt(1);
            } else {
                newSelectedDot = indicator.getChildAt(pos);
            }

            oldSelectedDot.setBackgroundResource(R.drawable.bg_indicator_no_selected);
            newSelectedDot.setBackgroundResource(R.drawable.bg_indicator_selected);
        }
    }
}
