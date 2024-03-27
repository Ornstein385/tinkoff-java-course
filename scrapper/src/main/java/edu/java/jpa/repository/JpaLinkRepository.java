package edu.java.jpa.repository;

import edu.java.jpa.model.LinkEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(String url);

    @Query("SELECT l FROM LinkEntity l WHERE l.lastUpdated <= :cutoff")
    List<LinkEntity> findAllWithLastUpdatedBefore(OffsetDateTime cutoff, Pageable pageable);
}
