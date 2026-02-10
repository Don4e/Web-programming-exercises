package mk.ukim.finki.wp.jan2025g1.repository;

import mk.ukim.finki.wp.jan2025g1.model.ArchaeologicalSite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchaeologicalSiteRepository extends JpaRepository<ArchaeologicalSite, Long> {
    Page<ArchaeologicalSite> findAll(Specification<ArchaeologicalSite> filter, Pageable pageable);
}
