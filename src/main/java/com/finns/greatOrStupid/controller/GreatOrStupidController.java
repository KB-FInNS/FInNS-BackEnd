package com.finns.greatOrStupid.controller;

import com.finns.greatOrStupid.dto.UpdateGreatOrStupidRequestDTO;
import com.finns.greatOrStupid.service.GreatOrStupidService;
import com.finns.post.dto.GreatAndStupidCount;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "GreatOrStupidController", tags = "좋아요 싫어요")
@PropertySource({"classpath:/application.properties"})
@CrossOrigin(origins = "http://localhost:5173") // 클라이언트의 도메인을 허용
@RestController
@RequiredArgsConstructor
public class GreatOrStupidController {

    private final GreatOrStupidService greatOrStupidService;

    @PutMapping("/greatOrStupid")
    public ResponseEntity<GreatAndStupidCount> toggleGreat(@RequestBody UpdateGreatOrStupidRequestDTO updateGreatOrStupidRequestDTO) {
        GreatAndStupidCount greatAndStupidCount = greatOrStupidService.toggleGreat(updateGreatOrStupidRequestDTO);
        return ResponseEntity.ok(greatAndStupidCount);
    }

    @GetMapping("/greatOrStupid/{userNo}/{postNo}/isGreat")
    public ResponseEntity<Boolean> isGreat(@PathVariable Long userNo, @PathVariable Long postNo) {
        Boolean result = greatOrStupidService.isGreat(userNo, postNo);
        return ResponseEntity.ok(result);
    }
}