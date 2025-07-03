    package org.example.event.controller;

    import lombok.RequiredArgsConstructor;
    import org.apache.logging.log4j.util.PerformanceSensitive;
    import org.example.event.dtos.req.LoginDTO;
    import org.example.event.dtos.req.RegisterDTO;
    import org.example.event.dtos.req.VerificationDTO;
    import org.example.event.dtos.res.LoginResponseDTO;
    import org.example.event.service.interfaces.AuthService;
    import org.example.event.utils.Util;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping(Util.AUTH_PATH)
    @RequiredArgsConstructor
    public class AuthController {

        private final AuthService authService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
            return ResponseEntity.ok(authService.login(loginDTO));
        }

        @PostMapping("/register")
        public HttpEntity<?> register(@RequestBody RegisterDTO registerDTO) {
            authService.register(registerDTO);
            return ResponseEntity.ok().build();
        }

        @PostMapping("/verify")
        public ResponseEntity<String> verify(@RequestBody VerificationDTO dto) {
            boolean success = authService.verifyCode(dto.getUserId(), dto.getCode());
            return success
                    ? ResponseEntity.ok("Email tasdiqlandi!")
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kod noto‘g‘ri yoki muddati tugagan.");
        }
    }
