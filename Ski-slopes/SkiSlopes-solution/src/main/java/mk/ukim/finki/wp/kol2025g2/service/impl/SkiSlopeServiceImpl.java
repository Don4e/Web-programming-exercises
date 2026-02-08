package mk.ukim.finki.wp.kol2025g2.service.impl;

import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiSlopeIdException;
import mk.ukim.finki.wp.kol2025g2.repository.SkiSlopeRepository;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.ukim.finki.wp.kol2025g2.service.FieldFilterSpecification.*;

@Service
public class SkiSlopeServiceImpl implements SkiSlopeService {
    private final SkiSlopeRepository skiSlopeRepository;
    private final SkiResortService skiResortService;

    public SkiSlopeServiceImpl(SkiSlopeRepository skiSlopeRepository, SkiResortService skiResortService) {
        this.skiSlopeRepository = skiSlopeRepository;
        this.skiResortService = skiResortService;
    }

    @Override
    public List<SkiSlope> listAll() {
        return this.skiSlopeRepository.findAll();
    }

    @Override
    public SkiSlope findById(Long id) {
        return this.skiSlopeRepository.findById(id).orElseThrow(InvalidSkiSlopeIdException::new);
    }

    @Override
    public SkiSlope create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        return this.skiSlopeRepository.save(new SkiSlope(name, length, difficulty, this.skiResortService.findById(skiResort)));
    }

    @Override
    public SkiSlope update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        SkiSlope skiSlope = this.findById(id);
        skiSlope.setName(name);
        skiSlope.setLength(length);
        skiSlope.setDifficulty(difficulty);
        skiSlope.setSkiResort(this.skiResortService.findById(skiResort));
        return this.skiSlopeRepository.save(skiSlope);
    }

    @Override
    public SkiSlope delete(Long id) {
        SkiSlope skiSlope = this.findById(id);
        this.skiSlopeRepository.delete(skiSlope);
        return skiSlope;
    }

    @Override
    public SkiSlope close(Long id) {
        SkiSlope skiSlope = this.findById(id);
        skiSlope.setClosed(true);
        return this.skiSlopeRepository.save(skiSlope);
    }

    @Override
    public Page<SkiSlope> findPage(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, int pageNum, int pageSize) {
        Specification<SkiSlope> specification = Specification.allOf(
                filterContainsText(SkiSlope.class, "name", name),
                greaterThan(SkiSlope.class, "length", length),
                filterEqualsV(SkiSlope.class, "difficulty", difficulty),
                filterEqualsV(SkiSlope.class, "skiResort.id", skiResort)
        );
        return this.skiSlopeRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize));
    }

}
