package se.utnij.linkshortener.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.utnij.linkshortener.util.DateUtil;
import se.utnij.linkshortener.util.UrlUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Url implements Serializable {
    private UUID id;
    private String url;
    private String shortPath;
    private long countOfVisit;
    private String creatorIpAddress;
    private String lastVisitIpAddress;
    private Date createdDate;
    private Date expiredDate;

    public Url(String creatorIpAddress, String url) {
        id = UUID.randomUUID();
        this.url = url;
        shortPath = UrlUtil.generateRandomPath();
        countOfVisit = 0;
        this.creatorIpAddress = creatorIpAddress;
        createdDate = new Date();
        expiredDate = DateUtil.addDaysToDate(7);
    }

    public void increaseCountOfVisit() {
        countOfVisit++;
    }
}