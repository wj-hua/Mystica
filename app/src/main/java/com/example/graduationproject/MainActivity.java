package com.example.graduationproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView logo;
    private Typeface typeface;
    private ImageView gotoRegister;
    private Button forgetPassword;
    private Button userRegister;
    private Button clearAccount;
    private Button clearPassword;
    private ImageView eye;
    private EditText account;
    private EditText password;
    private static final int UPDATE_TEXT1 = 1;
    private static final int UPDATE_TEXT2 = 2;
    private boolean flag1 = true;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = (TextView)findViewById(R.id.Logo);
        typeface = Typeface.createFromAsset(getAssets(),"dengkuan.ttf"); //加载第三方字体
        logo.setTypeface(typeface);//设置第三方字体
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        account = (EditText)findViewById(R.id.account);//账号
        password = (EditText)findViewById(R.id.password);//密码
        gotoRegister=(ImageView) findViewById(R.id.arrowright);//登录
        gotoRegister.setOnClickListener(this);
        forgetPassword = (Button)findViewById(R.id.forgetPassword);//忘记密码
        forgetPassword.setOnClickListener(this);
        userRegister = (Button)findViewById(R.id.userRegister);//注册
        userRegister.setOnClickListener(this);
        clearAccount = (Button)findViewById(R.id.clearAccount);//清除账号按钮
        clearAccount.setOnClickListener(this);
        clearPassword = (Button)findViewById(R.id.clearPassword);//清除密码按钮
        clearPassword.setOnClickListener(this);
        mp = MediaPlayer.create(this,R.raw.sound1);//初始化一个播放器
        eye = (ImageView)findViewById(R.id.eye);
        eye.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userRegister:
                Intent intent = new Intent(MainActivity.this, UserRegister.class);//跳转到注册界面
                startActivity(intent);
                break;
            case R.id.forgetPassword:
                final Dialog dialog = new Dialog(this,R.style.DialogTheme);
                View view = View.inflate(this,R.layout.dialog_custom_layout,null);
                dialog.setContentView(view);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.eye:
                mp.start();//播放音效
               if(flag1){
                   eye.setBackgroundResource(R.drawable.eye1);

                   password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   //getInstance是一个函数，在java中，可以使用这种方式使用单例模式创建类的实例，所谓单例模式就是一个类有且只有一个实例

                   //setTransformationMethod是TextView的一个方法，EditText继承于TextView自然可以使用
                   //这个方法是用来设置其中text的转换显示
                   //接收的参数是TransformationMethod接口，系统给了我们几个默认实现
                   //HideReturnsTransformationMethod隐藏回车
                   //SingleLineTransformationMethod不能用换行回车
                   //PasswordTransformationMethod密码类型
                   password.setSelection(password.getText().toString().trim().length());//设置光标在文本末尾
               }
               else{
                   eye.setBackgroundResource(R.drawable.eye);
                   password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   password.setSelection(password.getText().toString().trim().length());
               }
               flag1 = !flag1;
               password.invalidate();
            case R.id.clearAccount:
                account.setText("");
                break;
            case R.id.clearPassword:
                password.setText("");
                default:
                    break;
        }
    }


}
