package com.stockbit.aggregatorcommon.validation;

import com.stockbit.aggregatorcommon.constant.TestConstant;
import com.stockbit.aggregatorcommon.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private SomeRequest request;
    private List<String> strings = new ArrayList<>();

    @BeforeEach
    void setUp() {
        request = new SomeRequest("foo");
    }

    @Test
    void givenValidationAware_whenValidate_shouldCallValidateInValidationAware() {
        Validator.validate(request);
        assertTrue(request.validateCalled);
    }

    @Test
    void givenFail_whenValidate_shouldThrowException() {
        request = new SomeRequest("");
        Exception e = assertThrows(Exception.class, () -> Validator.validate(request));
        assertEquals(IllegalArgumentException.class, e.getClass());
        assertEquals("Foo cannot be empty", e.getMessage());
    }

    @Test
    void givenNullObject_whenValidate_shouldThrowException() {
        Exception e = assertThrows(Exception.class, () -> Validator.validate(request, new Object(), null));
        assertEquals(InvalidRequestException.class, e.getClass());
        assertEquals("Params cannot be null", e.getMessage());
    }

    @Test
    void givenRunnable_whenValidate_shouldDoRunnable() {
        Validator.validate(request, () -> strings.add(TestConstant.DUMMY));
        assertEquals(1, strings.size());
    }
}

@RequiredArgsConstructor
class SomeRequest implements ValidationAware {

    private final String foo;
    boolean validateCalled = true;

    @Override
    public void validate() {
        validateCalled = true;
        if (StringUtils.isBlank(foo)) {
            throw new IllegalArgumentException("Foo cannot be empty");
        }
    }
}
