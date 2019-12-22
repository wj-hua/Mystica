package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class UserRegisterSecond extends AppCompatActivity {
    @BindView(R.id.landing_btn)
    Button landing;
    @BindView(R.id.validateNum1)
    EditText validateNum1;
    @BindView(R.id.validateNum2)
    EditText validateNum2;
    @BindView(R.id.validateNum3)
    EditText validateNum3;
    @BindView(R.id.validateNum4)
    EditText validateNum4;
    @BindView(R.id.validateNum5)
    EditText validateNum5;
    @BindView(R.id.validateNum6)
    EditText validateNum6;


    public EventHandler eventHandler; //事件接收器
    String s1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_second);
        MobSDK.init(this);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        final String countryCode = intent.getStringExtra("countryCode");
        final String mobilePhoneNumber = intent.getStringExtra("mobilePhoneNumber");
        TextWatcher tw = new TextWatcher() {//TextWatcher是一个监听字符变化类
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    if (validateNum1.isFocused()) {
                        validateNum1.clearFocus();
                        validateNum2.requestFocus();
                    } else if (validateNum2.isFocused()) {
                        validateNum2.clearFocus();
                        validateNum3.requestFocus();
                    } else if (validateNum3.isFocused()) {
                        validateNum3.clearFocus();
                        validateNum4.requestFocus();
                    } else if (validateNum4.isFocused()) {
                        validateNum4.clearFocus();
                        validateNum5.requestFocus();
                    } else if (validateNum5.isFocused()) {
                        validateNum5.clearFocus();
                        validateNum6.requestFocus();
                    } else if (validateNum6.isFocused()) {
                        s1 = validateNum1.getText().toString() + validateNum2.getText().toString() +
                                validateNum3.getText().toString() + validateNum4.getText().toString() +
                                validateNum5.getText().toString() + validateNum6.getText().toString();

//                        SMSSDK.submitVerificationCode(countryCode, mobilePhoneNumber, s1);
                    }
                }
            }
        };

        validateNum1.requestFocus();
        validateNum1.addTextChangedListener(tw);
        validateNum2.addTextChangedListener(tw);
        validateNum3.addTextChangedListener(tw);
        validateNum4.addTextChangedListener(tw);
        validateNum5.addTextChangedListener(tw);
        validateNum6.addTextChangedListener(tw);

        landing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode(countryCode.trim(), mobilePhoneNumber.trim(), s1.trim());
            }
        });

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                Intent intent1 = new Intent(UserRegisterSecond.this,PasswordActivity.class);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(getApplicationContext(),"验证失败",Toast.LENGTH_SHORT).show();
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
