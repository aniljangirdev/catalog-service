package com.polarbookshop.catalog_service.domain;

import java.time.LocalDate;

public record Error<T>(
        T message,
        LocalDate localDate
) {
}
