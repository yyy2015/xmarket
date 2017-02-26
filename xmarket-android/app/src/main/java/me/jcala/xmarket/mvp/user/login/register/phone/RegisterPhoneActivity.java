package me.jcala.xmarket.mvp.user.login.register.phone;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jcala.xmarket.R;
import me.jcala.xmarket.di.modules.RegisterNextModule;
import me.jcala.xmarket.mvp.a_base.BaseActivity;
import me.jcala.xmarket.mvp.user.login.register.next.RegisterNextPresenter;

/**
 * Phone validation activity
 * @author cuihao
 */
public class RegisterPhoneActivity extends BaseActivity implements RegisterPhoneView{

    @BindView(R.id.register_phone_phone_autocomplete)
    EditText phone;

    @BindView(R.id.register_phone_phone)
    TextInputLayout phoneLayout;

    private String userId="";

    @Inject
    RegisterNextPresenter presenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.register_phone_layout);
        ButterKnife.bind(this);

    }

    @Override
    public void whenFails(String msg) {

    }

    @Override
    public void whenStartSetProgress() {

    }

    @Override
    public void whenStopSetProgress() {

    }

    @Override
    public void whenRegisterSuccess() {

    }
}
