package ac.fish.utils.adcore.request;

/**
 * 全局配置参数
 * Created by WangCheng on 2015/8/19.
 */
public class HttpRequestConfig {

    public static final int NO_ARG1 = -111;

    //正式环境
    public static final String MAIN_URL = "http://hongbao.jukaapp.com/v2_1/";
    public static final String IMAGE_HEAD_URL = "http://xzhongbao.img-cn-beijing.aliyuncs.com/";

    //测试环境
    //public static final String MAIN_URL = "http://dev.jukaapp.com/";
    //public static final String IMAGE_HEAD_URL = "http://juka-test.img-cn-beijing.aliyuncs.com/";

    public static final String LOCAL_IMAGE_HEAD_URL = "file://";
    public static final String LOCAL_IMAGE_ID_HEAD_URL = "drawable://";

    /**访问淘宝Ip地址库*/
    public static final String PHONE_IP_ADDRESS_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

}
