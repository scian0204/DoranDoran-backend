package com.daelim.dorandoranbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "NoiseSchedule", description = "소음 예고 API")
@RestController
@RequestMapping("/api/NoiseSchedule")
public class NoiseScheduleController {
}
