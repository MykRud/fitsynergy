package com.mike.projects.fitsynergy.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity(name = "system_user_details")
public class SystemUserDetails {
    @Id
    @SequenceGenerator(sequenceName = "system_user_details_id_seq", name = "system_user_details_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_user_details_id_seq_generator")
    private Integer id;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private SystemUserRole role;
    @Column(name = "is_expired")
    private Boolean isExpired;
    @Column(name = "is_locked")
    private Boolean isLocked;
    @Column(name = "is_credentials_expired")
    private Boolean isCredentialsExpired;
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public SystemUserDetails(SystemUserRole role, Boolean isExpired, Boolean isLocked, Boolean isCredentialsExpired, Boolean isEnabled) {
        this.role = role;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.isCredentialsExpired = isCredentialsExpired;
        this.isEnabled = isEnabled;
    }

    public static SystemUserDetails getInstanceOfClient(){
        return new SystemUserDetails(SystemUserRole.CLIENT, false, false, false, true);
    }

    public static SystemUserDetails getInstanceOfTrainer(){
        SystemUserDetails user = getInstanceOfClient();
        user.setRole(SystemUserRole.TRAINER);
        return user;
    }

    public static SystemUserDetails getInstanceOfAdmin(){
        SystemUserDetails user = getInstanceOfClient();
        user.setRole(SystemUserRole.ADMIN);
        return user;
    }
}
