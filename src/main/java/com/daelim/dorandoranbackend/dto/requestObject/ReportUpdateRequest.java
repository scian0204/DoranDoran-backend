package com.daelim.dorandoranbackend.controller.requestObject;

import lombok.Data;

@Data
public class ReportUpdateRequest {
    int idx;
    String userId;
    String occurDate;
    String detail;
}
