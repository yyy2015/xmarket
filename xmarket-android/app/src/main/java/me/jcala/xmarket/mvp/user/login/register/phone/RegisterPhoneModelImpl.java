package me.jcala.xmarket.mvp.user.login.register.phone;

import me.jcala.xmarket.AppConf;
import me.jcala.xmarket.data.dto.Result;
import me.jcala.xmarket.data.pojo.User;
import me.jcala.xmarket.mock.UserInfoMock;
import me.jcala.xmarket.network.Api;
import me.jcala.xmarket.network.ReqExecutor;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * phone model impl
 * @author cuihao
 */
public class RegisterPhoneModelImpl implements RegisterPhoneModel {

    @Override
    public void executeRegisterRequest(String user_id, String phone, onRegisterPhoneListener listener) {
        if(AppConf.useMock){
            listener.hasGotRegisterResult(new UserInfoMock().loginOrRegisterNext());
            return;
        }
        Result<User> result= new Result<User>().api(Api.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .userReq()
                .verifyMsg(user_id,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {
                        listener.hasGotRegisterResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.hasGotRegisterResult(result);
                    }

                    @Override
                    public void onNext(Result<User> userResult) {
                        result.setCode(userResult.getCode());
                        result.setMsg(userResult.getMsg());
                        result.setData(userResult.getData());
                    }
                });
    }
}
