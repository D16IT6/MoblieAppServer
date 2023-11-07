package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @Column(name = "roleId", nullable = false, length = 30)
    private String roleId;
    @Basic
    @Column(name = "roleName", nullable = true, length = 30,columnDefinition = "nvarchar(30)")
    private String roleName;
    @Basic
    @Column(name = "permission", nullable = true)
    private Integer permission;
    @OneToMany(mappedBy = "role")
    private Set<User> user;
    public Role(String roleId,String roleName,int permission){
        this.roleId=roleId;
        this.roleName=roleName;
        this.permission=permission;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) && Objects.equals(roleName, role.roleName) && Objects.equals(permission, role.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, permission);
    }
}
