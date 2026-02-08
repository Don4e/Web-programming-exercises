package mk.ukim.finki.wp.june2025g1.repository;

import mk.ukim.finki.wp.june2025g1.model.Startup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {
    Page<Startup> findAll(Specification<Startup> filter, Pageable pageable);
}
