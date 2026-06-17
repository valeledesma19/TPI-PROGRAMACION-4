package com.prode.tpi.feature.ranking.controller;

import com.prode.tpi.feature.ranking.dto.RankingResponseDto;
import com.prode.tpi.feature.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingGetController {

    private final RankingService rankingService;

    @GetMapping("/global")
    public ResponseEntity<List<RankingResponseDto>> rankingGlobal() {
        return ResponseEntity.ok(rankingService.rankingGlobal());
    }
}