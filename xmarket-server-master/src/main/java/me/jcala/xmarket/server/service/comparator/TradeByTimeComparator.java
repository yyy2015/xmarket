package me.jcala.xmarket.server.service.comparator;

import me.jcala.xmarket.server.entity.document.Trade;

import java.util.Comparator;

/**
 * Created by Mr.Zero on 2017/3/13.
 */
public class TradeByTimeComparator implements Comparator<Trade>{

    @Override
    public int compare(Trade o1, Trade o2) {
        if (o1.getCreateTime()>o2.getCreateTime()){
            return -1;
        } else if (o1.getCreateTime()<o2.getCreateTime()) {
            return 1;
        } else {
            return 0;
        }
    }
}
