package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.request.LoginRequest;
import com.daelim.dorandoranbackend.dto.request.UserRequest;
import com.daelim.dorandoranbackend.dto.request.UserUpdateRequest;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.dto.response.UserInfoResponse;
import com.daelim.dorandoranbackend.modules.CustomAuthorization;
import com.daelim.dorandoranbackend.modules.JwtProvider;
import com.daelim.dorandoranbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public Response<String> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.signUp(userObj);
    }

    @Operation(summary = "유저 정보 API - 로그인 필요")
    @CustomAuthorization
    @GetMapping("/info")
    public Response<UserInfoResponse> getUserInfoByUserId(HttpServletRequest request) {
        String userId = jwtProvider.getUserId(request);
        Response<UserInfoResponse> res = userService.getUserInfoByUserId(userId);

        return res;
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public Response<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.login(userObj);
    }

    @Operation(summary = "로그인 여부 확인 API - 로그인 필요")
    @ApiResponse(description = "로그인 상태: token <br>로그아웃 상태: 403에러")
    @CustomAuthorization
    @GetMapping("/isLogin")
    public Response<String> isLogin(HttpServletRequest request) {
        Response<String> res = new Response<>();
        res.setData(jwtProvider.getToken(request));

        return res;
    }

    @Operation(summary = "유저 정보 수정 API - 로그인 필요")
    @CustomAuthorization
    @PutMapping("/modify")
    public Response<UserInfoResponse> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserUpdateRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.updateUser(userObj, jwtProvider.getUserId(request));
    }

    @Operation(summary = "회원 탈퇴 API - 로그인 필요")
    @CustomAuthorization
    @PostMapping("/delete")
    public Response<Boolean> deleteUser(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.deleteUser(jwtProvider.getUserId(request));
    }

    @Operation(summary = "아이디 중복 체크 API")
    @GetMapping("/isIdDup/{userId}")
    public Response<Boolean> isIdDup(@PathVariable String userId) {
        return userService.isIdDup(userId);
    }

}
