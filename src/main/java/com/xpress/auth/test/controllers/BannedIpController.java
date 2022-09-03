package com.xpress.auth.test.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpress.auth.test.dto.BannedIpDTO;
import com.xpress.auth.test.models.request.BannedIpRequest;
import com.xpress.auth.test.models.responses.BannedIpResponse;
import com.xpress.auth.test.services.BannedIpService;

@RestController
@RequestMapping("adresses")
public class BannedIpController {

	@Autowired
	BannedIpService serviceBannedIp;
	
	@PostMapping
	public BannedIpResponse createBannedIp(@RequestBody BannedIpRequest adress) throws Exception{
		
		ModelMapper modelMapper = new ModelMapper();
		BannedIpDTO adresAd = modelMapper.map(adress, BannedIpDTO.class);
		BannedIpDTO adresAdRet = serviceBannedIp.createBannedIp(adresAd);
		BannedIpResponse adresRet = modelMapper.map(adresAdRet, BannedIpResponse.class);
		return adresRet;
	}
	
	@GetMapping
	public List<BannedIpResponse> getAdresBanneds(){
		List<BannedIpResponse> adresBannedListRet = new ArrayList<BannedIpResponse>();
		List<BannedIpDTO> adresBanneds = serviceBannedIp.getAdressBanneds();
		for(BannedIpDTO adres : adresBanneds) {
			ModelMapper modelMapper = new ModelMapper();
			BannedIpResponse adresAd = modelMapper.map(adres, BannedIpResponse.class);
			adresBannedListRet.add(adresAd);
		}
		return adresBannedListRet;
	}
}
