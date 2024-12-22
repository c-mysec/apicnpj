package com.example.supplier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj; // Change the type from long to String, since CNPJ can contain letters now
    private String nomeContato;
    private String emailContato;
    private String telefoneContato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() { // Use string type for CNPJ that can contain letters and numbers
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = (cnpj == null || cnpj.length() != 14) ? "" : cnpj.toUpperCase(); // Convert CNPJ input to uppercase if it is not empty and has exactly 14 characters
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }
}