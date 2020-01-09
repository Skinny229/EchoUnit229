package com.skinnycodebase.EchoUnit229.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EchoGameRepository extends CrudRepository<EchoGame, Long> {
}
