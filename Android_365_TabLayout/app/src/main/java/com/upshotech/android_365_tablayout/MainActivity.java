package com.upshotech.android_365_tablayout;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Tab1"));
        tabs.addTab(tabs.newTab().setText("Tab2"));
        tabs.addTab(tabs.newTab().setText("Tab3"));
        tabs.addTab(tabs.newTab().setText("Tab4"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                final int colorTo = getRandomColor();

                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                        colorFrom, colorTo);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        final int color = (int) valueAnimator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabs.setBackgroundColor(color);
                        fab.setBackgroundTintList(ColorStateList.valueOf(color));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }
                });

                colorAnimation.start();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SimpleViewpagerAdapter());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    private int getRandomColor() {
        Random rnd = new Random();
        final int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

    /**
     * Viewpager adapter
     */
    private class SimpleViewpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabs.getTabCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView content = new TextView(MainActivity.this);
            container.addView(content);
            return content;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
