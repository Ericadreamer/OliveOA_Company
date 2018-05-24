package com.oliveoa.controller;

public class CompanyInfoService {
    private static final CompanyInfoService ourInstance = new CompanyInfoService();

    public static CompanyInfoService getInstance() {
        return ourInstance;
    }

    private CompanyInfoService() {
    }
}
