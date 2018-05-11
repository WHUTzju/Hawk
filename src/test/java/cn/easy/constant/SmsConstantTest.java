package cn.easy.constant;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by zhangrui on 2018/5/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsConstantTest {

    private static final String SMS_SIGNATURE = "EasyTech";
    private static final String TEMPLATE_CODE = "SMS_134322749";
    private static final String phoneNum = "15557006867";

    @Autowired
    SmsConstant smsConstant;

    @Test
    public void sendSms() {
        Map<String, String> codeMap = new HashMap<>();
        codeMap.put("code", RandomStringUtils.randomNumeric(6));
        String templateParam = JSON.toJSONString(codeMap);
        int count = 0;
        while (count < 10) {
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = smsConstant.sendSms(phoneNum, SMS_SIGNATURE, TEMPLATE_CODE, templateParam);

                if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                    break;
                }
                System.out.println("sendSmsResponse" + JSON.toJSONString(sendSmsResponse));
            } catch (ClientException e) {
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("count=" + count);


    }

}