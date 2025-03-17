package kr.co.choi.etc.company.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import kr.co.choi.etc.common.TrackingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_company")
public class Company extends TrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "id", nullable = false)
    @Comment("primary key")
    private UUID id;

    @Column(nullable = false)
    @Comment("회사명")
    private String name;

    public Company(String name) {
        this.name = name;
    }
}
