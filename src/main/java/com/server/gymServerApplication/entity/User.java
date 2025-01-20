package com.server.gymServerApplication.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.gymServerApplication.entity.baseObj.BaseObject;
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
        @Index(name = "idx_phone", columnList = "phone")})
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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avata;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bill> bills;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    @Temporal(TemporalType.DATE)
//    private Date birthDate;
//
//
//    private boolean isEnable;
//
//

//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//
//    private String uid;
//
//    @Column(length = 150)
//    @Size(max = 150, message = "Describe cannot exceed 150 characters")
//    private String description;
//
//
//    @Size(max = 20, min = 4, message = "tag name k hop le !")
//    private String tagName;
//
//
//    /**
//     * set mật định ngay sinh là 01/01/2000
//     */
//    public void init() {
//        if (this.birthDate == null) {
//            try {
//                this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
//            } catch (ParseException e) {
//                throw new RuntimeException("Error parsing default date", e);
//            }
//        }
//    }
}
