package com.server.gymServerApplication.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.infor.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(indexes = {@Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_phone", columnList = "phone"),
        @Index(name = "idx_faceId", columnList = "faceId")})
public class User extends BaseObject {

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avata;

    private String faceId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private UserInformation userInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bill> bills;

    @OneToOne
    private Membership membership;

    @OneToOne
    private Calendar calendar;

}
