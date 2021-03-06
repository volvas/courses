/*
 * This file is generated by jOOQ.
 */
package com.devproserv.courses.jooq.tables;


import com.devproserv.courses.jooq.Coursedb;
import com.devproserv.courses.jooq.Indexes;
import com.devproserv.courses.jooq.Keys;
import com.devproserv.courses.jooq.tables.records.LecturersRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Lecturers extends TableImpl<LecturersRecord> {

    private static final long serialVersionUID = 1698074389;

    /**
     * The reference instance of <code>coursedb.lecturers</code>
     */
    public static final Lecturers LECTURERS = new Lecturers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LecturersRecord> getRecordType() {
        return LecturersRecord.class;
    }

    /**
     * The column <code>coursedb.lecturers.lect_id</code>.
     */
    public final TableField<LecturersRecord, Integer> LECT_ID = createField("lect_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>coursedb.lecturers.degree</code>.
     */
    public final TableField<LecturersRecord, String> DEGREE = createField("degree", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * Create a <code>coursedb.lecturers</code> table reference
     */
    public Lecturers() {
        this(DSL.name("lecturers"), null);
    }

    /**
     * Create an aliased <code>coursedb.lecturers</code> table reference
     */
    public Lecturers(String alias) {
        this(DSL.name(alias), LECTURERS);
    }

    /**
     * Create an aliased <code>coursedb.lecturers</code> table reference
     */
    public Lecturers(Name alias) {
        this(alias, LECTURERS);
    }

    private Lecturers(Name alias, Table<LecturersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Lecturers(Name alias, Table<LecturersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Lecturers(Table<O> child, ForeignKey<O, LecturersRecord> key) {
        super(child, key, LECTURERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Coursedb.COURSEDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LECTURERS_FK_LECTURERS_USERS1_IDX, Indexes.LECTURERS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LecturersRecord> getPrimaryKey() {
        return Keys.KEY_LECTURERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LecturersRecord>> getKeys() {
        return Arrays.<UniqueKey<LecturersRecord>>asList(Keys.KEY_LECTURERS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<LecturersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<LecturersRecord, ?>>asList(Keys.FK_LECTURERS_USERS1);
    }

    public Users users() {
        return new Users(this, Keys.FK_LECTURERS_USERS1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lecturers as(String alias) {
        return new Lecturers(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lecturers as(Name alias) {
        return new Lecturers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Lecturers rename(String name) {
        return new Lecturers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Lecturers rename(Name name) {
        return new Lecturers(name, null);
    }
}
