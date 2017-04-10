package me.jcala.xmarket.mvp.user.login.register.phone;

import android.content.Context;

import me.jcala.xmarket.data.dto.Result;
import me.jcala.xmarket.data.pojo.User;
import me.jcala.xmarket.data.storage.SharedPreferencesStorage;

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
        if (checkInput(user_id,code)) {
            registerPhoneModel.executeRegisterRequest(user_id,code,this);
            registerPhoneView.whenStartSetProgress();
        }
    }

    private boolean checkInput(String user_id,String phone){
        if (user_id.isEmpty()){
            registerPhoneView.whenFails("APP发生异常,userId为空");
            return false;
        }
        if (phone.isEmpty()){
            registerPhoneView.whenFails("验证码不可以为空");
            return false;
        }
        return true;
    }

    @Override
    public void hasGotRegisterResult(Result result) {
        registerPhoneView.whenStopSetProgress();
        if (result == null) return;
        switch (result.getCode()) {
            case 100:
//                User user = (User) result.getData();
//                SharedPreferencesStorage.instance.saveUser(context,user);
                registerPhoneView.whenRegisterSuccess();
                break;
            case 207:
            case 204:
            case 99:
                registerPhoneView.whenFails(result.getMsg());
                break;
            default: break;
        }
    }
}
