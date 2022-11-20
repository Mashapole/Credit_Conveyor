package com.enfint.deal.repository;

import com.enfint.deal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long>
{
}
