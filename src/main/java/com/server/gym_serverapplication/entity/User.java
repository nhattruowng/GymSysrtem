package com.server.gym_serverapplication.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.gym_serverapplication.entity.base.BaseObject;
import com.server.gym_serverapplication.entity.infor.Gender;
import com.server.gym_serverapplication.entity.infor.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@RedisHash("user")
@Table(indexes = {@Index(name = "idx_email", columnList = "email")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY )
public class User extends BaseObject {

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, message = "Password")
    private String password;

    @Column(unique = true)
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;


    private boolean isEnable;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avata;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    private String uid;

    @Column(length = 150)
    @Size(max = 150, message = "Describe cannot exceed 150 characters")
    private String description;

    private String address;

    @Size(max = 20, min = 4, message = "tag name k hop le !")
    private String tagName;


    /**
     * set mật định ngay sinh là 01/01/2000
     */
    public void init() {
        if (this.birthDate == null) {
            try {
                this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing default date", e);
            }
        }
    }
}
