package com.bikkadIt.payloads;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class helper {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();

        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageSize(page.getSize());
        response.setPageNumber(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}