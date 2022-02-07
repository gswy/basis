package xin.wanyun.server.exception;

/**
 * Rbac异常处理类
 */
public class RbacException extends RuntimeException {

    /**
     * @param message 错误信息
     */
    public RbacException(String message) {
        super(message);
    }

}
