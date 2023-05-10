package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.controller.requestObject.ApartInfoRequest;
import com.daelim.dorandoranbackend.controller.requestObject.HoRequest;
import com.daelim.dorandoranbackend.entity.ApartInfo;
import com.daelim.dorandoranbackend.service.ApartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Apart", description = "아파트 API")
@RestController
@RequestMapping("/api/apart")
public class ApartController {
    @Autowired
    ApartService apartService;

    @Operation(summary = "아파트 목록 API", description = "아파트 이름으로 아파트 목록 검색하는 API")
    @GetMapping("/{apartName}")
    public List<ApartInfo> getApartList(@PathVariable String apartName) {
        return apartService.getApartList(apartName);
    }

    @Operation(summary = "아파트별 동 목록 API", description = "아파트ID로 동 목록 검색하는 API")
    @ApiResponse(description = "아파트 별 동 목록 리스트")
    @GetMapping("/dong/{apartId}")
    public List<String> getDongList(@PathVariable Integer apartId) {
        return apartService.getDongList(apartId);
    }

    @Operation(summary = "아파트 동 별 호 목록 API", description = "아파트ID와 동으로 호 목록 검색하는 API")
    @ApiResponse(description = "아파트 동 별 호 목록 리스트")
    @PostMapping("/ho")
    public List<String> getHoList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = HoRequest.class)
                    )
            )
            @RequestBody Map<String, Object> pathVars) {
        Integer apartId = Integer.parseInt(pathVars.get("apartId").toString());
        String dong = pathVars.get("dong").toString();

        return apartService.getHoList(apartId, dong);
    }

    @PostMapping("/regist")
    public ApartInfo registApart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ApartInfoRequest.class)
                    )
            )
            @RequestBody Map<String, Object> apartInfoObj) {
        return apartService.registApart(apartInfoObj);
    }

}
