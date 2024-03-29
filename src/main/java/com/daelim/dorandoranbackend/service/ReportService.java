package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.response.Error;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.entity.ApartUser;
import com.daelim.dorandoranbackend.entity.Report;
import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApartUserRepository apartUserRepository;

    // 2023-09-20_apartId 대신 apartIdx를 넣어 왜래키 오류 나서 고침
    @Autowired
    ApartRepository apartRepository;

    public void insertReport(Map<String, Object> reportObj) { // 신고서 작성
        Report report = objMpr.convertValue(reportObj, Report.class);
        String userId = String.valueOf(reportObj.get("userId"));
        ApartUser apartUser = apartUserRepository.findByUserId(userId);


        Integer apartId = apartRepository.findFirstByApartIdx(apartUser.getApartIdx()).getApartId();


//        System.out.println(">> Apart ID : " + apartUser.getApartIdx()); // 확인용

        report.setApartId(apartId);

        reportRepository.save(report);
    }

    public List<Map<String, Object>> getAllReport(String userId) { // 신고서 리스트 처리
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Report> reports = null;
        User user = userRepository.findByUserId(userId).get();
//        System.out.println(">> isAdmin : " + user.getIsAdmin()); // 확인용

        if (user.getIsAdmin() != null) {
            int isAdmin = user.getIsAdmin();
            reports = reportRepository.findAllByApartId(isAdmin);
        } else if (user.getIsAdmin() == null) {
            reports = reportRepository.findAllByUserId(userId);
        }

        reports.forEach(report -> {
            Map<String, Object> result = new HashMap<>();
            result.put("report", report);
            resultList.add(result);
        });

        return resultList;
    }

    public Report viewReport(Integer idx) { // 신고서 상세 페이지
        return reportRepository.findById(idx).get();
    }

    public void updateBoard(Map<String, Object> reportObj, HttpSession session) { // 신고서 수정
        Report report = objMpr.convertValue(reportObj, Report.class);

        session.setAttribute("userId", reportObj.get("userId")); // 테스트용

        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(report.getUserId())) {
            Optional<Report> optReport = reportRepository.findById(report.getIdx());
            Report report1 = optReport.get();
            report.setReportDate(report1.getReportDate());

            reportRepository.save(report);
        }
    }

    public Response<String> deleteBoardPost(Integer idx, HttpSession session) { //신고서 삭제
        Response<String> res = new Response<>();
        Report report = reportRepository.findAllByIdx(idx);
        String userId = report.getUserId();

        session.setAttribute("userId", "test1"); // 테스트용

        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(userId)) {
            reportRepository.deleteById(idx);
        } else {
//            System.out.println(">> session userId : " + session.getAttribute("userId")); // 확인용
//            System.out.println(">> reportObj userId : " + userId); // 확인용
            Error error = new Error();
            error.setErrorId(1);
            error.setMessage("로그인 된 userId와 일치하지 않음");
            res.setError(error);
        }
        return res;
    }

    public Response<String> checkReport(Map<String, Object> reportObj) { // 신고서 확인 체크용
        Response<String> res = new Response<>();

        Integer idx = Integer.parseInt(String.valueOf(reportObj.get("idx")));
        Report report = reportRepository.findAllByIdx(idx);

        String userId = String.valueOf(reportObj.get("userId"));
        User user = userRepository.findByUserId(userId).get();

        if (user.getIsAdmin() != null) {
            System.out.println(">> isCheck : " + report.getIsCheck());
            if (report.getIsCheck() == null) {
                report.setIsCheck(userId);
            } else {
                report.setIsCheck(null);
            }
            reportRepository.save(report);
        } else if (user.getIsAdmin() == null) {
            Error error = new Error();
            error.setErrorId(1);
            error.setMessage("관리자만 확인 가능");
            res.setError(error);
        }
        return res;
    }

}
