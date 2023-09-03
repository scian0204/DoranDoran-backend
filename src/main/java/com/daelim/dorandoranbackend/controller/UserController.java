package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.request.LoginRequest;
import com.daelim.dorandoranbackend.dto.request.UserRequest;
import com.daelim.dorandoranbackend.dto.response.Error;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.dto.response.UserInfoResponse;
import com.daelim.dorandoranbackend.modules.JwtProvider;
import com.daelim.dorandoranbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;
    private final String cookieKey = "dorandoran-token";

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public Response<UserInfoResponse> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.signUp(userObj, response);
    }

    @Operation(summary = "유저 정보 API")
    @GetMapping("/info")
    public Response<UserInfoResponse> getUserInfoByUserId(HttpServletRequest request) {
        String token = request.getHeader(cookieKey);
        Response<UserInfoResponse> res = new Response<>();
        if (token == null || token.isEmpty()) {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("토큰이 없읍");
            res.setError(error);
        } else {
            res = userService.getUserInfoByUserId(token);
        }

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

    @Operation(summary = "로그인 여부 확인 API")
    @ApiResponse(description = "로그인 상태: 해당 아이디 세션<br>로그아웃 상태: null")
    @GetMapping("/isLogin")
    public Response<String> isLogin(@Parameter(hidden = true) @CookieValue(value = cookieKey, required = false)Cookie cookie) {
        Response<String> res = new Response<>();
        if (cookie == null) {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("토큰이 발급되지 않음");
            res.setError(error);
        } else {
            String token = cookie.getValue();
            if (jwtProvider.validateToken(token)) {
                res.setData(jwtProvider.getUserId(token));
            } else {
                Error error = new Error();
                error.setErrorId(1);
                error.setMessage("토큰 유효기간 초과됨");
                res.setError(error);
            }
        }
        return res;
    }

    @Operation(summary = "유저 정보 수정 API")
    @PutMapping("/modify")
    public Response<UserInfoResponse> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj,
            @Parameter(hidden = true)
            @CookieValue(value = cookieKey, required = false)Cookie cookie) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.updateUser(userObj, cookie);
    }

    @Operation(summary = "회원 탈퇴 API")
    @ApiResponse(description = "탈퇴 성공: 2<br>탈퇴실패-로그인돼있는 id와 다름: 0<br>탈퇴실패-id는 맞지만 pw틀림: 1")
    @PostMapping("/delete")
    public Response<Object> deleteUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpServletResponse response,
            @Parameter(hidden = true)
            @CookieValue(value = cookieKey, required = false)Cookie cookie) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.deleteUser(userObj, response, cookie);
    }

    @Operation(summary = "아이디 중복 체크 API")
    @GetMapping("/isIdDup/{userId}")
    public Response<Boolean> isIdDup(@PathVariable String userId) {
        return userService.isIdDup(userId);
    }

}
