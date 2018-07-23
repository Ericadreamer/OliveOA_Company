package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.EmployeeInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EMPLOYEE_INFO".
*/
public class EmployeeInfoDao extends AbstractDao<EmployeeInfo, Void> {

    public static final String TABLENAME = "EMPLOYEE_INFO";

    /**
     * Properties of entity EmployeeInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Eid = new Property(0, String.class, "eid", false, "EID");
        public final static Property Dcid = new Property(1, String.class, "dcid", false, "DCID");
        public final static Property Pcid = new Property(2, String.class, "pcid", false, "PCID");
        public final static Property Id = new Property(3, String.class, "id", false, "ID");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Sex = new Property(5, String.class, "sex", false, "SEX");
        public final static Property Birth = new Property(6, String.class, "birth", false, "BIRTH");
        public final static Property Tel = new Property(7, String.class, "tel", false, "TEL");
        public final static Property Email = new Property(8, String.class, "email", false, "EMAIL");
        public final static Property Address = new Property(9, String.class, "address", false, "ADDRESS");
        public final static Property Orderby = new Property(10, int.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(11, String.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(12, String.class, "updatetime", false, "UPDATETIME");
    }


    public EmployeeInfoDao(DaoConfig config) {
        super(config);
    }
    
    public EmployeeInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EMPLOYEE_INFO\" (" + //
                "\"EID\" TEXT," + // 0: eid
                "\"DCID\" TEXT," + // 1: dcid
                "\"PCID\" TEXT," + // 2: pcid
                "\"ID\" TEXT," + // 3: id
                "\"NAME\" TEXT," + // 4: name
                "\"SEX\" TEXT," + // 5: sex
                "\"BIRTH\" TEXT," + // 6: birth
                "\"TEL\" TEXT," + // 7: tel
                "\"EMAIL\" TEXT," + // 8: email
                "\"ADDRESS\" TEXT," + // 9: address
                "\"ORDERBY\" INTEGER NOT NULL ," + // 10: orderby
                "\"CREATETIME\" TEXT," + // 11: createtime
                "\"UPDATETIME\" TEXT);"); // 12: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EMPLOYEE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EmployeeInfo entity) {
        stmt.clearBindings();
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(1, eid);
        }
 
        String dcid = entity.getDcid();
        if (dcid != null) {
            stmt.bindString(2, dcid);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(4, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(7, birth);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(8, tel);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(9, email);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(10, address);
        }
        stmt.bindLong(11, entity.getOrderby());
 
        String createtime = entity.getCreatetime();
        if (createtime != null) {
            stmt.bindString(12, createtime);
        }
 
        String updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindString(13, updatetime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EmployeeInfo entity) {
        stmt.clearBindings();
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(1, eid);
        }
 
        String dcid = entity.getDcid();
        if (dcid != null) {
            stmt.bindString(2, dcid);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(4, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(7, birth);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(8, tel);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(9, email);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(10, address);
        }
        stmt.bindLong(11, entity.getOrderby());
 
        String createtime = entity.getCreatetime();
        if (createtime != null) {
            stmt.bindString(12, createtime);
        }
 
        String updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindString(13, updatetime);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public EmployeeInfo readEntity(Cursor cursor, int offset) {
        EmployeeInfo entity = new EmployeeInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // eid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dcid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pcid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sex
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // birth
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // tel
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // email
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // address
            cursor.getInt(offset + 10), // orderby
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // createtime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EmployeeInfo entity, int offset) {
        entity.setEid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDcid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPcid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSex(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBirth(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTel(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setEmail(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAddress(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setOrderby(cursor.getInt(offset + 10));
        entity.setCreatetime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUpdatetime(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(EmployeeInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(EmployeeInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(EmployeeInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
