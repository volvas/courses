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

package com.devproserv.courses.model;

/**
 * Represents the entity of Course. Maps the table 'courses' in the database.
 * Part of DAO design pattern.
 *
 * @since 0.5.0
 */
public class Course {
    /**
     * ID.
     */
    private final int id;

    /**
     * Name.
     */
    private final String name;

    /**
     * Description.
     */
    private final String description;

    /**
     * Primary constructor.
     * @param id ID
     * @param name Name
     * @param description Description
     */
    public Course(final int id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter.
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter.
     * @return Description
     */
    public String getDescription() {
        return this.description;
    }
}
