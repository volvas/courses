/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vladimir
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

package com.devproserv.courses.validation.rules;

import com.devproserv.courses.validation.results.VldResult;
import com.devproserv.courses.validation.results.VldResultInvalid;
import com.devproserv.courses.validation.results.VldResultValid;
import java.util.function.Predicate;

/**
 * Common validating rule wrapping predicate and message.
 *
 * @since 0.5.3
 */
public final class VldRuleCommon implements VldRule {
    /**
     * Condition.
     */
    private final Predicate<String> condition;

    /**
     * Message.
     */
    private final String message;

    /**
     * Primary constructor.
     *
     * @param condition Condition
     * @param message Message
     */
    public VldRuleCommon(final Predicate<String> condition, final String message) {
        this.condition = condition;
        this.message = message;
    }

    @Override
    public VldResult apply(final String param) {
        final VldResult result;
        if (this.condition.test(param)) {
            result = new VldResultInvalid(this.message);
        } else {
            result = new VldResultValid();
        }
        return result;
    }
}
