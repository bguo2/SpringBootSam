package com.example.demo.managers;


import com.example.demo.Repositories.EpRepository;
import com.example.demo.dtos.EpRequestDto;
import com.example.demo.dtos.EpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpDaoService {

    @Autowired
    private EpRepository epRepo;

    public EpResponseDto getEpDevices(EpRequestDto epReqDto) throws Exception{
        return epRepo.getEpDevices(epReqDto);
    }

    public EpResponseDto getEpDevices1(EpRequestDto epReqDto){
        return epRepo.getEpDevices1(epReqDto);
    }

    public EpResponseDto getEpDevices2(EpRequestDto epReqDto){
        return epRepo.getEpDevices2(epReqDto);
    }
}

