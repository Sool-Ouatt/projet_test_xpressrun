package com.xpress.auth.test.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpress.auth.test.dto.BannedIpDTO;
import com.xpress.auth.test.entities.BannedIpEntity;
import com.xpress.auth.test.repositories.BannedIpRepository;
import com.xpress.auth.test.services.BannedIpService;

@Service
public class BannedIpServiceImpl implements BannedIpService{
	
	@Autowired
	BannedIpRepository repositoryBannedIp;
	
	public BannedIpDTO createBannedIp(BannedIpDTO ip) {
		if(!isBanned(ip.getAdresseIp())) {
			ModelMapper modelMapper = new ModelMapper();
			BannedIpEntity adressAd = modelMapper.map(ip, BannedIpEntity.class);
			BannedIpEntity adressRet = repositoryBannedIp.save(adressAd);
			return modelMapper.map(adressRet, BannedIpDTO.class);
		}
		return ip;
	}
	
	public List<BannedIpDTO> getAdressBanneds(){
		
		List<BannedIpDTO> adresListRet = new ArrayList<BannedIpDTO>();
		List<BannedIpEntity> adressList = repositoryBannedIp.findAll();
		for(BannedIpEntity adresIp : adressList) {
			ModelMapper modelMapper = new ModelMapper();
			adresListRet.add(modelMapper.map(adresIp, BannedIpDTO.class));
		}
		return adresListRet;
	}
	
	public Boolean isBanned(String adres) {
		BannedIpEntity adress = repositoryBannedIp.findByAdres(adres);
			return adress != null;
	}
}
