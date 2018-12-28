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

package com.devproserv.courses.validation;

import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.rules.VldRuleContainsLetters;
import com.devproserv.courses.validation.rules.VldRuleNotEmpty;
import com.devproserv.courses.validation.rules.VldRuleNotNull;
import com.devproserv.courses.validation.rules.VldRuleStartLetter;

/**
 * Combines rules to validate field "username".
 *
 * @since 1.0.0
 */
public class VldUsername {
    /**
     * Field.
     */
    private final String field;

    /**
     * Constructor.
     * @param field Field to check
     */
    public VldUsername(final String field) {
        this.field = field;
    }

    /**
     * Validates the field.
     *
     * @return Validation result
     */
    public VldResult validate() {
        return new VldRuleNotNull("Username and password should not be null!")
            .and(
                new VldRuleNotEmpty(
                    "Username and password should not be empty!"
                )
            )
            .and(
                new VldRuleStartLetter(
                    "Username shouldn't start with digit or non letter!"
                )
            )
            .and(
                new VldRuleContainsLetters(
                    "Username should contain only letters and digits!"
                )
            )
            .apply(this.field);
    }
}
