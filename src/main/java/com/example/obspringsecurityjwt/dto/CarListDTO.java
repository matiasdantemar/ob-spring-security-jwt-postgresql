package com.example.obspringsecurityjwt.dto;

import java.util.List;

import com.example.obspringsecurityjwt.domain.Car;

public class CarListDTO {

    private List<Car> cars;

    public CarListDTO() {}

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

}