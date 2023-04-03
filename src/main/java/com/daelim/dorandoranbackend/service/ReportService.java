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
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired()
    ReportRepository reportRepository;

    public void write(Map<String, Object> boardObj, MultipartFile file) throws Exception { //글 작성 처리
        Report report = objMpr.convertValue(boardObj, Report.class);
//        String idx = (String) boardObj.get("idx");
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\files"; //저장할 경로 지정

        if (file != null) {
            File saveFile = new File(filePath, boardObj.get("idx").toString());
            file.transferTo(saveFile);
//            report.setImageLoc(String.valueOf(saveFile));
        }

        reportRepository.save(report);
    }

    public Page<Report> getAllBoards(Pageable pageable) { //게시글 리스트 처리
//        long boardsCount = boardRepository.count(); //게시글 수 0개 조건문??
        return reportRepository.findAll(pageable);
    }

    public Report viewBoard(Integer idx) { //게시물 상세 페이지
        return reportRepository.findById(idx).get();
    }

    public void updateBoard(Map<String, Object> boardObj, HttpSession session) throws Exception { //게시물 수정
//        System.out.println("test1");
        Report report = objMpr.convertValue(boardObj, Report.class);
//        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\files"; //이미지 수정용 주소 (필요한가??)

//        session.setAttribute("userId", "test"); //테스트용
//        System.out.println(session.getAttribute("userId") != null && session.getAttribute("userId").equals(board.getUserId()));
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(report.getUserId())) {
//            System.out.println("test2");

//            board.setTitle(board.getTitle());
//            board.setContent(board.getContent()); //?

            Optional<Report> optBoard = reportRepository.findById(report.getIdx());
            Report report1 = optBoard.get();
            report.setReportDate(report1.getReportDate());
//            report.setImageLoc(report1.getImageLoc()); // 날짜와 파일경로 null값으로 넘길 경우 db도 null 수정됨을 방지

//            File saveFile = new File(filePath, (String) boardObj.get("idx"));
//            file.transferTo(saveFile);
//            board.setImageLoc(String.valueOf(saveFile));

            reportRepository.save(report);
        }
    }

/*    public void deleteBoard(Integer idx, HttpSession session) { //게시물 삭제
        //userId가 접속된 userId와 동일한지 비교??,,,,
//        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(board.getUserId())) {
//        }
        boardRepository.deleteById(idx);
    }*/

    public String deleteBoardPost(Map<String, Object> boardObj, HttpSession session) { //게시물 삭제
        Integer idx = Integer.parseInt((String) boardObj.get("idx"));
        String userId = (String) boardObj.get("userId");

        System.out.println("test");
        if (session.getAttribute("userId") != null && session.getAttribute("userId").equals(userId)) {
            reportRepository.deleteById(idx);
            return "0"; //userId 동일 = 삭제됨
        } else {
            return "1"; //
        }
    }
}
