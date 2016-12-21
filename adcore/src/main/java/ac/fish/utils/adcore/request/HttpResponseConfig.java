package ac.fish.utils.adcore.request;

/**
 * 服务器返回数据结果状态
 * Created by WangCheng on 2015/8/19.
 */
public class HttpResponseConfig {
    /**
     * 接口访问成功
     */
    public static final int CONNET_SUCCESS = 200;
    /**
     * 接口访问失败
     */
    public static final int CONNET_ERROR = 400;
    /**
     * 没有网络连接
     */
    public static final int NETWORK_ERROR = 401;
    /**
     * 服务器异常
     */
    public static final int SERVER_ERROR = 500;
}
