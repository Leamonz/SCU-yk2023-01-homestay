package homestay.service;

import homestay.dao.Data;
import homestay.dao.UserDao;
import homestay.utils.EmailUtil;
import org.apache.commons.mail.EmailException;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Random;

public class UserService {

    public boolean register(Data data, JSONObject resJson) throws JSONException, SQLException {
        UserDao dao = new UserDao();
        // 取数据
        String id = data.getParam().getString("id");
        // 检查重复id
        JSONObject attemptUser = dao.queryUserById(id);
        if(attemptUser.length() != 0){
           // 存在相同id
            resJson.put("resCode", "R0001");
            resJson.put("registerInfo", "error: duplicate usernames");
           return false;
        }
        dao.addUser(data);
        resJson.put("resCode", "00000");
        resJson.put("registerInfo", "success");
        return true;
    }

    public void modifyUserInfo(Data data, JSONObject resJson) throws JSONException, SQLException {
        UserDao dao = new UserDao();
        String email = data.getParam().getString("email");
        // 检查是否存在
        JSONObject attemptUser = dao.queryUserByKey("email", email);
        if(attemptUser.length() == 0) {
            // 不存在此用户
            resJson.put("resCode", "S0001");
            resJson.put("resetInfo", "user does not exist");
            return;
        }
        // 修改
        dao.modifyUserInfo(data);
        resJson.put("resCode", "00000");
        resJson.put("resetInfo", "success");
    }
}
