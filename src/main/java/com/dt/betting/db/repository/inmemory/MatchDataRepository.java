package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Match;

@Component
public class MatchDataRepository extends InMemoryDataRepository<Match> {
}
