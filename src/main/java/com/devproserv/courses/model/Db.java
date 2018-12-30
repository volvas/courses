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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides connection to the database.
 *
 * @since 0.5.0
 */
public class Db {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Db.class);

    /**
     * DB URL.
     */
    private static final String DB_URL = "java:comp/env/jdbc/coursedb";

    /**
     * DataSource.
     */
    private DataSource source;

    /**
     * Finds a data source that can give a DB connection.
     * @return DataSource
     */
    public DataSource dataSource() {
        if (this.source == null) {
            try {
                final InitialContext ctx = new InitialContext();
                this.source = (DataSource) ctx.lookup(Db.DB_URL);
            } catch (final NamingException exc) {
                LOGGER.error("Source with path '{}' not found! Error: {}", Db.DB_URL, exc);
            }
        }
        return this.source;
    }
}
