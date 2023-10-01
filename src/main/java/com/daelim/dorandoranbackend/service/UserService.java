package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.request.UserRequest;
import com.daelim.dorandoranbackend.dto.request.UserUpdateRequest;
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

    public Response<String> signUp(Map<String, Object> userObj) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserRequest userRequest = objMpr.convertValue(userObj, UserRequest.class);
        User user = userRequest.convert();
        Response<String> res = new Response<>();

        if (isIdDup(user.getUserId()).getData()) {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("userId 중복됨");
            res.setError(error);
        } else {
            user.setPassword(encrypt(user.getPassword()));
            userRepository.save(user);

            ApartUser apartUser = new ApartUser();
            apartUser.setUserId(user.getUserId());
            apartUser.setApartIdx(userRequest.getApartIdx());
            apartUserRepository.save(apartUser);

            res.setData(jwtProvider.createToken(user.getUserId()));
        }
        return res;
    }

    public Response<String> login(Map<String, Object> userObj) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String userId = (String) userObj.get("userId");
        String password = (String) userObj.get("password");
        Response<String> res = new Response<>();

        Optional<User> byUserId = userRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            User user = byUserId.get();
            if (user.getPassword().equals(encrypt(password))) {
                String token = jwtProvider.createToken(userId);
                res.setData(token);
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

    public Response<UserInfoResponse> updateUser(Map<String, Object> userObj, String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserUpdateRequest userUpdateRequest = objMpr.convertValue(userObj ,UserUpdateRequest.class);
        User updateUserData = userUpdateRequest.convert();
        Integer apartIdx = Integer.parseInt(userObj.get("apartIdx").toString());

        Response<UserInfoResponse> res = new Response<>();

        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isPresent()) {
            User currentUserData = userOpt.get();

            updateUserData.setUserId(userId);
            updateUserData.setRegDate(currentUserData.getRegDate());
            updateUserData.setPassword(encrypt(updateUserData.getPassword()));

            res.setData(new UserInfoResponse(userRepository.save(updateUserData)));
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 아이디를 가진 유저가 없음");
            res.setError(error);
        }

        return res;
    }

    public Response<Boolean> deleteUser(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Response<Boolean> res = new Response<>();
        Optional<User> userOpt = userRepository.findByUserId(userId);

        if (userOpt.isPresent()) {
            userRepository.deleteById(userId);
            res.setData(true);
        } else {
            Error error = new Error();
            error.setErrorId(0);
            error.setMessage("해당 아이디를 가진 유저가 없음");
            res.setError(error);
            res.setData(false);
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
