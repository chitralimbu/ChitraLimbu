package com.chitra.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CVDownloadHits {
    private final Counter cvDownloadCounter;

    public CVDownloadHits(MeterRegistry meterRegistry){
        cvDownloadCounter = Counter.builder("cv_downloads")
                                    .description("Number of CV Downloads")
                                    .register(meterRegistry);
    }

    public void counterIncrement(){
        cvDownloadCounter.increment();
    }
}
