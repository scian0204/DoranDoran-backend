package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.dto.requestObject.LoginRequest;
import com.daelim.dorandoranbackend.dto.requestObject.UserRequest;
import com.daelim.dorandoranbackend.dto.responseObject.Response;
import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public Response<User> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.signUp(userObj, session);
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public Response<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.login(userObj, session);
    }

    @Operation(summary = "로그아웃 API")
    @GetMapping("/logout")
    public Response<String> logout(HttpSession session) {
        session.removeAttribute("userId");
        Response<String> res = new Response<>();
        return res;
    }

    @Operation(summary = "로그인 여부 확인 API")
    @ApiResponse(description = "로그인 상태: 해당 아이디 세션<br>로그아웃 상태: null")
    @GetMapping("/isLogin")
    public Response<String> isLogin(HttpSession session) {
        Response<String> res = new Response<>();
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            res.setData(userId.toString());
        }
        return res;
    }

    @Operation(summary = "유저 정보 수정 API")
    @PutMapping("/modify")
    public Response<User> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @RequestBody Map<String, Object> userObj, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.updateUser(userObj, session);
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
            @RequestBody Map<String, Object> userObj, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.deleteUser(userObj, session);
    }

    @Operation(summary = "아이디 중복 체크 API")
    @GetMapping("/isIdDup/{userId}")
    public Response<Boolean> isIdDup(@PathVariable String userId) {
        return userService.isIdDup(userId);
    }
}
