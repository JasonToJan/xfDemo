package com.xingfu.xfdemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class PrePareActivity extends AppCompatActivity {

    private static final int PAGE_NUM = 3;

    private ViewPager previewPager;

    private TextView btnKnow;
    private ImageView btnClose;
    private int currentItem=0;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            currentItem=arg0;
            if (arg0 == PAGE_NUM - 1) {
                btnKnow.setText("开始拍摄");
                btnKnow.setVisibility(View.VISIBLE);

            } else {
                btnKnow.setText("下一步");
                btnKnow.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepare_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        previewPager =(ViewPager)findViewById(R.id.pa_vp_preview);
        previewPager.setOnPageChangeListener(pageChangeListener);

        btnKnow = (TextView) findViewById(R.id.pa_tv_know);
        btnClose=(ImageView) findViewById(R.id.pa_iv_close);

        btnKnow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(btnKnow.getText().toString().equals("开始拍摄")){
                    finish();
                }else{
                    previewPager.setCurrentItem(++currentItem);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createStepView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    @Override
    public void onDestroy() {
        //SharedPreferencesUtils.setParam(this,Constants.isFirstLaunch,false);
        super.onDestroy();
    }

    /**
     * 创建拍摄准备图片
     */
    private void createStepView() {
        ArrayList<View> views = new ArrayList<View>();
        int images[] = new int[]{R.drawable.prepare1,
                R.drawable.prepare2,R.drawable.prepare3};
        GifImageView gifImageView;
        for (int i = 0; i < images.length; i++) {
            View view = LayoutInflater.from(PrePareActivity.this).inflate(
                    R.layout.item_prepare_layout, null);
            gifImageView = (GifImageView) view.findViewById(R.id.item_gif_imageview);
            gifImageView.setImageResource(images[i]);
            gifImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //view.setBackgroundColor(0xFFFFFFFF);
            views.add(view);
        }
        previewPager.setAdapter(new SetepAdapter(views));

    }

    static class SetepAdapter extends PagerAdapter {

        private List<View> imageViews;

        public SetepAdapter(List<View> imageViews) {
            this.imageViews = imageViews;
        }


        @Override
        public int getCount() {

            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));

        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;

            container.addView(imageViews.get(position), 0);

            return imageViews.get(position);
        }

    }
}
