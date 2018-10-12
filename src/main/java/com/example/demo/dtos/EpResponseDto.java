package com.example.demo.dtos;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EpResponseDto {
    private long totalCount;
    private List<EpDevice> eps = new ArrayList<>();

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<EpDevice> getEps(){
        return eps;
    }

    public void addEpDevice(EpDevice ep) {
        this.eps.add(ep);
    }

    public void setEps(List<EpDevice> eps) {
        this.eps = eps;
    }
}
