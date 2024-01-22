package com.shop.coreutils.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageableUtils {

    public static Pageable createPageable(int page, int size, String[] sort){
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){

            for(String sortItem :sort){
                String[] sortOrder = sort[0].split(",");
                orders.add(new Sort.Order(Sort.Direction.fromString(sortOrder[1]),sortOrder[0]));
            }
        }
        else
            orders.add(new Sort.Order(Sort.Direction.fromString(sort[1]),sort[0]));


        return PageRequest.of(page,size,Sort.by(orders));
    }

    public static <T> ResponseEntity<Map<String,Object>> preparePaginatedResponse(String entityName,
                                                                       Page<T> page){

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(entityName, page.getContent());
        responseMap.put("currentPage", page.getNumber());
        responseMap.put("totalItems", page.getTotalElements());
        responseMap.put("totalPages", page.getTotalPages());
        responseMap.put("size", page.getSize());
        responseMap.put("numberOfElements",page.getNumberOfElements());

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
