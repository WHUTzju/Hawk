package cn.easy.dal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zhangrui on 2018/5/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
@Builder
public class AccountEntity {
    @ApiModelProperty(value = "账户uuid,自动生成")
    @Id
    @GenericGenerator(name = "id_uuid", strategy = "uuid")
    @GeneratedValue(generator = "id_uuid")
    @Column(name = "id", nullable = false, unique = true, length = 40)
    private String id;

    @ApiModelProperty(value = "手机号码")
    @Column(name = "phone", columnDefinition = ("varchar(20) not null comment '手机号码'"))
    private String phone;

    @ApiModelProperty(value = "密码")
    @Column(name = "password", columnDefinition = ("varchar(32) not null comment '密码'"))
    private String password;

    @ApiModelProperty(value = "邮箱")
    @Column(name = "email", columnDefinition = ("varchar(128) not null comment '邮箱'"))
    private String email;

    //TODO
    @ApiModelProperty(value = "角色")
    @Column(name = "roleCode", columnDefinition = ("int default 0  comment '用户角色'"))
    private int roleCode;

    //TODO 用户个人信息 需要实名认证？

    @ApiModelProperty(value = "用户姓名")
    @Column(name = "name", columnDefinition = ("varchar(40) not null comment '用户名'"))
    private String name;
    @ApiModelProperty(value = "会员等级")
    @Column(name = "level", columnDefinition = ("int default 1 comment '会员等级'"))
    private String level;


    //todo 账户状态信息

    @ApiModelProperty(value = "用户状态 0:正常 1:无效 2:冻结 3:锁定 4:待审核")
    @Column(name = "status", columnDefinition = ("int default 0  comment '用户状态'"))
    private int status;

    @ApiModelProperty(value = "密码错误次数，输入正确清空")
    @Column(name = "error_count", columnDefinition = ("int default 0 comment '密码错误次数'"))
    private int errorCount;

    @ApiModelProperty(value = "锁定时时间戳")
    @Column(name = "lock_time", columnDefinition = ("bigint default null comment '锁定时间戳'"))
    private long lockTime;
}
