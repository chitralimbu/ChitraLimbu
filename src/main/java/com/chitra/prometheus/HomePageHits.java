package com.chitra.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HomePageHits {

    private final Counter homepageCounter;

    public HomePageHits(MeterRegistry meterRegistry){
        homepageCounter = Counter.builder("homepage")
                                .description("Number of homepage hits")
                                .register(meterRegistry);
    }

    public void counterIncrement(){
        homepageCounter.increment();
    }
}
