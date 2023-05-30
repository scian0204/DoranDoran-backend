package com.daelim.dorandoranbackend.controller.requestObject;

import lombok.Data;

@Data
public class ReportInsertRequest {
    String userId;
    String occurDate;
    String detail;
}
