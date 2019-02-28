package com.epam.hotel.utils;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginationHelper {

    private PaginationHelper() {
    }

    public static Integer getPage(Integer page) {
        if (page == null || page < 1) page = 1;
        return page;
    }

    public static Integer getLimit(Integer limit, Integer defaultLimit) {
        if (limit == null || limit < 1) limit = defaultLimit;
        return limit;
    }

    public static List<Integer> getPageNumbers(Page pagedList) {
        return IntStream.rangeClosed(1, pagedList.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
    }

    public static boolean isPageBeyondTotalPages(int page, Page pageList) {
        return page > pageList.getTotalPages();
    }

}