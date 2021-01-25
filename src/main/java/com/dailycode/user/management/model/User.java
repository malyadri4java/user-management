package com.dailycode.user.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "user_id", length = 50, nullable = false)
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long userId;

    @Column (name = "username", length = 50, nullable = false, unique = true)
    @Size (min = 5, max = 15)
    @NotEmpty
    private String userName;

    @Column (name = "password", length = 1024)
    private String password;

    @Column (name = "email", length = 128, nullable = false, unique = true)
    @Email
    @NotEmpty
    private String email;

    @Column (name = "first_name", length = 100)
    @Size (min = 3, max = 50)
    private String firstName;

    @Column (name = "last_name", length = 100)
    @Size (min = 3, max = 50)
    private String lastName;

    @Column (name = "company_name")
    private String companyName;

    @Column (name = "mobileNumber")
    @Positive
    @Digits (integer = 10, fraction = 0)
    private Long mobileNumber;

    @Column (name = "address")
    private String address;

    @Column (name = "city")
    private String city;

    @Column (name = "status")
    private String status;

    @Column (name = "creation_date")
    private String creationDate;

    @OneToOne (targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "role_id")
    private Role role;
}
