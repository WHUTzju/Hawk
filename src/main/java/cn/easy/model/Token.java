package cn.easy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by superlee on 2017/11/7.
 */
@Data
@AllArgsConstructor
public class Token {
    private long timestamp;
    private String roleCode;
    private String userName;
//    private String clientId;
//    private String authVersion;

    public Token() {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Token{" +
                "timestamp=" + timestamp +
                ", roleCode='" + roleCode + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
