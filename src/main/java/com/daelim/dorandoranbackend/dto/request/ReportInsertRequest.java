package com.daelim.dorandoranbackend.dto.request;

import lombok.Data;

@Data
public class ReportInsertRequest {
    String userId;
    String occurDate;
    String detail;
}
