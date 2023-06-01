package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.entity.WarningMessage;
import com.daelim.dorandoranbackend.service.WarningMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WarningMessage", description = "유저별 경고 메시지 컨트롤러")
@RestController
@RequestMapping("/warningMsg")
public class WarningMessageController {
    @Autowired
    WarningMessageService warningMessageService;

    @Operation(summary = "유저별 경고메시지 목록 API")
    @GetMapping("/{userId}")
    public Response<Page<WarningMessage>> getMessage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "idx") Pageable pageable, @PathVariable String userId) {
        return warningMessageService.getMessage(pageable, userId);
    }
    
}
