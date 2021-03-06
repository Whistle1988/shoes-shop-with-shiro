package cn.shoes.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author shoes
 * @Date 2019-01-19
 */
@Data
@ToString
@Table(name = "tb_user_role")
public class UserRole implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "role_id")
    private long roleId;

    public UserRole() {
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
