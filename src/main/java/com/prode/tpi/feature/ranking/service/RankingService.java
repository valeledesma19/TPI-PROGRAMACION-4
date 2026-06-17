package com.prode.tpi.feature.ranking.service;

import com.prode.tpi.feature.ranking.dto.RankingResponseDto;

import java.util.List;

public interface RankingService {

    List<RankingResponseDto> rankingGlobal();
}