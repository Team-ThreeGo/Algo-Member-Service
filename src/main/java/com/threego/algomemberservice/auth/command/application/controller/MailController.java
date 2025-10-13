package com.threego.algomemberservice.auth.command.application.controller;

import com.threego.algomemberservice.auth.command.application.service.MailService;
import com.threego.algomemberservice.auth.command.application.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(
        name = "Auth - Mail",
        description = "사용자 이메일 인증 API"
)
@RestController
@RequestMapping("/auth")
public class MailController {

    private final MailService mailService;
    private final RedisService redisService;

    public MailController(MailService mailService, RedisService redisService) {
        this.mailService = mailService;
        this.redisService = redisService;
    }

    @Operation(
            summary = "이메일 인증번호 전송",
            description = "사용자의 구글 이메일로 인증번호를 전송합니다."
    )
    @PostMapping("/email")
    public HashMap<String, Object> mailSend(
            @Parameter(description = "인증번호를 받을 이메일 주소", required = true)
            @RequestParam String mail
    ) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            int number = mailService.sendMail(mail);
            redisService.setDataExpire(mail, String.valueOf(number), 3);
            map.put(mail, number);

            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", e.getMessage());
        }

        return map;
    }

    @Operation(
            summary = "인증번호 확인",
            description = "사용자가 입력한 인증번호와 전송된 번호가 일치하는지 확인합니다."
    )
    @GetMapping("/code")
    public ResponseEntity<Boolean> mailCheck(
            @Parameter(description = "사용자 이메일", required = true)
            @RequestParam String mail,
            @Parameter(description = "사용자가 입력한 인증번호", required = true)
            @RequestParam String code
    ) {
        boolean isMatch = redisService.checkData(mail, code);
        return ResponseEntity.ok(isMatch);
    }
}
