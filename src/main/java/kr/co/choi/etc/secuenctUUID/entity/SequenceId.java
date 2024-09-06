package kr.co.choi.etc.secuenctUUID.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.choi.etc.common.TrackingEntity;
import org.hibernate.annotations.Comment;

@Entity
@Comment("시리얼 아이디 자동 생성")
public class SequenceId extends TrackingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
