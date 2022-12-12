package com.example.myapplication.dao;

import com.example.myapplication.SitCode;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

public class DaoSession extends AbstractDaoSession {
    private final DaoConfig sitCodeDaoConfig;

    private final SitCodeDao sitCodeDao;

    public DaoSession(Database db, IdentityScopeType type,
                      Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);

        sitCodeDaoConfig = daoConfigMap.get(SitCodeDao.class).clone();
        sitCodeDaoConfig.initIdentityScope(type);

        sitCodeDao = new SitCodeDao(sitCodeDaoConfig, this);
        registerDao(SitCode.class, sitCodeDao);

    }

    public void clear() {
        sitCodeDaoConfig.clearIdentityScope();
    }

    public SitCodeDao getSitCodeDao() {
        return sitCodeDao;
    }
}
