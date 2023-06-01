package com.daelim.dorandoranbackend.dto.requestObject;

import lombok.Data;

import java.util.List;

@Data
public class ApartAllRequest {
    Integer apartId;
    List<String> dongs;
    Integer numOfFloor;
    Integer numOfHo;
}
