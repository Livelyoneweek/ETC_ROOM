package kr.co.choi.etc.secuenctUUID.repository;

import kr.co.choi.etc.secuenctUUID.entity.SequenceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceIdRepository extends JpaRepository<SequenceId,Long> {
}
