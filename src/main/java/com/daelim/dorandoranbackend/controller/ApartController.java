package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.request.ApartAllRequest;
import com.daelim.dorandoranbackend.dto.request.ApartInfoRequest;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.dto.response.UserInfoResponse;
import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.entity.ApartInfo;
import com.daelim.dorandoranbackend.modules.CustomAuthorization;
import com.daelim.dorandoranbackend.modules.JwtProvider;
import com.daelim.dorandoranbackend.service.ApartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Apart", description = "아파트 API")
@RestController
@RequestMapping("/api/apart")
@RequiredArgsConstructor
public class ApartController {
    private final ApartService apartService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "아파트 목록 API", description = "아파트 이름으로 아파트 목록 검색하는 API")
    @GetMapping("/{apartName}")
    public Response<List<ApartInfo>> getApartList(@PathVariable String apartName) {
        return apartService.getApartList(apartName);
    }

    @Operation(summary = "아파트별 동 목록 API", description = "아파트ID로 동 목록 검색하는 API")
    @ApiResponse(description = "아파트 별 동 목록 리스트")
    @GetMapping("/dong/{apartId}")
    public Response<List<String>> getDongList(@PathVariable Integer apartId) {
        return apartService.getDongList(apartId);
    }

    @Operation(summary = "아파트 동 별 호 목록 API", description = "아파트ID와 동으로 호 목록 검색하는 API")
    @ApiResponse(description = "아파트 동 별 호 목록 리스트")
    @GetMapping("/ho/{apartId}/{dong}")
    public Response<List<Apart>> getHoList(@PathVariable Integer apartId, @PathVariable String dong) {
        return apartService.getHoList(apartId, dong);
    }

    @Operation(summary = "아파트 동 호 API", description = "apartId, dong, ho로 apartIdx 얻는 API")
    @GetMapping("/{apartId}/{dong}/{ho}")
    public Response<Apart> getApartByApartIdAndDongAndHo(@PathVariable Integer apartId, @PathVariable String dong, @PathVariable String ho) {
        return apartService.getApartByApartIdAndDongAndHo(apartId, dong, ho);
    }

    @Operation(summary = "호별 유저 목록 API", description = "apart의 apartIdx를 넣을 것")
    @GetMapping("/getUser/{apartIdx}")
    public Response<List<UserInfoResponse>> getUserByHo(@PathVariable Integer apartIdx) {
        return apartService.getUserByHo(apartIdx);
    }

    @Operation(summary = "유저별 호 API - 로그인 필요")
    @CustomAuthorization
    @GetMapping("/getInfo")
    public Response<Apart> getInfoByUserId(HttpServletRequest request) {
        String userId = jwtProvider.getUserId(request);
        return apartService.getInfoByUserId(userId);
    }

    @Operation(summary = "아파트 등록 API")
    @PostMapping("/regist")
    public Response<ApartInfo> registApart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ApartInfoRequest.class)
                    )
            )
            @RequestBody Map<String, Object> apartInfoObj) {
        return apartService.registApart(apartInfoObj);
    }

    @Operation(summary = "아파트별 동, 호 일괄등록 API", description = "아파트 등록 API에서 아파트 등록을 먼저 하고 해당 apartId를 넣을 것")
    @ApiResponse(description = "등록한 해당 아파트 동, 호 리스트")
    @PostMapping("/registAll")
    public Response<List<Apart>> registAll(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ApartAllRequest.class)
                    ),
                    description = "apartId: 등록할 아파트 id<br>dongs: 동 리스트<br>numOfFloor: 층수<br>numOfHo: 층별 호 수"
            )
            @RequestBody Map<String, Object> apartAll) {
            return apartService.registAll(apartAll);
    }

}
