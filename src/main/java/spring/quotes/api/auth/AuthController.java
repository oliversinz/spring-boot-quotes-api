package spring.quotes.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.quotes.api.user.AppUser;
import spring.quotes.api.user.AppUserRepository;
import spring.quotes.api.user.AppUserRole;

@RestController
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        if (appUserRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = new AppUser();
        appUser.setFirstName(signUpDto.getFirstName());
        appUser.setLastName(signUpDto.getLastName());
        appUser.setEmail(signUpDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        appUser.setUserRole(AppUserRole.USER);

        appUserRepository.save(appUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

}
