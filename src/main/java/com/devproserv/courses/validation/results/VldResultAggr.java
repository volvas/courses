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

package com.devproserv.courses.validation.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates several validation result in one object.
 *
 * @since 1.0.0
 */
public class VldResultAggr {
    /**
     * Stores intermediate results.
     */
    private final List<VldResult> results = new ArrayList<>(8);

    /**
     * Add the given result to storage.
     *
     * @param result Validation result
     * @return This instance
     */
    public VldResultAggr join(final VldResult result) {
        this.results.add(result);
        return this;
    }

    /**
     * Returns final result. If someone is not valid, it'll be sent
     *  as a result. So first join most important results.
     *
     * @return Final validation result
     */
    public VldResult aggregate() {
        return this.results.stream()
            .filter(result -> !result.valid())
            .findFirst()
            .orElse(new VldResultValid());
    }
}
