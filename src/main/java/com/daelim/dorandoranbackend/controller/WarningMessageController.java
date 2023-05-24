package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.controller.responseObject.Response;
import com.daelim.dorandoranbackend.entity.WarningMessage;
import com.daelim.dorandoranbackend.service.WarningMessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "WarningMessage", description = "유저별 경고 메시지 컨트롤러")
@RestController
@RequestMapping("/warningMsg")
public class WarningMessageController {
    @Autowired
    WarningMessageService warningMessageService;

    @GetMapping("/{userId}")
    public Response<List<WarningMessage>> getMessage(@PathVariable String userId) {
        return warningMessageService.getMessage(userId);
    }
    
}
