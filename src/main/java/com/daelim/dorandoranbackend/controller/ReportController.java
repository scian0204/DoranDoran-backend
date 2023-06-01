package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.request.ReportCheckRequest;
import com.daelim.dorandoranbackend.dto.request.ReportDeleteRequest;
import com.daelim.dorandoranbackend.dto.request.ReportInsertRequest;
import com.daelim.dorandoranbackend.dto.request.ReportUpdateRequest;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.entity.Report;
import com.daelim.dorandoranbackend.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody Map<String,Object> reportObj) {
        reportService.insertReport(reportObj);
    }

    @Operation(summary = "소음 신고 리스트", description = "유저 아이디로 소음 신고 목록 검색 API")
    @ApiResponse(description = "유저 아이디별 소음 신고 리스트")
    @GetMapping("/list/{userId}")
    public List<Map<String, Object>> getAllReport(@PathVariable String userId) {
        return reportService.getAllReport(userId);
    }

    @Operation(summary = "소음 신고별 상세", description = "idx(게시판 상세 id)로 소음 신고서 상세 검색 API")
    @GetMapping("/{idx}")
    public Report viewReport(@PathVariable int idx) {
        return reportService.viewReport(idx);
    }

    @Operation(summary = "소음 신고 수정", description = "등록과 동일하게 occurDate는 \"yyyy.MM.dd'T'hh:mm\" 형식으로 입력")
    @PutMapping("/update")
    public void updateReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ReportUpdateRequest.class)
                    )
            )
            @RequestBody Map<String,Object> reportObj, HttpSession session) {
        reportService.updateBoard(reportObj, session);
    }

    @Operation(summary = "소음 신고 삭제")
    @ApiResponse(description = "삭제 성공: 0<br>삭제실패-로그인 된 userId와 일치하지 않음: 1")
    @PostMapping("/delete/{idx}")
    public Response<String> deleteReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ReportDeleteRequest.class)
                    )
            )
            @PathVariable int idx, HttpSession session) {
        return reportService.deleteBoardPost(idx, session);
    }

    @Operation(summary = "소음 신고 확인 여부")
    @ApiResponse(description = "확인 여부 수정 성공: 0 (수정 성공 시 소음 신고 확인 여부에 관리자ID (userId) 입력)" +
            "<br>수정실패-일반 사용자로 관리자만 확인 가능: 1")
    @PutMapping("/check")
    public Response<String> checkReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ReportCheckRequest.class)
                    )
            )
            @RequestBody Map<String,Object> reportObj){
        return reportService.checkReport(reportObj);
    }
}
