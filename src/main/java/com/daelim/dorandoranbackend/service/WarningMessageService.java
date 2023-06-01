package com.daelim.dorandoranbackend.service;

import com.daelim.dorandoranbackend.dto.response.Response;
import com.daelim.dorandoranbackend.entity.WarningMessage;
import com.daelim.dorandoranbackend.repository.WarningMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WarningMessageService {
    @Autowired
    WarningMessageRepository warningMessageRepository;

    public Response<Page<WarningMessage>> getMessage(Pageable pageable, String userId) {
        Page<WarningMessage> warningMessages = warningMessageRepository.findAllByUserId(pageable, userId);
        Response<Page<WarningMessage>> res = new Response<>();
        res.setData(warningMessages);

        return res;
    }
}
