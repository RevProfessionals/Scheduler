package com.revature.scheduler.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationDTO {

    private String address;
    private String city;
    private String state;
}
