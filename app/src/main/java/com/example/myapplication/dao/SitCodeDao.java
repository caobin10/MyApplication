package com.example.myapplication.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.SitCode;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class SitCodeDao extends AbstractDao<SitCode, Void> {
    public static final String TABLENAME = "MyTest";
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "id");
        public final static Property Code = new Property(1, String.class, "code", false, "code");
        public final static Property Name = new Property(2, String.class, "name", false, "name");
    }
    public SitCodeDao(DaoConfig config) {
        super(config);
    }

    public SitCodeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SitCode entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
    }
    @Override
    protected final void bindValues(SQLiteStatement stmt, SitCode entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }

        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
    }
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override
    public SitCode readEntity(Cursor cursor, int offset) {
        SitCode entity = new SitCode(
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // code
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // name
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, SitCode entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
    }

    @Override
    protected final Void updateKeyAfterInsert(SitCode entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    @Override
    public Void getKey(SitCode entity) {
        return null;
    }

    @Override
    public boolean hasKey(SitCode entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
