package com.Profpost.api;

import com.Profpost.dto.UserProfileDTO;
import com.Profpost.dto.UserSearchDTO;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/search")
@PreAuthorize("hasAnyRole('CREATOR', 'READER')")
public class UserSearchController {
    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<UserSearchDTO>> searchUserByName(@PathVariable String userName) {
        List<UserSearchDTO> users = userService.searchUsersByName(userName);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
}
