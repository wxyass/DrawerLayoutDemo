package com.wxyass.drawerlayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wxyass.drawerlayoutdemo.fragment.NewsFragment;
import com.wxyass.drawerlayoutdemo.fragment.SubscriptionFragment;
import com.wxyass.drawerlayoutdemo.left.ContentAdapter;
import com.wxyass.drawerlayoutdemo.left.ContentModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ContentModel> mList = new ArrayList<>();;
    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout rightLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mListView = (ListView) findViewById(R.id.left_listview);
        ContentAdapter adapter = new ContentAdapter(this,mList);
        mListView.setAdapter(adapter);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        rightLayout = (RelativeLayout) findViewById(R.id.right);

        findViewById(R.id.leftmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        findViewById(R.id.rightmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //模拟两条数据

                    case 0://新闻
                        //切换新闻的Fragment
                        replaceFragment(new NewsFragment());
                        break;
                    case 1://订阅
                        //切换订阅的Fragment
                        replaceFragment(new SubscriptionFragment());
                        break;

                    default:
                        break;
                }
                //点击任一项item项，都关闭左侧菜单
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });


        /**切换listview的item背景色，选中为红色；否则为黑色的方式二。当点击item的时候，就会触发子项item的焦点*/
        mListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == true){
                    //获得焦点
                    mListView.setSelector(android.R.color.holo_red_light) ;
                }   else{
                    //失去焦点
                    mListView.setSelector(android.R.color.black) ;
                }
            }
        });

        rightLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 关闭右侧菜单
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });


    }

    private void initData() {

        mList.add(new ContentModel(R.drawable.chart_5_8, "新闻", 1));
        mList.add(new ContentModel(R.drawable.chart_3_8, "订阅", 2));
        mList.add(new ContentModel(R.drawable.chart_3_4, "图片", 3));
        mList.add(new ContentModel(R.drawable.chart_1_8, "视频", 4));
        mList.add(new ContentModel(R.drawable.chart_1_4, "跟帖", 5));
        mList.add(new ContentModel(R.drawable.chart_1_2, "投票", 6));
    }


    public void replaceFragment(Fragment fragment){
        //1、拿到FragmentManager管理器
        FragmentManager manager = getSupportFragmentManager();

        //2、获取事物
        FragmentTransaction transaction = manager.beginTransaction();

        //3、fragment的替换
        transaction.replace(R.id.content,fragment);

        //4、提交事物
        transaction.commit();
    }



}
