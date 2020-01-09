package com.skinnycodebase.EchoUnit229.models;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EchoGameRepository extends CrudRepository<EchoGame, Long> {


}
