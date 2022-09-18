package com.bank;

public class CertificateOfDeposit extends Account {
    protected int term;

    public void setMaturity(int term) {
        this.term = term;
    }

    public int getMaturity() {
        return term;
    }
}
