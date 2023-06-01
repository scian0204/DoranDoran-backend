package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.responseObject.Response;
import com.daelim.dorandoranbackend.entity.WarningMessage;
import com.daelim.dorandoranbackend.repository.WarningMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningMessageService {
    @Autowired
    WarningMessageRepository warningMessageRepository;

    public Response<List<WarningMessage>> getMessage(String userId) {
        List<WarningMessage> warningMessages = warningMessageRepository.findAllByUserId(userId);
        Response<List<WarningMessage>> res = new Response<>();
        res.setData(warningMessages);

        return res;
    }
}
