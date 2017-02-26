package me.jcala.xmarket.mvp.user.login.register.phone;

/**
 * register phone view interface
 * @author cuihao
 */
public interface RegisterPhoneView {
    void whenFails(String msg);//当异常发生
    void whenStartSetProgress();//显示进度条
    void whenStopSetProgress();//隐藏进度条
    void whenRegisterSuccess();
}
