package com.c.ctgapp.mvvm.model;

public class CertificationinfoLiveData extends MutableLiveData<Certificationinfo> {
    private CertificationinfoLiveData() {
    }

    private static class Holder {
        public static final CertificationinfoLiveData INSTANCE = new CertificationinfoLiveData();
    }

    public static CertificationinfoLiveData getInstance() {
        return Holder.INSTANCE;
    }
}
