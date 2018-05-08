package cn.easy.dal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Smiles on 12/5/16.
 */
@Entity
@Table(name = "security_code")
@Data
@AllArgsConstructor
public class SecurityCode  {

    @ApiModelProperty(value = "手机号或邮箱")
    @Column(name = "phoneMail", nullable = false, length = 50)
    private String phoneMail;

    @ApiModelProperty(value = "验证码类型")
    @Column(name = "phone", nullable = false, length = 11)
    private Integer type;

    @ApiModelProperty(value = "验证码")
    @Column(name = "code", nullable = false, length = 11)
    private String code;

    @ApiModelProperty(value = "ID")
    @Id
    @GenericGenerator(name = "id_uuid", strategy = "uuid")
    @GeneratedValue(generator = "id_uuid")
    @Column(name = "id", nullable = false, unique = true, length = 40)
    private Long id;

}
