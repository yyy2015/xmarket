package me.jcala.xmarket.server.service.inter;

/**
 * Jpush message interface
 */
public interface MessageService {

    /**
     * Push message to jpush server
     * @param userId message receiver alias
     * @param context message context
     */
    void pushMessage(String userId, String context);

}
