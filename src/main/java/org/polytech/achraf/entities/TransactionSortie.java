package main.java.org.polytech.achraf.entities;

import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class TransactionSortie extends Transaction{

}
