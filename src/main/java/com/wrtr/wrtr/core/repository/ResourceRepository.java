package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}
