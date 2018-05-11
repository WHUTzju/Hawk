package cn.easy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by superlee on 2018/1/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCode {
    private String phoneMail;
    private String code;
    private long createTime;
    private long reSendTime;
    private long validTime;
}
