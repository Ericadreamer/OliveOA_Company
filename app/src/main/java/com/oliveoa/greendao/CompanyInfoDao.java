package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.CompanyInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_INFO".
*/
public class CompanyInfoDao extends AbstractDao<CompanyInfo, Long> {

    public static final String TABLENAME = "COMPANY_INFO";

    /**
     * Properties of entity CompanyInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Fullname = new Property(3, String.class, "fullname", false, "FULLNAME");
        public final static Property Telephone = new Property(4, String.class, "telephone", false, "TELEPHONE");
        public final static Property Fax = new Property(5, String.class, "fax", false, "FAX");
        public final static Property Zipcode = new Property(6, String.class, "zipcode", false, "ZIPCODE");
        public final static Property Address = new Property(7, String.class, "address", false, "ADDRESS");
        public final static Property Website = new Property(8, String.class, "website", false, "WEBSITE");
        public final static Property Email = new Property(9, String.class, "email", false, "EMAIL");
        public final static Property Introduction = new Property(10, String.class, "introduction", false, "INTRODUCTION");
    }


    public CompanyInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"USERNAME\" TEXT," + // 1: username
                "\"PASSWORD\" TEXT," + // 2: password
                "\"FULLNAME\" TEXT," + // 3: fullname
                "\"TELEPHONE\" TEXT," + // 4: telephone
                "\"FAX\" TEXT," + // 5: fax
                "\"ZIPCODE\" TEXT," + // 6: zipcode
                "\"ADDRESS\" TEXT," + // 7: address
                "\"WEBSITE\" TEXT," + // 8: website
                "\"EMAIL\" TEXT," + // 9: email
                "\"INTRODUCTION\" TEXT);"); // 10: introduction
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyInfo entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String fullname = entity.getFullname();
        if (fullname != null) {
            stmt.bindString(4, fullname);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(5, telephone);
        }
 
        String fax = entity.getFax();
        if (fax != null) {
            stmt.bindString(6, fax);
        }
 
        String zipcode = entity.getZipcode();
        if (zipcode != null) {
            stmt.bindString(7, zipcode);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String website = entity.getWebsite();
        if (website != null) {
            stmt.bindString(9, website);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(11, introduction);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyInfo entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String fullname = entity.getFullname();
        if (fullname != null) {
            stmt.bindString(4, fullname);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(5, telephone);
        }
 
        String fax = entity.getFax();
        if (fax != null) {
            stmt.bindString(6, fax);
        }
 
        String zipcode = entity.getZipcode();
        if (zipcode != null) {
            stmt.bindString(7, zipcode);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String website = entity.getWebsite();
        if (website != null) {
            stmt.bindString(9, website);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String introduction = entity.getIntroduction();
        if (introduction != null) {
            stmt.bindString(11, introduction);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyInfo readEntity(Cursor cursor, int offset) {
        CompanyInfo entity = new CompanyInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fullname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // telephone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // fax
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // zipcode
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // website
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // email
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // introduction
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyInfo entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFullname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTelephone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFax(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setZipcode(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setWebsite(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEmail(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setIntroduction(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyInfo entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyInfo entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyInfo entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
