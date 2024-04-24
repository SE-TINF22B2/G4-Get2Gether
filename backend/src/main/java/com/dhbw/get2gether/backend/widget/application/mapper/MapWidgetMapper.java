package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import com.dhbw.get2gether.backend.widget.model.map.MapWidget;
import com.dhbw.get2gether.backend.widget.model.map.MapWidgetCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapWidgetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    MapWidget mapToMapWidget(MapWidgetCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    Location mapToLocation(LocationAddCommand command);

}
