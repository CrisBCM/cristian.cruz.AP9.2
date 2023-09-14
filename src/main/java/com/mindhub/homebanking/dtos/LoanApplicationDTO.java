package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private int amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(Long loanId, int amount, int payments, String toAccountNumber){
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }
    public Long getLoanId() {
        return loanId;
    }

    public int getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
