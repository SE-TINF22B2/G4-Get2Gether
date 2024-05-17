package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolCreateCommand;
import com.dhbw.get2gether.backend.widget.model.carpool.Rider;
import com.dhbw.get2gether.backend.widget.model.carpool.RiderAddCommand;
import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolWidget;
import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarpoolWidgetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "driverId", ignore = true)
    CarpoolWidget mapToCarpoolWidget(CarpoolCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Rider mapToRider(RiderAddCommand command);

    @Mapping(target = "id", ignore = true)
    Location mapToLocation(LocationAddCommand command);
}
