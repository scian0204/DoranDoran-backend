package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.controller.requestObject.ApartInfoRequest;
import com.daelim.dorandoranbackend.controller.requestObject.ReportInsertRequest;
import com.daelim.dorandoranbackend.entity.Report;
import com.daelim.dorandoranbackend.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "Report", description = "소음신고 API")
@RestController
@RequestMapping("/api/Report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @Operation(summary = "소음 신고 등록", description = "occurDate는 \"yyyy.MM.dd'T'hh:mm\" 형식으로 입력. ex) \"2023-05-16T22:30\"")
    @PostMapping("/write")
    public void insertReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        schema = @Schema(implementation = ReportInsertRequest.class)
                )
            )
            @RequestBody Map<String,Object> reportObj) throws Exception {
        reportService.insertReport(reportObj);
    }

    @Operation(summary = "소음 신고 리스트", description = "유저 아이디로 소음 신고 목록 검색 API")
    @ApiResponse(description = "유저 아이디별 소음 신고 리스트")
    @GetMapping("/list/{userId}")
    public List<Map<String, Object>> getAllReport(@PathVariable String userId) throws Exception {
        return reportService.getAllReport(userId);
    }

    @Operation(summary = "소음 신고별 상세", description = "idx(게시판 상세 id)로 소음 신고서 상세 검색 API")
    @GetMapping("/{idx}")
    public Report viewReport(@PathVariable int idx) {
        return reportService.viewReport(idx);
    }

    @Operation(summary = "소음 신고 수정")
    @PutMapping("/update")
    public void updateReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ReportInsertRequest.class)
                    )
            )
            @RequestBody Map<String,Object> reportObj, HttpSession session) throws Exception {
        reportService.updateBoard(reportObj, session);
    }

    @Operation(summary = "소음 신고 삭제")
    @PostMapping("/delete") //POST 형식
    public String deleteReport(@RequestBody Map<String,Object> reportObj, HttpSession session) throws Exception {
        return reportService.deleteBoardPost(reportObj, session);
    }

    @Operation(summary = "소음 신고 확인 여부")
    @PostMapping("/check") //POST 형식
    public String checkReport(@RequestBody Map<String,Object> reportObj, HttpSession session) throws Exception {
        return reportService.deleteBoardPost(reportObj, session);
    }
}
