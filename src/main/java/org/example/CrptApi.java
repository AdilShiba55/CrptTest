package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CrptApi {
    private final int requestLimit;
    private final AtomicInteger requestCount;
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://ismp.crpt.ru";

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.requestLimit = requestLimit;
        this.requestCount = new AtomicInteger(0);
        this.restTemplate = new RestTemplate();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> requestCount.set(0), 1L, 1L, timeUnit);
    }

    public Object create(CrptDocumentCreateRequest requestBody) {
        if(requestCount.incrementAndGet() > requestLimit) {
            throw new RuntimeException("Превышен лимит запросов в определенный интервал");
        }
        String endpoint = baseUrl + "/api/v3/lk/documents/create";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody);
        HttpEntity<Object> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {});
        return result.getBody();
    }

    @Getter
    @Setter
    public static class CrptDocumentCreateRequest {
        @JsonProperty("description")
        private CrptDescription description;
        @JsonProperty("doc_id")
        private String docId;
        @JsonProperty("doc_status")
        private String docStatus;
        @JsonProperty("doc_type")
        private String docType;
        @JsonProperty("importRequest")
        private Boolean importRequest;
        @JsonProperty("owner_inn")
        private String ownerInn;
        @JsonProperty("participant_inn")
        private String participantInn;
        @JsonProperty("producer_inn")
        private String producerInn;
        @JsonProperty("production_date")
        private Date productionDate;
        @JsonProperty("production_type")
        private String productionType;
        @JsonProperty("products")
        private List<CrptProduct> products;
        @JsonProperty("reg_date")
        private Date regDate;
        @JsonProperty("reg_number")
        private String regNumber;
    }

    @Getter
    @Setter
    private static class CrptDescription {
        @JsonProperty("participantInn")
        private String participantInn;
    }

    @Getter
    @Setter
    private static class CrptProduct {
        @JsonProperty("certificate_document")
        private String certificateDocument;
        @JsonProperty("certificate_document_date")
        private Date certificateDocumentDate;
        @JsonProperty("certificate_document_number")
        private String certificateDocumentNumber;
        @JsonProperty("owner_inn")
        private String ownerInn;
        @JsonProperty("producer_inn")
        private String producerInn;
        @JsonProperty("production_date")
        private Date productionDate;
        @JsonProperty("tnved_code")
        private String tnvedCode;
        @JsonProperty("uit_code")
        private String uitCode;
        @JsonProperty("uitu_code")
        private String uituCode;
    }
}
