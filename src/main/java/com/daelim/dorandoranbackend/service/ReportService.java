package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.entity.Report;
import com.daelim.dorandoranbackend.repository.ReportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class ReportService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired()
    ReportRepository reportRepository;

    public void insertReport(Map<String, Object> reportObj) { //신고서 작성
        Report report = objMpr.convertValue(reportObj, Report.class);
        reportRepository.save(report);
    }

    public List<Map<String, Object>> getAllReport(String userId) { //신고서 리스트 처리
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Report> reports;

        if (userId == "admin") { // admin 유저 생성 후 확인하기
            reports = reportRepository.findAll();
        } else {
            reports = reportRepository.findAllByUserId(userId);
        }

        reports.forEach(report -> {
            Map<String, Object> result = new HashMap<>();
            result.put("report", report);
            resultList.add(result);
        });

        return resultList;
    }

    public Report viewReport(Integer idx) { //신고서 상세 페이지
        return reportRepository.findById(idx).get();
    }

    public void updateBoard(Map<String, Object> boardObj, HttpSession session) throws Exception { //신고서 수정
        Report report = objMpr.convertValue(boardObj, Report.class);

        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(report.getUserId())) {

            Optional<Report> optBoard = reportRepository.findById(report.getIdx());
            Report report1 = optBoard.get();
            report.setReportDate(report1.getReportDate());

            reportRepository.save(report);
        }
    }

    public String deleteBoardPost(Map<String, Object> boardObj, HttpSession session) { //게시물 삭제
        Integer idx = Integer.parseInt((String) boardObj.get("idx"));
        String userId = (String) boardObj.get("userId");

        System.out.println("test");
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(userId)) {
            reportRepository.deleteById(idx);
            return "0"; //userId 동일 = 삭제됨
        } else {
            return "1";
        }
    }
}
