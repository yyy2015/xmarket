package me.jcala.xmarket.mvp.sort;


import java.util.List;

import io.realm.Realm;
import me.jcala.xmarket.data.dto.Result;
import me.jcala.xmarket.data.pojo.TradeTag;
import me.jcala.xmarket.mock.TradeMock;
import me.jcala.xmarket.network.Api;
import me.jcala.xmarket.network.ReqExecutor;
import me.jcala.xmarket.network.TradeReq;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class TradeTagModelImpl implements TradeTagModel {
    @Override
    public void executeGetTagReq(final onGainListener listener,final Realm realm) {
//        if (AppConf.useMock){
        if (true){
            listener.onComplete(new TradeMock().tradeTag(),realm);
            return;
        }
        Result<List<TradeTag>> result = new Result<List<TradeTag>>().api(Api.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .tradeTags(TradeReq.GET_TAG)//zwq modified(change GET_Name to GET_Tag)  未测试
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<TradeTag>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onComplete(result,realm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setCode(Api.SERVER_ERROR.code());
                        listener.onComplete(result,realm);
                    }
                    @Override
                    public void onNext(Result<List<TradeTag>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
