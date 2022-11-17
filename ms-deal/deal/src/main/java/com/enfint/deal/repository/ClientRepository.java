package com.enfint.deal.repository;

import com.enfint.deal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long>
{

}
