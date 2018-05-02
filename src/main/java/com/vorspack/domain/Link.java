package com.vorspack.domain;

public class Link {
    private String linkInfo;
    @Override
    public boolean equals(Object obj) {
        Link link = (Link) obj;
        return link.linkInfo.equals(this.linkInfo);
    }

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }
}
