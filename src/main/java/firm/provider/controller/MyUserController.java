package firm.provider.controller;

import firm.provider.dto.UserInfo;
import firm.provider.model.MyUser;
import firm.provider.service.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class MyUserController {

    private MyUserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MyUser user) {
        userService.addUser(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyUser> getUserInfo() {

        String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser user = userService.getUser(mail).orElseThrow(() -> new UsernameNotFoundException("Пользовател не найден"));

        return ResponseEntity.ok(user);
    }
}
