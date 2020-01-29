package com.skinnycodebase.EchoUnit229.service.repos;

import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EchoGamePublicRepository extends CrudRepository<EchoGamePublic, Long> {


}
