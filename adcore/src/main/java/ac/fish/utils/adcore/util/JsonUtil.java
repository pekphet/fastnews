package ac.fish.utils.adcore.util;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON 工具类
 */
public class JsonUtil {
    static Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * 获取的 Code 状态码
     *
     * @param jsonStr JSON字符串
     * @return 状态码 200 ：响应成功 400：错误
     */
    public static String getJsonCode(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.get("code").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据Key值查找到 对应key的 Json字符串
     *
     * @param jsonStr JSON字符串
     * @param key     Json中的key
     * @return key对应的 JSON字符串 Value值
     */
    public static String getKeyFindJsonStr(String jsonStr, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (!jsonObject.isNull(key)) {
                String str = jsonObject.getString(key);
                return str;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getErrorMessage(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把 JSON 字符串 转换成 实体类
     *
     * @param jsonStr JSON字符串
     * @param clazz   实体类字节码
     * @return 实体类
     */
    public static <T> T toEntity(String jsonStr, Class<T> clazz) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1) {
                return null;
            }
            T fromJson = gson.fromJson(jsonStr, clazz);
            return fromJson;
        } catch (JsonSyntaxException e) {
            // Toast.makeText(SoftApplication.getContext(), "JSON转换出错！",
            // 0).show();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把 JSON 字符串 转换成 实体集合
     *
     * @param jsonStr JSON字符串
     * @param tt      TypeToken对象
     * @return 返回转换后的 集合
     * @code 示例代码： String data = JsonUtil.getKeyFindJsonStr(result,"data");
     * List<Order> orderList = JsonUtil.toList(data,new
     * TypeToken<List<Order>>(){});
     */
    public static <T> List<T> toList(String jsonStr, TypeToken<List<T>> tt) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1) {
                return null;
            }
            Type type = tt.getType();
            List<T> list = gson.fromJson(jsonStr, type);
            return list;
        } catch (JsonSyntaxException e) {
            // Toast.makeText(SoftApplication.getContext(), "JSON转换出错！",
            // 0).show();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把 JSON 字符串 转换成 实体集合
     *
     * @param jsonStr JSON字符串
     * @param tt      TypeToken对象
     * @return 返回转换后的 集合
     * @code 示例代码： String data = JsonUtil.getKeyFindJsonStr(result,"data");
     * ArrayList<Order> orderList = JsonUtil.toArrayList(data,new
     * TypeToken<List<Order>>(){});
     */
    public static <T> ArrayList<T> toArrayList(String jsonStr,
                                               TypeToken<List<T>> tt) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1) {
                return null;
            }
            Type type = tt.getType();
            ArrayList<T> list = gson.fromJson(jsonStr, type);
            return list;
        } catch (JsonSyntaxException e) {
            // Toast.makeText(SoftApplication.getContext(), "JSON转换出错！",
            // 0).show();
            e.printStackTrace();
            return null;
        }
    }

   /* public static UserInfo getUserInfoByWeChatLoginResurn(String result) {
        UserInfo userInfo = new UserInfo();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            if (!data.isNull("uid")) {
                userInfo.setUid(data.getString("uid"));
            }
            if (!data.isNull("mobile")) {
                userInfo.setMobile(data.getString("mobile"));
            }
            if (!data.isNull("nickname")) {
                userInfo.setNickname(data.getString("nickname"));
            }
            if (!data.isNull("gender")) {
                userInfo.setGender(data.getString("gender"));
            }
            if (!data.isNull("avatar")) {
                userInfo.setAvatar(data.getString("avatar"));
            }
            if (!data.isNull("birthday")) {
                userInfo.setBirthday(data.getString("birthday"));
            }
            if (!data.isNull("city_name")) {
                userInfo.setCity_name(data.getString("city_name"));
            }
            if (!data.isNull("city_id")) {
                userInfo.setCity_id(data.getString("city_id"));
            }
            if (!data.isNull("job")) {
                userInfo.setJob(data.getString("job"));
            }
            if (!data.isNull("umeng_token")) {
                userInfo.setUmeng_token(data.getString("umeng_token"));
            }
            if (!data.isNull("user_type")) {
                userInfo.setUser_type(data.getString("user_type"));
            }
            if (!data.isNull("password")) {
                userInfo.setPassword(data.getString("password"));
            }
            if (!data.isNull("register_time")) {
                userInfo.setRegister_time(data.getString("register_time"));
            }
            if (!data.isNull("login_time")) {
                userInfo.setLogin_time(data.getString("login_time"));
            }
            if (!data.isNull("modify_time")) {
                userInfo.setModify_time(data.getString("modify_time"));
            }
            if (!data.isNull("version")) {
                userInfo.setVersion(data.getString("version"));
            }
            if (!data.isNull("device_type")) {
                userInfo.setVersion(data.getString("device_type"));
            }
            if (!data.isNull("device_id")) {
                userInfo.setDevice_id(data.getString("device_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static UserInfo getTempUserInfoByWeChatLoginResurn(String result) {
        UserInfo userInfo = new UserInfo();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            if (!data.isNull("uid")) {
                userInfo.setUid(data.getString("uid"));
            }
            if (!data.isNull("mobile")) {
                userInfo.setMobile(data.getString("mobile"));
            }
            if (!data.isNull("nickname")) {
                userInfo.setNickname(data.getString("nickname"));
            }
            if (!data.isNull("gender")) {
                userInfo.setGender(data.getString("gender"));
            }
            if (!data.isNull("avatar")) {
                userInfo.setAvatar(data.getString("avatar"));
            }
            if (!data.isNull("birthday")) {
                userInfo.setBirthday(data.getString("birthday"));
            }
            if (!data.isNull("city_name")) {
                userInfo.setCity_name(data.getString("city_name"));
            }
            if (!data.isNull("city_id")) {
                userInfo.setCity_id(data.getString("city_id"));
            }
            if (!data.isNull("job")) {
                userInfo.setJob(data.getString("job"));
            }
            if (!data.isNull("umeng_token")) {
                userInfo.setUmeng_token(data.getString("umeng_token"));
            }
            if (!data.isNull("user_type")) {
                userInfo.setUser_type(data.getString("user_type"));
            }
            if (!data.isNull("password")) {
                userInfo.setPassword(data.getString("password"));
            }
            if (!data.isNull("register_time")) {
                userInfo.setRegister_time(data.getString("register_time"));
            }
            if (!data.isNull("login_time")) {
                userInfo.setLogin_time(data.getString("login_time"));
            }
            if (!data.isNull("modify_time")) {
                userInfo.setModify_time(data.getString("modify_time"));
            }
            if (!data.isNull("version")) {
                userInfo.setVersion(data.getString("version"));
            }
            if (!data.isNull("device_type")) {
                userInfo.setVersion(data.getString("device_type"));
            }
            if (!data.isNull("device_id")) {
                userInfo.setDevice_id(data.getString("device_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }*/

    public static String getVerfyByWeChatLoginResurn(String result) {
        String verfy = "";
        try {
            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            verfy = data.getString("verify");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verfy;
    }

    public static String getLikeCountByWeChatLoginResurn(String result) {
        String likeCount = "";
        try {
            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            if (!data.isNull("like_count")) {
                likeCount = data.getString("like_count");
            } else {
                likeCount = "0";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return likeCount;
    }

    /**
     * 获取账户明细列表
     *
     * @param result 网络访问返回的数据
     * @return
     */
    /*public static List<AccountEntity> getAccountEntityList(String result) {
        List<AccountEntity> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject data = object.getJSONObject("data");
            JSONArray withDrawArray = data.getJSONArray("history_withdraw");
            for (int i = 0; i < withDrawArray.length(); i++) {
                JSONObject withDrawObject = withDrawArray.getJSONObject(i);
                String time = withDrawObject.getString("ctime");
                list.add(new AccountEntity(withDrawObject.getString("out_money"), time, DateUtil.getTimeMillis(time),
                        1));
            }
            JSONArray payArray = data.getJSONArray("history_pay");
            for (int j = 0; j < payArray.length(); j++) {
                JSONObject payObject = payArray.getJSONObject(j);
                String time = payObject.getString("ctime");
                list.add(new AccountEntity(payObject.getString("total_fee"), time, DateUtil.getTimeMillis(time), 2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sortByTime(list);
    }

    public static List<AccountEntity> sortByTime(List<AccountEntity> list) {
        if (list.size() > 0) {    //查看数组是否为空
            quickSort(list, 0, list.size() - 1);
        }
        return list;
    }

    public static int getMiddle(List<AccountEntity> list, int low, int high) {
        AccountEntity tmp = list.get(low);    //数组的第一个作为中轴
        while (low < high) {
            while (low < high && list.get(high).getTimeStamp() <= tmp.getTimeStamp()) {
                high--;
            }
            list.set(low, list.get(high));  //比中轴大的记录移到低端
            while (low < high && list.get(low).getTimeStamp() >= tmp.getTimeStamp()) {
                low++;
            }
            list.set(high, list.get(low));//比中轴小的的记录移到高端
        }
        list.set(low, tmp); //中轴记录到尾
        return low;  //返回中轴的位置
    }

    public static void quickSort(List<AccountEntity> list, int low, int high) {
        if (low < high) {
            int middle = getMiddle(list, low, high);  //将list数组进行一分为二
            quickSort(list, low, middle - 1);        //对低字表进行递归排序
            quickSort(list, middle + 1, high);       //对高字表进行递归排序
        }
    }*/

}
