package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        CrptApi crptApi = new CrptApi(TimeUnit.MILLISECONDS, 1);
        ObjectMapper objectMapper = new ObjectMapper();
        CrptApi.CrptDocumentCreateRequest requestBody = getRequestBody(objectMapper);
        Object result = crptApi.create(requestBody);
    }

    private static CrptApi.CrptDocumentCreateRequest getRequestBody(ObjectMapper objectMapper) throws JsonProcessingException {
        String json = "{\n" +
                "  \"description\": {\n" +
                "    \"participantInn\": \"string\"\n" +
                "  },\n" +
                "  \"doc_id\": \"string\",\n" +
                "  \"doc_status\": \"string\",\n" +
                "  \"doc_type\": \"LP_INTRODUCE_GOODS\",\n" +
                "  \"importRequest\": true,\n" +
                "  \"owner_inn\": \"string\",\n" +
                "  \"participant_inn\": \"string\",\n" +
                "  \"producer_inn\": \"string\",\n" +
                "  \"production_date\": \"2020-01-23\",\n" +
                "  \"production_type\": \"string\",\n" +
                "  \"products\": [\n" +
                "    {\n" +
                "      \"certificate_document\": \"string\",\n" +
                "      \"certificate_document_date\": \"2020-01-23\",\n" +
                "      \"certificate_document_number\": \"string\",\n" +
                "      \"owner_inn\": \"string\",\n" +
                "      \"producer_inn\": \"string\",\n" +
                "      \"production_date\": \"2020-01-23\",\n" +
                "      \"tnved_code\": \"string\",\n" +
                "      \"uit_code\": \"string\",\n" +
                "      \"uitu_code\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"reg_date\": \"2020-01-23\",\n" +
                "  \"reg_number\": \"string\"\n" +
                "}";

        return objectMapper.readValue(json, CrptApi.CrptDocumentCreateRequest.class);
    }
}
