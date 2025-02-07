package com.server.gymServerApplication.entity.mysql;

import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Table(indexes = {@Index("id_")})
public class Calendar extends BaseObject {

    @OneToOne
    private User user;

    @OneToOne
    private Service service;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PracticeInformation> practiceInformation;

}
