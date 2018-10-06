package com.oliveoa.jsonbean;

import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.pojo.OfficialDocumentIssued;

import java.util.ArrayList;

public class OfficialDocumentInfoJsonBean {
    private OfficialDocument officialDocument;
    private ArrayList<OfficialDocumentIssued> officialDocumentIssueds;
    private ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads;

    public OfficialDocumentInfoJsonBean() {
    }

    public OfficialDocumentInfoJsonBean(OfficialDocument officialDocument, ArrayList<OfficialDocumentIssued> officialDocumentIssueds, ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads) {
        this.officialDocument = officialDocument;
        this.officialDocumentIssueds = officialDocumentIssueds;
        this.officialDocumentCirculreads = officialDocumentCirculreads;
    }

    public OfficialDocument getOfficialDocument() {
        return officialDocument;
    }

    public void setOfficialDocument(OfficialDocument officialDocument) {
        this.officialDocument = officialDocument;
    }

    public ArrayList<OfficialDocumentIssued> getOfficialDocumentIssueds() {
        return officialDocumentIssueds;
    }

    public void setOfficialDocumentIssueds(ArrayList<OfficialDocumentIssued> officialDocumentIssueds) {
        this.officialDocumentIssueds = officialDocumentIssueds;
    }

    public ArrayList<OfficialDocumentCirculread> getOfficialDocumentCirculreads() {
        return officialDocumentCirculreads;
    }

    public void setOfficialDocumentCirculreads(ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads) {
        this.officialDocumentCirculreads = officialDocumentCirculreads;
    }

    @Override
    public String toString() {
        return "OfficialDocumentInfoJsonBean{" +
                "officialDocument=" + officialDocument +
                ", officialDocumentIssueds=" + officialDocumentIssueds +
                ", officialDocumentCirculreads=" + officialDocumentCirculreads +
                '}';
    }
}
