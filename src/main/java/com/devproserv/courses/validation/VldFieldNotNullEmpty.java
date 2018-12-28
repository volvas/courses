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
import com.devproserv.courses.validation.rules.VldRuleNotEmpty;
import com.devproserv.courses.validation.rules.VldRuleNotNull;

/**
 * Combines rules to validate field on null and empty status.
 *
 * @since 1.0.0
 */
public class VldFieldNotNullEmpty {
    /**
     * Field.
     */
    private final String field;

    /**
     * Name of the field.
     */
    private final String name;

    /**
     * Constructor.
     * @param field Field to check
     * @param name Name of the field
     */
    public VldFieldNotNullEmpty(final String field, final String name) {
        this.field = field;
        this.name = name;
    }

    /**
     * Validates the field.
     *
     * @return Validation result
     */
    public VldResult validate() {
        return new VldRuleNotNull(
            String.format("%s should not be null!", this.name)
        ).and(
            new VldRuleNotEmpty(
                String.format("%s should not be empty!", this.name)
            )
        )
        .apply(this.field);
    }
}
