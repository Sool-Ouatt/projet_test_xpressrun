package com.xpress.auth.test.services;

import java.util.List;

import com.xpress.auth.test.dto.BannedIpDTO;

public interface BannedIpService {
	public BannedIpDTO createBannedIp(BannedIpDTO ip);
	public List<BannedIpDTO> getAdressBanneds();
	public Boolean isBanned(String adres);
}
