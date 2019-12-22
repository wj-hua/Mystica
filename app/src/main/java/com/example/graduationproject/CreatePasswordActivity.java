package com.example.graduationproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.graduationproject.greendao.Password;
import com.example.graduationproject.greendao.PasswordDao;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePasswordActivity extends AppCompatActivity {
    ActionBar actionBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_password_name)
    EditText et_password_name;
    @BindView(R.id.et_password_user_name)
    EditText et_password_user_name;
    @BindView(R.id.et_password_password)
    EditText et_password_password;
    @BindView(R.id.et_password_note)
    EditText et_password_note;

    String password_name, password_user_name, password_password, password_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让左边导航按钮显示出来
            actionBar.setTitle("创建密码");
        }
    }

    @OnClick({R.id.bt_save_password, R.id.bt_create_password})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_save_password:
                password_name = et_password_name.getText().toString();
                password_user_name = et_password_user_name.getText().toString();
                password_password = et_password_password.getText().toString();
                password_note = et_password_note.getText().toString();
                PasswordDao mPasswordDao = MyApplication.getInstances().getDaoSession().getPasswordDao();
                Password password = new Password();
                password.setPassword_name(password_name);
                password.setPassword_userName(password_user_name);
                password.setPassword_password(password_password);
                password.setPassword_note(password_note);
                mPasswordDao.insert(password);
                break;
            case R.id.bt_create_password:
                new XPopup.Builder(this)
                        .asCustom(new XpopupTest(this))
                        .show();
        }
    }
}
