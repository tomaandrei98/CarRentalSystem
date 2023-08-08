package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCarDto;
import com.example.rental.enums.Status;
import com.example.rental.enums.Transmission;
import com.example.rental.exception.CarNotFoundException;
import com.example.rental.exception.CategoryNotFoundException;
import com.example.rental.exception.DeleteCarNotAccepted;
import com.example.rental.model.Car;
import com.example.rental.model.Category;
import com.example.rental.model.Rental;
import com.example.rental.repository.CarRepository;
import com.example.rental.repository.CategoryRepository;
import com.example.rental.service.CarService;
import com.example.rental.utils.converter.CarConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.*;
import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CarConverter carConverter;

    @Override
    @Log
    public List<ResponseCarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public List<ResponseCarDto> getAvailableCars() {
        return carRepository.findAll().stream()
                .filter(car -> car.getStatus().equals(Status.AVAILABLE))
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public List<ResponseCarDto> getCarByCategoryId(Long categoryId) {
        return carRepository.findAll().stream()
                .filter(car -> car.getCategory().getId().equals(categoryId))
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public List<ResponseCarDto> getCarsByMatchingName(String matchingName) {
        return carRepository.findAll().stream()
                .filter(car -> car.getMake().toLowerCase().contains(matchingName.toLowerCase()) ||
                        car.getModel().toLowerCase().contains(matchingName.toLowerCase()))
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public PaginatedResponseCarDto getCarsPaginated(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Car> carPage = carRepository.findAll(of(pageNumber, pageSize, Sort.by(sortBy)));

        return PaginatedResponseCarDto.builder()
                .cars(carPage.getContent().stream()
                        .map(carConverter::convertModelToResponseDto)
                        .toList())
                .numberOfItems(carPage.getTotalElements())
                .numberOfPages(carPage.getTotalPages())
                .build();
    }

    @Override
    public void populateWithCars() {
        carRepository.save(
                Car.builder()
                        .make("Renault")
                        .model("Clio")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/renault/clio_lrg.jpg")
                        .rentalPricePerDay(22.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(1L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Fiat")
                        .model("Panda")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/fiat/panda_lrg.jpg")
                        .rentalPricePerDay(24.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(1L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Toyota")
                        .model("Corolla")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/new_images/toyota/corolla_hatch_lrg.jpg")
                        .rentalPricePerDay(44.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(1L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Toyota")
                        .model("Corolla")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/new_images/toyota/corolla_hatch_lrg.jpg")
                        .rentalPricePerDay(44.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(2L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Ford")
                        .model("Focus")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/ford/focus_lrg.jpg")
                        .rentalPricePerDay(40.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(2L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Renault")
                        .model("Megane")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/new_images/renault/megane_4_doors_lrg.jpg")
                        .rentalPricePerDay(35.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(2L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Skoda")
                        .model("Octavia")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/skoda/octavia_lrg.jpg")
                        .rentalPricePerDay(30.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(2)
                        .category(categoryRepository.findById(3L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Dacia")
                        .model("Duster")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/dacia/duster_lrg.jpg")
                        .rentalPricePerDay(60.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(3)
                        .category(categoryRepository.findById(3L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Opel")
                        .model("Astra Estate")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/opel/astra_estate_lrg.jpg")
                        .rentalPricePerDay(55.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(4L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Mercedes")
                        .model("C-Class")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/mercedes/c_class_lrg.jpg")
                        .rentalPricePerDay(125.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(2)
                        .largeBag(1)
                        .category(categoryRepository.findById(5L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("BMW")
                        .model("3 Series")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/bmw/3_series_lrg.jpg")
                        .rentalPricePerDay(135.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(2)
                        .largeBag(1)
                        .category(categoryRepository.findById(5L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Mercedes")
                        .model("GLC")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/mercedes/glc_lrg.jpg")
                        .rentalPricePerDay(141.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(2)
                        .largeBag(1)
                        .category(categoryRepository.findById(5L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Opel")
                        .model("Vivaro")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/opel/vivaro_lrg.jpg")
                        .rentalPricePerDay(170.0)
                        .status(Status.AVAILABLE)
                        .seats(9)
                        .transmission(Transmission.MANUAL)
                        .smallBag(1)
                        .largeBag(2)
                        .category(categoryRepository.findById(6L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Mercedes")
                        .model("Vito")
                        .year(2023)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/mercedes/vito_lrg.jpg")
                        .rentalPricePerDay(180.0)
                        .status(Status.AVAILABLE)
                        .seats(9)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(2)
                        .category(categoryRepository.findById(6L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Toyota")
                        .model("C-HR Hybrid")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/toyota/c-hr_lrg.jpg")
                        .rentalPricePerDay(72.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(1)
                        .category(categoryRepository.findById(7L).orElseThrow())
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .make("Volkswagen")
                        .model("Touareg")
                        .year(2022)
                        .imageUrl("https://cdn2.rcstatic.com/images/car_images/web/volkswagen/touareg_lrg.jpg")
                        .rentalPricePerDay(172.0)
                        .status(Status.AVAILABLE)
                        .seats(5)
                        .transmission(Transmission.AUTOMATIC)
                        .smallBag(1)
                        .largeBag(2)
                        .category(categoryRepository.findById(7L).orElseThrow())
                        .build()
        );
    }

    @Override
    @Log
    public List<ResponseCarDto> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        return carRepository.findAll().stream()
                .filter(car -> isCarAvailableInDateRange(car, startDate, endDate))
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    private boolean isCarAvailableInDateRange(Car car, LocalDate startDate, LocalDate endDate) {
        for (Rental rental : car.getRentals()) {
            if (!rental.isReturned() && !rental.getStartDate().isAfter(endDate) && !rental.getEndDate().isBefore(startDate)) {
                return false; // Car has an ongoing rental during the specified date range
            }
        }
        return true; // Car is available in the given date range
    }

    @Override
    @Log
    public ResponseCarDto getCarById(Long carId) {
        Car downloadedCar = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        return carConverter.convertModelToResponseDto(downloadedCar);
    }

    @Override
    @Log
    public ResponseCarDto saveCar(RequestSaveCarDto requestSaveCarDto) {
        Long categoryId = requestSaveCarDto.getCategoryId();
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId)));
        Car carToSave = carConverter.convertRequestSaveToModel(requestSaveCarDto);

        carToSave.setCategory(savedCategory);
        savedCategory.addCar(carToSave);

        Car savedCar = carRepository.save(carToSave);
        return carConverter.convertModelToResponseDto(savedCar);
    }

    @Override
    @Log
    @Transactional
    public ResponseCarDto updateCar(RequestUpdateCarDto requestUpdateCarDto) {
        Long carDtoId = requestUpdateCarDto.getId();
        Optional<Car> optionalCar = carRepository.findById(carDtoId);

        optionalCar
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carDtoId))
                );

        optionalCar.ifPresent(updateCar -> {
            updateCar.setMake(requestUpdateCarDto.getMake());
            updateCar.setModel(requestUpdateCarDto.getModel());
            updateCar.setYear(requestUpdateCarDto.getYear());
            updateCar.setImageUrl(requestUpdateCarDto.getImageUrl());
            updateCar.setRentalPricePerDay(requestUpdateCarDto.getRentalPricePerDay());
            updateCar.setStatus(requestUpdateCarDto.getStatus());
            updateCar.setSeats(requestUpdateCarDto.getSeats());
            updateCar.setTransmission(requestUpdateCarDto.getTransmission());
            updateCar.setSmallBag(requestUpdateCarDto.getSmallBag());
            updateCar.setLargeBag(requestUpdateCarDto.getLargeBag());
        });

        return carConverter.convertModelToResponseDto(
                carRepository.findById(carDtoId)
                        .orElseThrow(
                                () -> new CarNotFoundException(getCarNotFoundMessage(carDtoId))
                        )
        );
    }

    @Override
    @Log
    public void deleteCarById(Long carId) {
        Car carToDelete = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        if (!carToDelete.getRentals().isEmpty()) {
            throw new DeleteCarNotAccepted(getDeleteCarNotAcceptedMessage(carId));
        }

        carRepository.delete(carToDelete);
    }
}
