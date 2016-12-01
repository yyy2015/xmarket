package me.jcala.xmarket.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import me.jcala.xmarket.data.dto.Result;
import me.jcala.xmarket.data.pojo.TradeTag;
import me.jcala.xmarket.data.pojo.User;

public class UserInfoMock {

    public Result<String>  register(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":\"583fdbe359ed2e05b82eb4d1\"}";
      return new Gson().fromJson(json,  new TypeToken<Result<String>>() {}.getType());
    }

    public Result<List<String>> gainSchoolList(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":[\"西南交通大学(犀浦校区)\"," +
                "\"西南交通大学(九里校区)\",\"西南交通大学(峨眉校区)\",\"电子科技大学(沙河校区)\",\"电子科技大学(清水河校区)\"]}";
        return new Gson().fromJson(json,  new TypeToken<Result<List<String>>>() {}.getType());
    }

    public Result<List<TradeTag>> tradeTag(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":[{\"id\":1,\"name\":\"学习资料\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_book.jpg\"},{\"id\":2,\"name\":\"生活用品\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_life.jpg\"},{\"id\":3,\"name\":\"衣物鞋帽\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_clothes.jpeg\"},{\"id\":4,\"name\":\"运动健身\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_body.jpg\"},{\"id\":5,\"name\":\"手机数码\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_phone.jpg\"},{\"id\":6,\"name\":\"电脑办公\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_computer.jpg\"},{\"id\":7,\"name\":\"电器\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_elec.jpg\"},{\"id\":8,\"name\":\"数码配件\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_parts.jpg\"},{\"id\":9,\"name\":\"租赁\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_rent.jpg\"},{\"id\":9,\"name\":\"其他\",\"bgPic\":" +
                "\"https://jcalaz.github.io/img/sort_other.jpg\"}]}";
        return new Gson().fromJson(json,  new TypeToken<Result<List<TradeTag>>>() {}.getType());
    }
    public Result<User> loginOrRegisterNext(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":{\"id\":\"583fdbe359ed2e05b82eb4d1\"," +
                "\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ4bWFya2V0IiwiaWF0IjoxNDgwNjA0MDY3LCJzdWIiO" +
                "iI1ODNmZGJlMzU5ZWQyZTA1YjgyZWI0ZDEiLCJpc3MiOiJqY2FsYSIsImV4cCI6MTQ4MDYxMTI2N30.AA61PCn" +
                "Q7H5MI_xU-YjeA9iAa7EEig5rpr_KxgRgB7Y\",\"username\":\"jcala\",\"password\":\"a665a459204" +
                "22f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3\",\"school\":\"西南交通大学(九里校区)\"," +
                "\"phone\":\"18200000455000\",\"avatarUrl\":\"/img/avatar.jpg\",\"sellTrades\":null,\"soldTrades\":" +
                "null,\"donateTrades\":null,\"boughtTrades\":null,\"toBeConfirmTrades\":null,\"teams\":null}}";
        return new Gson().fromJson(json,  new TypeToken<Result<User>>() {}.getType());
    }
}
