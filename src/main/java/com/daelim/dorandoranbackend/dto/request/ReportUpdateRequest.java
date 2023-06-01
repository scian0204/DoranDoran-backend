package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class ReportUpdateRequest {
    int idx;
    String userId;
    String occurDate;
    String detail;
}
