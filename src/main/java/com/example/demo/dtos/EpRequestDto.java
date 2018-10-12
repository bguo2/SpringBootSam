package com.example.demo.dtos;


import org.springframework.stereotype.Component;

@Component
public class EpRequestDto {
    private long accountId;
    private long supportAccountId;
    private long pageOffset;
    private int pageCount;
    private String searchField;
    private String searchValue;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getSupportAccountId() {
        return supportAccountId;
    }

    public void setSupportAccountId(long supportAccountId) {
        this.supportAccountId = supportAccountId;
    }

    public long getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(long pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
