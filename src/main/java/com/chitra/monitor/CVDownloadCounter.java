package com.chitra.monitor;

import com.chitra.repository.DocumentRepository;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CVDownloadCounter implements InfoContributor {
    private DocumentRepository documentRepo;

    private CVDownloadCounter(DocumentRepository documentRepo){
        this.documentRepo = documentRepo;
    }

    @Override
    public void contribute(Builder builder){
        long cvCount = documentRepo.findByTitle("chitraLimbuCV.pdf").getCount();
        Map<String, Object> cvMap = new HashMap<>();
        cvMap.put("count", cvCount);
        builder.withDetail("cv-stats", cvMap);
    }
}
