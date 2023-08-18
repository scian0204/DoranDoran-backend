package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.request.UserRequest;
import com.daelim.dorandoranbackend.dto.response.Error;
import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.dto.response.UserInfoResponse;
import com.daelim.dorandoranbackend.entity.ApartUser;
import com.daelim.dorandoranbackend.entity.User;
import com.daelim.dorandoranbackend.modules.JwtProvider;
import com.daelim.dorandoranbackend.repository.ApartUserRepository;
import com.daelim.dorandoranbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService{
    ObjectMapper objMpr = new ObjectMapper();
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApartUserRepository apartUserRepository;
    @Autowired
    JwtProvider jwtProvider;

    private String cookieKey = "dorandoran-token";

    public Response<UserInfoResponse> signUp(Map<String, Object> userObj, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserRequest userRequest = objMpr.convertValue(userObj, UserRequest.class);
        User user = new User(userRequest);
        Response<UserInfoResponse> res = new Response<>();

        Optional<User> userS = userRepository.findByUserId(user.getUserId());
        if (userS.isEmpty()) {
            user.setPassword(encrypt(user.getPassword()));
            res.setData(new UserInfoResponse(userRepository.save(user)));
            ApartUser apartUser = new ApartUser();
            apartUser.setUserId(user.getUserId());
            apartUser.setApartIdx(userRequest.getApartIdx());
            apartUserRepository.save(apartUser);
            String token = jwtProvider.createToken(user.getUserId());
            ResponseCookie resCookie = ResponseCookie.from(cookieKey, token)
                    .path("/")
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(false)
                    .build();
            response.addHeader("Set-Cookie", resCookie.toString());
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("userId 중복됨");
            res.setError(error);
        }
        return res;
    }

    public Response<String> login(Map<String, Object> userObj, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String userId = (String) userObj.get("userId");
        String password = (String) userObj.get("password");
        Response<String> res = new Response<>();

        Optional<User> byUserId = userRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            User user = byUserId.get();
            if (user.getPassword().equals(encrypt(password))) {
                String token = jwtProvider.createToken(userId);
                ResponseCookie resCookie = ResponseCookie.from(cookieKey, token)
                        .path("/")
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(false)
                        .build();
                response.addHeader("Set-Cookie", resCookie.toString());
                res.setData(user.getUserId());
                return res;
            } else {
                Error error = new Error();
                error.setErrorId(1);
                error.setMessage("userId는 존재하지만 password가 일치하지 않음");
                res.setError(error);
                return res;
            }
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("userId 존재하지 않음");
            res.setError(error);
            return res;
        }
    }

    public Response<UserInfoResponse> updateUser(Map<String, Object> userObj, Cookie cookie) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Integer apartIdx = Integer.parseInt(userObj.get("apartIdx").toString());
        userObj.remove("apartIdx");
        User user = objMpr.convertValue(userObj, User.class);
        Response<UserInfoResponse> res = new Response<>();
        String token = cookie.getValue();
        if (jwtProvider.validateToken(token)) {
            String userId = jwtProvider.getUserId(token);
            if (userId.equals(user.getUserId())) {
                Optional<User> optUser = userRepository.findByUserId(user.getUserId());
                User user1 = optUser.get();
                user.setRegDate(user1.getRegDate());
                user.setPassword(encrypt(user.getPassword()));
                res.setData(new UserInfoResponse(userRepository.save(user)));
            } else {
                Error error = new Error();
                error.setErrorId(1);
                error.setMessage("로그인 된 userId와 일치하지 않음");
                res.setError(error);
            }
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("로그아웃 상태임");
            res.setError(error);
        }
        return res;
    }

    public Response<Object> deleteUser(Map<String, Object> userObj, HttpServletResponse response, Cookie cookie) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String userId = (String) userObj.get("userId");
        String password = (String) userObj.get("password");
        Response<Object> res = new Response<>();

        Optional<User> byUserId = userRepository.findByUserId(userId);
        String token = cookie.getValue();
        if (!jwtProvider.validateToken(token)) {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("로그아웃 상태임");
            res.setError(error);
        } else if (jwtProvider.getUserId(token).equals(userId)) {
            User user = byUserId.get();
            if (user.getPassword().equals(encrypt(password))) {
                Cookie rmCookie = new Cookie(cookieKey, null);
                rmCookie.setMaxAge(0);
                response.addCookie(rmCookie);
                userRepository.deleteById(userId);
            } else {
                Error error = new Error();
                error.setErrorId(2);
                error.setMessage("userId는 존재하지만 password가 일치하지 않음");
                res.setError(error);
            }
        } else {
            Error error = new Error();
            error.setErrorId(1);
            error.setMessage("로그인 된 userId와 일치하지 않음");
            res.setError(error);
        }
        return res;
    }

    public String encrypt(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pw.getBytes("utf-8"));
        return bytesToHex(md.digest());
    }
    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public Response<Boolean> isIdDup(String userId) {
        boolean result = false;
        Response<Boolean> res = new Response<>();
        Optional<User> userS = userRepository.findByUserId(userId);
        if (userS.isPresent()) {
            result = true;
        }
        res.setData(result);
        return res;
    }

    public Response<UserInfoResponse> getUserInfoByUserId(String userId) {
        Response<UserInfoResponse> res = new Response<>();
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            res.setData(new UserInfoResponse(userOptional.get()));
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 아이디를 가진 유저가 없음");
            res.setError(error);
        }
        return res;
    }
}
