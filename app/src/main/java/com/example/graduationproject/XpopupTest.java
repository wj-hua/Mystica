package com.example.graduationproject;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XpopupTest extends CenterPopupView {

    @BindView(R.id.xpopup_password)
    TextView xpopup_password;

    public XpopupTest(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.xpopup_center_create_password;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

}
