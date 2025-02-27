package com.restaurante.restaurante.mapper;

import com.restaurante.restaurante.dto.OrderDTO;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.DishRepository;
import com.restaurante.restaurante.utils.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final DishMapper dishMapper;


    @Autowired
    public OrderMapper( DishMapper dishMapper) {

        this.dishMapper = dishMapper;

    }

    public OrderDTO toDTO(Orders order) {
        if (order == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getDateOrder());
        dto.setTotalPrice(order.getTotalPrice());

        // Convertir cliente a DTO
        if (order.getClient() != null) {
            dto.setClientId(order.getClient().getId());
        }

        // Convertir platos a DTOs
        if (order.getDishes() != null) {
            dto.setDishes(dishMapper.toDTOList(order.getDishes()));
        }

        return dto;
    }

    public Orders toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        Orders order = new Orders();
        order.setId(dto.getId());
        order.setDateOrder(dto.getOrderDate());
        order.setTotalPrice(dto.getTotalPrice());

        // El cliente y los platos se manejarán en el servicio
        return order;
    }

    public List<OrderDTO> toDTOList(List<Orders> orders) {
        if (orders == null) {
            return Collections.emptyList();
        }
        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}