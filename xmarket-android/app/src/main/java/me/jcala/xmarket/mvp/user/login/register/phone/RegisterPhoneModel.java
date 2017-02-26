package me.jcala.xmarket.mvp.user.login.register.phone;


import me.jcala.xmarket.data.dto.Result;

/**
 * register phone verification model
 * @author cuihao
 */

public interface RegisterPhoneModel {

    interface onRegisterPhoneListener{

        void hasGotRegisterResult(Result result);

    }

    void executeRegisterRequest(String user_id,String phone,onRegisterPhoneListener listener);
}
