package com.backend.dto;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
public class Credentials {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
   @Column(name = "credential")
   private String credential;

    public Integer getId() {
        return id;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
