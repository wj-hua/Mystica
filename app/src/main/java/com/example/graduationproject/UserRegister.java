package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class UserRegister extends AppCompatActivity {
    @BindView(R.id.countryCode)
    TextView countryCode;
    @BindView(R.id.mobilePhoneNumber)
    EditText mobilePhoneNumber;

    public EventHandler eventHandler;//事件接收器
    private String countryNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobSDK.init(this);
        setContentView(R.layout.activity_userregister);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        init();
    }

    @OnClick(R.id.returnToMainActivity)
    public void returnToMainActivity() {
        finish();
    }

    @OnClick(R.id.countryDown)
    public void openCountryNumber() {
        SMSSDK.getSupportedCountries();
    }

    @OnClick(R.id.sendVerificationcode)
    public void sendVerificationcode() {
        if(!mobilePhoneNumber.getText().toString().trim().equals("")){
            if (checkTel(mobilePhoneNumber.getText().toString().trim())) {
                SMSSDK.getVerificationCode(countryCode.getText().toString(),mobilePhoneNumber.getText().toString());//获取验证码
            }else{
                Toast.makeText(UserRegister.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(UserRegister.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化事件接收器
     */
    private void init() {
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
                                Intent intent = new Intent(UserRegister.this, UserRegisterSecond.class);
                                intent.putExtra("countryCode",countryCode.getText().toString());
                                intent.putExtra("mobilePhoneNumber",mobilePhoneNumber.getText().toString());
                                startActivity(intent);
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            if (result == SMSSDK.RESULT_COMPLETE) {//获取国家列表成功
                                ArrayList<String> countryNameList = new ArrayList<String>();//国家名称集合
                                ArrayList<String> countryNumberList = new ArrayList<String>();//国家号码集合
                                for (Map.Entry<Character, ArrayList<String[]>> ent : SMSSDK.getGroupedCountryList().entrySet()) {
                                    ArrayList<String[]> cl = ent.getValue();
                                    for (String[] paire : cl) {
                                        countryNameList.add(paire[0]);
                                        countryNumberList.add(paire[1]);
                                    }
                                }
                                Intent intent = new Intent(UserRegister.this, CountryNumber.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("countryNameList", (Serializable) countryNameList);
                                bundle.putSerializable("countryNumberList", (Serializable) countryNumberList);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 1);
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
// 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                String it = data.getStringExtra("Message");
                String regEx = "[^0-9]";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(it);
                countryNum = matcher.replaceAll("").trim();
                countryCode.setText("+" + countryNum);
        }
    }

    public boolean checkTel(String tel) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}