    package com.example.webstrtest.controller;

    import com.example.webstrtest.JwtCore;
    import com.example.webstrtest.dto.SigninRequest;
    import com.example.webstrtest.dto.SignupRequest;
    import com.example.webstrtest.entity.ToDoUser;
    import com.example.webstrtest.repository.ToDoUserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/auth")
    public class SecurityController {

        private ToDoUserRepository toDoUserRepository;
        private PasswordEncoder passwordEncoder;
        private AuthenticationManager authenticationManager;
        private JwtCore jwtCore;

        @Autowired
        public void setUserRepository(ToDoUserRepository toDoUserRepository) {
            this.toDoUserRepository = toDoUserRepository;
        }

        @Autowired
        public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        @Autowired
        // здесь нужен 1 бин, но дается 2 (authenticationManagerBuilder из AuthenticationConfiguration и configureAuthenticationManagerBuilder из SecurityConfigurator)
        public void setAuthenticationManager(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        @Autowired
        public void setJwtCore(JwtCore jwtCore) {
            this.jwtCore = jwtCore;
        }

        @PostMapping("/signup")
        ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
            if(toDoUserRepository.existsByUsername(signupRequest.getUsername())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
            }
            if(toDoUserRepository.existsByEmail(signupRequest.getUsername())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different email");
            }
            ToDoUser toDoUser = new ToDoUser();
            toDoUser.setEmail(signupRequest.getEmail());
            toDoUser.setUsername(signupRequest.getUsername());
            String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
            toDoUser.setPassword(hashedPassword);
            toDoUserRepository.save(toDoUser);
            return ResponseEntity.ok(hashedPassword);
        }

        @PostMapping("/signin")
        ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
            Authentication authentication;
            try{
                authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
            } catch (BadCredentialsException e){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtCore.generateToken(authentication);
            return ResponseEntity.ok(jwt);
        }
    }
