package com.skinnycodebase.EchoUnit229.service.repos;

import com.skinnycodebase.EchoUnit229.models.ServerConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerConfigRepository extends CrudRepository<ServerConfig, String> {
}
