package me.jcala.xmarket.mvp.user.login.register.phone;

import android.content.Context;

import me.jcala.xmarket.data.dto.Result;

/**
 * presenter impl
 * @author cuihao
 */
public class RegisterPhonePresenterImpl implements RegisterPhonePresenter,
        RegisterPhoneModel.onRegisterPhoneListener {

    private Context context;
    private RegisterPhoneView registerPhoneView;
    private RegisterPhoneModel registerPhoneModel;

    public RegisterPhonePresenterImpl(Context context, RegisterPhoneView registerPhoneView) {
        this.context = context;
        this.registerPhoneView = registerPhoneView;
        this.registerPhoneModel = new RegisterPhoneModelImpl();
    }

    @Override
    public void registerPhone(String user_id, String code) {

    }

    @Override
    public void hasGotRegisterResult(Result result) {

    }
}
