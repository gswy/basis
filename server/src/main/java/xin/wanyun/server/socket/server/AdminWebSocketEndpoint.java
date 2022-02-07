package xin.wanyun.server.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import xin.wanyun.server.socket.message.OnlineMessage;
import xin.wanyun.server.utils.JwtUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/admin/websocket")
public class AdminWebSocketEndpoint {

    /**
     * 保存客户端连接Map
     */
    private static final ConcurrentHashMap<Long, AdminWebSocketEndpoint> connects = new ConcurrentHashMap<>();

    /**
     * Websocket Session
     */
    private Session session;

    /**
     * 连接的用户ID
     */
    private Long userId;

    /**
     *
     * @param session WebsocketSession
     * @throws IOException 异常
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {

        // 获取token
        Map<String, List<String>> params = session.getRequestParameterMap();
        List<String> param = params.get("token");
        if (param == null || param.isEmpty()) {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "token不能为空"));
            return;
        }

        // 获取到token，进行token验证
        String token = param.get(0);
        Long uid = JwtUtil.validateToken(token);
        if (uid == null) {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "token错误"));
            return;
        }

        // 将连接实例添加到静态map中
        this.userId = uid;
        this.session = session;
        connects.put(uid, this);

        sendOnlineCount();  // 推送在线人数
    }

    /**
     * 收到消息
     * @param message 消息字符串
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("-----------接收消息------------");
        // sendMessage(message);
    }

    /**
     * 连接错误回调
     * @param session 错误session
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误" + session);
    }

    /**
     * 连接关闭回调
     */
    @OnClose
    public void onClose() {
        connects.remove(userId);
        sendOnlineCount();  // 推送在线人数
        System.out.println("------------关闭连接-----------");
    }

    /**
     * 给全部发送消息
     */
    public void sendMessage(String message) {
        for (AdminWebSocketEndpoint webSocketEndpoint: connects.values()) {
            webSocketEndpoint.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 给一个人发送消息
     */
    public void sendMessage(String message, Long userId) {
        for (AdminWebSocketEndpoint webSocketEndpoint: connects.values()) {
            if (webSocketEndpoint.userId.equals(userId)) {
                webSocketEndpoint.session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 给一些人发送消息
     */
    public void sendMessage(String message, List<Long> userIds) {
        for (AdminWebSocketEndpoint webSocketEndpoint: connects.values()) {
            if (userIds.contains(webSocketEndpoint.userId)) {
                webSocketEndpoint.session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 给客户端推送在线人数
     */
    public void sendOnlineCount() {
        sendMessage(toJson(new OnlineMessage("online", connects.size())));
    }

    /**
     * 对象转json字符串
     *
     * @param obj 转换类
     * @return json 字符串
     */
    public String toJson(Serializable obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
