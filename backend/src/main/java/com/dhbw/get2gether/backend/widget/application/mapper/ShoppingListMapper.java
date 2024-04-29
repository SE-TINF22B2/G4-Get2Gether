package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.shoppinglist.Entry;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.EntryAddCommand;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.ShoppingListCreateCommand;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.ShoppingListWidget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingListMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entries", ignore = true)
    ShoppingListWidget mapToShoppingListWidget(ShoppingListCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checked", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "buyerId", ignore = true)
    Entry mapToEntry(EntryAddCommand command);

}
