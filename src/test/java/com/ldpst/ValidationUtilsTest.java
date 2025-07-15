package com.ldpst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void validateStr() {
        assertTrue(ValidationUtils.validateStr("\"79456543210\""));
        assertTrue(ValidationUtils.validateStr("\"\""));
        assertTrue(ValidationUtils.validateStr("\"the_word\""));

        assertFalse(ValidationUtils.validateStr("the_word"));
        assertFalse(ValidationUtils.validateStr(""));
    }
}