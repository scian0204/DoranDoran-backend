package com.daelim.dorandoranbackend.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Apart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apartIdx;
    private Integer apartId;
    private String dong;
    private String ho;

    public Apart(Apart apart) {
        this.apartIdx = apart.getApartIdx();
        this.apartId = apart.getApartId();
        this.dong = apart.getDong();
        this.ho = apart.getHo();
    }
}
