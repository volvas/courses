/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Vladimir
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.devproserv.courses.form;

/**
 * Number validation.
 */
public final class NumberValidation implements Validation {
    /**
     * Number.
     */
    private final String number;

    /**
     * Message.
     */
    private String message;

    /**
     * Constructor.
     * @param number Number
     */
    public NumberValidation(String number) {
        // TODO make default access after refactoring subscribe
        this.number = number;
    }

    @Override
    public boolean validated() {
        boolean result = true;
        message = "ok";
        if (number == null || number.isEmpty()) {
            message = "Field should not be empty!";
            result = false;
        } else if (number.matches(".*\\D+.*")) {
            message = "Field should contain only digits";
            result = false;
        }
        return result;
    }

    @Override
    public String errorMessage() {
        return message;
    }
}
