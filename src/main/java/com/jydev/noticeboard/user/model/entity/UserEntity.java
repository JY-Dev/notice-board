package com.jydev.noticeboard.user.model.entity;

import com.jydev.noticeboard.user.model.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String id;
    private String profileImageUrl;
    private String email;
    private String nickname;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @CreatedDate
    private LocalDateTime createdDateTime;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        UserEntity user = (UserEntity) obj;
        return Objects.equals(user.id,this.getId()) && Objects.equals(password,((UserEntity) obj).password);
    }
}
