package mk.ukim.finki.wp.jan2025g2.service.impl;

import mk.ukim.finki.wp.jan2025g2.model.NationalPark;
import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.ParkType;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidNationalParkIdException;
import mk.ukim.finki.wp.jan2025g2.model.exceptions.InvalidParkLocationIdException;
import mk.ukim.finki.wp.jan2025g2.repository.NationalParkRepository;
import mk.ukim.finki.wp.jan2025g2.repository.ParkLocationRepository;
import mk.ukim.finki.wp.jan2025g2.service.NationalParkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.ukim.finki.wp.jan2025g2.service.FieldFilterSpecification.*;

@Service
public class NationalParkServiceImpl implements NationalParkService {
    private final NationalParkRepository nationalParkRepository;
    private final ParkLocationRepository parkLocationRepository;

    public NationalParkServiceImpl(NationalParkRepository nationalParkRepository, ParkLocationRepository parkLocationRepository) {
        this.nationalParkRepository = nationalParkRepository;
        this.parkLocationRepository = parkLocationRepository;
    }

    @Override
    public List<NationalPark> listAll() {
        return this.nationalParkRepository.findAll();
    }

    @Override
    public NationalPark findById(Long id) {
        return this.nationalParkRepository.findById(id).orElseThrow(InvalidNationalParkIdException::new);
    }

    @Override
    public NationalPark create(String name, Double areaSize, Double rating, ParkType parkType, Long locationId) {
        ParkLocation parkLocation = this.parkLocationRepository.findById(locationId).orElseThrow(InvalidParkLocationIdException::new);
        return this.nationalParkRepository.save(new NationalPark(name, areaSize, rating, parkType, parkLocation));
    }

    @Override
    public NationalPark update(Long id, String name, Double areaSize, Double rating, ParkType parkType, Long locationId) {
        ParkLocation parkLocation = this.parkLocationRepository.findById(locationId).orElseThrow(InvalidParkLocationIdException::new);
        NationalPark nationalPark = this.findById(id);
        nationalPark.setName(name);
        nationalPark.setAreaSize(areaSize);
        nationalPark.setRating(rating);
        nationalPark.setParkType(parkType);
        nationalPark.setLocation(parkLocation);
        return this.nationalParkRepository.save(nationalPark);
    }

    @Override
    public NationalPark delete(Long id) {
        NationalPark nationalPark = this.findById(id);
        this.nationalParkRepository.delete(nationalPark);
        return nationalPark;
    }

    @Override
    public NationalPark close(Long id) {
        NationalPark nationalPark = this.findById(id);
        nationalPark.setClosed(true);
        return this.nationalParkRepository.save(nationalPark);
    }

    @Override
    public Page<NationalPark> findPage(String name, Double areaSize, Double rating, ParkType parkType, Long locationId, int pageNum, int pageSize) {
        Specification<NationalPark> specification = Specification.allOf(
                filterContainsText(NationalPark.class, "name", name),
                greaterThan(NationalPark.class, "areaSize", areaSize),
                greaterThan(NationalPark.class, "rating", rating),
                filterEquals(NationalPark.class, "location.id", locationId),
                filterEqualsV(NationalPark.class, "parkType", parkType)
        );

        return this.nationalParkRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize));
    }

}
