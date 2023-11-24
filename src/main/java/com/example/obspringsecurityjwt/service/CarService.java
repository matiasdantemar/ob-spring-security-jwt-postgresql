package com.example.obspringsecurityjwt.service;

import com.example.obspringsecurityjwt.domain.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


// Interfaz que define los métodos de servicio para operaciones relacionadas con la entidad Car.
// Un servicio actúa como intermediario entre el controlador y el repositorio, separando la lógica
// de negocio de las responsabilidades de manejo de peticiones del controlador.

public interface CarService {

    // spring repository methods

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Long count();

    Car save(Car car);

    void deleteById(Long id);

    void deleteAll();

    void deleteAll(List<Car> cars);

    void deleteAllById(List<Long> ids);

    // custom methods

    List<Car> findByDoors(Integer doors);

    List<Car> findByManufacturerAndModel(String manufacturer, String model);

    List<Car> findByDoorsGreaterThanEqual(Integer doors);

    List<Car> findByModelContaining(String model);

    List<Car> findByYearIn(List<Integer> years);

    List<Car> findByYearBetween(Integer startYear, Integer endYear);

    List<Car> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Car> findByAvailableTrue();

    Long deleteAllByAvailableFalse();


}