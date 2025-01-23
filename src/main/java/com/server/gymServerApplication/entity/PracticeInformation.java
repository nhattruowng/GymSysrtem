package com.server.gymServerApplication.entity;


import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PracticeInformation extends BaseObject {

    private String description; // thong tin buoi tap

    @ManyToOne
    @JoinColumn(name = "pt_id")
    private User pt; // PT

    private float calo; /// luu calo

    private int time; /// luu thoi gian tap (phut)

    private LocalDateTime dateTime; // luu ngay tap

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
}
