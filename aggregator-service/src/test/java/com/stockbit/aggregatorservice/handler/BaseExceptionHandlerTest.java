package com.stockbit.aggregatorservice.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class BaseExceptionHandlerTest {

    private BaseExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new BaseExceptionHandler();
    }

    @Test
    void givenException_shouldReturnErrorResponse() {
        ErrorResponse response = exceptionHandler.handle(new DataNotFoundException("Not found"));
        assertThat(response.getError()).isEqualTo(DataNotFoundException.class.getSimpleName());
        assertThat(response.getMessage()).isEqualTo("Not found");
    }
}
