package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.shoppinglist.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShoppingListMapper {
    ShoppingListMapper MAPPER = Mappers.getMapper(ShoppingListMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entries", ignore = true)
    ShoppingListWidget mapToShoppingListWidget(ShoppingListCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checked", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "buyerId", ignore = true)
    Entry mapToEntry(EntryAddCommand command);

    // Verstehe nicht, warum hier source checked ist unt nicht isChecked
    @Mapping(target = "checked", source = "checked")
    EntryCheck mapEntryCheckCommandToEntryCheck(EntryCheckCommand command);

}
