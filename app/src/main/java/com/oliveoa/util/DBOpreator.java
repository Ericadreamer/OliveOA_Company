package com.oliveoa.util;

import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.List;

public class DBOpreator {

    public void DepartmentDaoUpdate(List<DepartmentInfo> departmentInfos){
        DepartmentInfoDao departmentInfoDao =EntityManager.getInstance().getDepartmentInfo();
        List<DepartmentInfo> dp = departmentInfoDao.queryBuilder().list();
        departmentInfoDao.deleteAll();

        for (int i = 0; i < departmentInfos.size(); i++) {
            departmentInfoDao.insertOrReplace(departmentInfos.get(i));
        }
    }

    public void DutyDaoUpdate(List<DutyInfo> dutyInfos){
        DutyInfoDao dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
        for (int j = 0; j < dutyInfos.size(); j++) {
            dutyInfoDao.insertOrReplace(dutyInfos.get(j));
        }
    }
}
