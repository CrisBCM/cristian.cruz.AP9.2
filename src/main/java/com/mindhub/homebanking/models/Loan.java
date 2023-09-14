package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private int maxAmount;
    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();
    @OneToMany(mappedBy = "loan")
    private Set<ClientLoan> loans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, int maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Set<ClientLoan> getLoans() {
        return loans;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
    public void addLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        loans.add(clientLoan);
    }
}
