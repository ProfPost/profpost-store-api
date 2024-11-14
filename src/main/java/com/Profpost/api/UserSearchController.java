package com.Profpost.api;

import com.Profpost.dto.UserSearchDTO;
import com.Profpost.service.UserService;
import com.Profpost.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/search")
@PreAuthorize("hasAnyRole('CREATOR', 'READER')")
public class UserSearchController {
    private final UserService userService;
    private final CreatorService creatorService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<UserSearchDTO>> searchUserByName(@PathVariable String userName) {
        List<UserSearchDTO> users = userService.searchUsersByName(userName);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/creator-id")
    public ResponseEntity<Integer> getCreatorId(@RequestParam Integer userId) {
        Integer creatorId = creatorService.findCreatorIdByUserId(userId);
        return ResponseEntity.ok(creatorId);
    }
}
