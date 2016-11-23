package com.sds.study.socketclient;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    Toolbar toolbar;
    MyOpenHelper myOpenHelper;
    ChatDAO chatDAO;
    Chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        toolbar=(Toolbar)findViewById(R.id.my_toolbar);
        /*Appbar로 적용되는 시점!*/
        setSupportActionBar(toolbar);
        init();
    }
    /*데이터 베이스 초기화 및 SQLiteDatabase 객체 얻기*/
    public void init(){
        myOpenHelper = new MyOpenHelper(this);

        /*아래의 메서드가 호출될때, onCreate, onUpgrade가 호출된다.*/
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();
        chatDAO = new ChatDAO(db);

        /*2번째 프레그먼트 접근하여, 알맞는 값 대입하기*/
        ConfigFragment configFragment=(ConfigFragment) myPagerAdapter.fragments[1];

        //configFragment.setData(chat);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*회원 에니메이션 효과주기*/
    public void setRotate(View view){
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.ani_config);
        view.startAnimation(ani);
    }

    /*매뉴를 선택하면*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_chat :
                viewPager.setCurrentItem(0); break;
            case R.id.menu_config :
                viewPager.setCurrentItem(1);
             //setRotate(item.getActionView());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*페이지 관련 이벤트*/
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    /*사용자가 해당 페이지를 확정 지을때(Commit시)*/
    public void onPageSelected(int position) {
        if(position==1) {//설정 화면 일때만
            ConfigFragment configFragment=(ConfigFragment) myPagerAdapter.fragments[position];
            chat=chatDAO.select(0);
            configFragment.setData(chat);
        }
    }

    public void onPageScrollStateChanged(int state) {
    }
}
