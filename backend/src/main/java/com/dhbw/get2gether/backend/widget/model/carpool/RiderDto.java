package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class RiderDto {
    private SimpleUserDto user;
    private String pickupPlace;
}
