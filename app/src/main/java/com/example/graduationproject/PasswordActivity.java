package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends AppCompatActivity{
    ActionBar actionBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//让左边导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.mipmap.threelines);//设置左边导航按钮的图片
            actionBar.setTitle("密码");//标题默认为密码
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.toolbar_search://跳转到搜索活动
                new XPopup.Builder(this)
                        .asCustom(new XpopupTest(this))
                        .show();
                break;

            case R.id.toolbar_sort:
                new XPopup.Builder(this)
                        .atView(toolbar)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"影音视听", "实用工具", "聊天社交", "图书阅读", "时尚购物",
                                "摄影摄像", "学习教育", "旅行交通", "金融理财", "娱乐消遣", "新闻资讯", "居家生活",
                                "体育运动", "医疗健康", "效率办公"}, new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
                break;
                default:
                    break;
        }
        return true;
    }

    @OnClick(R.id.fab)
    public void OnPasswordActivityClick(){
        startActivity(new Intent(PasswordActivity.this, CreatePasswordActivity.class));
    }
}
