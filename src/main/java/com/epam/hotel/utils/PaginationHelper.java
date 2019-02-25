package com.epam.hotel.utils;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginationHelper {

    private PaginationHelper() {}

    public static List<Integer> getPageNumber(Page<? extends Object> pagedList) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, pagedList.getTotalPages()).
                boxed().collect(Collectors.toList());
        return pageNumbers;
    }

}