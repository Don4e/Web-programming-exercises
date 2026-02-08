package mk.ukim.finki.wp.jan2025g2.repository;

import mk.ukim.finki.wp.jan2025g2.model.NationalPark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalParkRepository extends JpaRepository<NationalPark, Long> {
    Page<NationalPark> findAll(Specification<NationalPark> filter, Pageable pageable);
}
