package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.carpool.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarpoolWidgetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "cars", ignore = true)
    CarpoolWidget mapToCarpoolWidget(CarpoolCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driverId", ignore = true)
    @Mapping(target = "riders", ignore = true)
    Car mapToCar(CarAddCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Rider mapToRider(RiderAddCommand command);
}
