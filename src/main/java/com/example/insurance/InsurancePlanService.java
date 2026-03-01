package com.example.insurance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InsurancePlanService {

    private static final Logger logger = LoggerFactory.getLogger(InsurancePlanService.class);
    private static final String BASE_URL = "https://public.fhir.flex.optum.com/R4/Insurance";

    private final RestTemplate restTemplate;

    public InsurancePlanService() {
        this.restTemplate = new RestTemplate();
    }

    public void consumeAllPlans() {
        String url = BASE_URL;
        AtomicInteger pageCounter = new AtomicInteger(0);

        while (url != null) {
            logger.info("Fetching page {}: {}", pageCounter.incrementAndGet(), url);

            ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("Request failed with status {}", response.getStatusCode());
                break;
            }

            String body = response.getBody();
            logger.info("Received {} bytes", body != null ? body.length() : 0);

            // TODO: Process body JSON as needed (e.g., parse bundle, save results)

            // Determine next page link from bundle
            url = extractNextLink(body);
        }

        logger.info("Completed fetching all pages");
    }

    private String extractNextLink(String bundleJson) {
        if (bundleJson == null) return null;
        // simple parsing for the "link" array with relation "next" using a naive approach
        int idx = bundleJson.indexOf("\"relation\":\"next\"");
        if (idx < 0) return null;
        int urlStart = bundleJson.indexOf("\"url\":\"", idx);
        if (urlStart < 0) return null;
        urlStart += 8;
        int urlEnd = bundleJson.indexOf("\"", urlStart);
        if (urlEnd < 0) return null;
        return bundleJson.substring(urlStart, urlEnd);
    }
}
