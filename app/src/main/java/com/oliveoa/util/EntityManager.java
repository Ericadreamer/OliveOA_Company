package com.oliveoa.util;


import com.oliveoa.greendao.CompanyInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.greendao.PropertiesInfoDao;


public class EntityManager {
    private static EntityManager entityManager;
    public CompanyInfoDao companyInfoDao;
    public DepartmentInfoDao departmentInfoDao;
    public DutyInfoDao dutyInfoDao;
    public EmployeeInfoDao employeeInfoDao;
    public PropertiesInfoDao propertiesInfoDao;


    /*
     * 创建CompanyInfo表实例
     *
     * @return
     */

    public CompanyInfoDao getCompanyInfo() {
        companyInfoDao = DaoManager.getInstance().getSession().getCompanyInfoDao();
        return companyInfoDao;

    }

     /*
     * 创建Department表实例
     *
     * @return
     */


    public DepartmentInfoDao getDepartmentInfo(){
        departmentInfoDao = DaoManager.getInstance().getSession().getDepartmentInfoDao();
        return departmentInfoDao;
    }

/**
     * 创建DutyInfo表实例
     *
     * @return
*/

    public DutyInfoDao getDutyInfoInfo(){
        dutyInfoDao = DaoManager.getInstance().getSession().getDutyInfoDao();
        return dutyInfoDao;
    }

/*
     * 创建EmployeeInfo表实例
     *
     * @return
*/

    public EmployeeInfoDao getEmployeeInfoDao(){
        employeeInfoDao = DaoManager.getInstance().getSession().getEmployeeInfoDao();
        return employeeInfoDao;
    }

/*
     * 创建PropertiesInfo表实例
     *
     * @return
*/

    public PropertiesInfoDao getPropertiesDao(){
        propertiesInfoDao = DaoManager.getInstance().getSession().getPropertiesInfoDao();
        return propertiesInfoDao;
    }



/*
     * 创建单例
     *
     * @return
*/

    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}
