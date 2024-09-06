package kr.co.choi.etc.secuenctUUID.service;

import kr.co.choi.etc.secuenctUUID.entity.SequenceId;
import kr.co.choi.etc.secuenctUUID.repository.SequenceIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class SequenceIdService {

    @Autowired
    private SequenceIdRepository serialUUIDRepository;

    @Transactional
    public Long longValue() {
        SequenceId result = serialUUIDRepository.save(new SequenceId());
        return result.getId();
    }

    @Transactional
    public UUID uuidValue() {
        SequenceId result = serialUUIDRepository.save(new SequenceId());
        return newSequenceUUID(result.getCreatedDate().atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                result.getId());
    }

    private UUID newSequenceUUID(long timestamp, long bigserial) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(timestamp);
        buffer.putLong(bigserial);
        buffer.flip();

        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();

        return new UUID(mostSigBits, leastSigBits);
    }
}
