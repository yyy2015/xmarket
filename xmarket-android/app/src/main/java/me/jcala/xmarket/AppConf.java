package me.jcala.xmarket;

public interface AppConf {

    boolean useMock=false;//是否使用本地离线数据，不与服务器进行交互；true表示使用离线，false使用服务器数据

    boolean enabled_ssl=true;//是否使用https协议(包括Fresco加载的图片)

    String BASE_URL="http://172.19.127.56:8080/";//服务器的xmarket-server地址及端口
//    String BASE_URL="http://192.168.31.202:8080/";//服务器的xmarket-server地址及端口

    long Message_Interval=30000L;//轮询获取消息的时间间隔.(秒)

    int size=8;//每个页面加载的数据长度

}
