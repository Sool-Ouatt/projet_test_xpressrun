package com.xpress.auth.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xpress.auth.test.entities.BannedIpEntity;

public interface BannedIpRepository extends JpaRepository<BannedIpEntity, Long>{
	@Query("SELECT ad FROM adressebanni ad WHERE ad.adresseIp = ?1")
	BannedIpEntity findByAdres(String adresseIp);

}
