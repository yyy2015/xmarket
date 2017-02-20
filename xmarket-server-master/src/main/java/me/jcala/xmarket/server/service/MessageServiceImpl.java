package me.jcala.xmarket.server.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import me.jcala.xmarket.server.conf.JPushConfig;
import me.jcala.xmarket.server.service.inter.MessageService;

import javax.annotation.Resource;

/**
 * Message service impl
 * @author cuihao
 */
@Resource
public class MessageServiceImpl implements MessageService {

    @Resource
    private JPushConfig jPushConfig;

    /**
     * Push message to jpush server
     *
     * @param userId  message receiver alias
     * @param context message context
     */
    @Override
    public void pushMessage(String userId, String context) {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(jPushConfig.getMasterSecret(),
                jPushConfig.getAppKey(), null, clientConfig);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_alias_alert(userId,context);
        try {
            PushResult result = jpushClient.sendPush(payload);
            Thread.sleep(5000);
//            jpushClient.close();
        } catch (APIConnectionException | APIRequestException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static PushPayload buildPushObject_android_alias_alert(String alias, String context) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(context,"xmarket",null))
                .build();
    }
}
