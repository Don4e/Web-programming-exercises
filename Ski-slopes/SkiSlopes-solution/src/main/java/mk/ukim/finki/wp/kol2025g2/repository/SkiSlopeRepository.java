package mk.ukim.finki.wp.kol2025g2.repository;

import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkiSlopeRepository extends JpaRepository<SkiSlope, Long> {
    Page<SkiSlope> findAll(Specification<SkiSlope> filter, Pageable pageable);
}
