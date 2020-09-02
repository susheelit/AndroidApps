package com.ceo.elc.model;

public class ModelPS {
private String PSNO, PSNAME;

    public ModelPS(String PSNO, String PSNAME) {
        this.PSNO = PSNO;
        this.PSNAME = PSNAME;
    }

    public String getPSNO() {
        return PSNO;
    }

    public void setPSNO(String PSNO) {
        this.PSNO = PSNO;
    }

    public String getPSNAME() {
        return PSNAME;
    }

    public void setPSNAME(String PSNAME) {
        this.PSNAME = PSNAME;
    }
}
