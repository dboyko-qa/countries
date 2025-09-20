package com.dboiko.countries.controller.error;

import java.util.List;
import java.util.Map;

public class ApiError {
    private final String apiVersion;
    private final Error error;

    private record Error(String code,
                         String message,
                         List<ErrorItem> errors) {}

    public record ErrorItem(String domain,
                            String reason,
                            String message) {
    }

    public ApiError(String apiVersion, Error error) {
        this.apiVersion = apiVersion;
        this.error = error;
    }

    public ApiError(String apiVersion,
                    String code,
                    String message,
                    String domain,
                    String reason) {
        this.apiVersion = apiVersion;
        this.error = new Error (
                code,
                message,
                List.of(new ErrorItem(domain, reason, message)));

    }

    public static ApiError fromAttributeMap(Map<String, Object> attributesMap, String apiVersion) {
        return new ApiError(apiVersion,
                ((Integer) attributesMap.get("status")).toString(),
                (String) attributesMap.getOrDefault("error", ""),
                (String) attributesMap.getOrDefault("path", ""),
                (String) attributesMap.getOrDefault("error", ""));
    }

    public Map<String, Object> toAttributesMap() {
        return Map.of(
                "apiVersion", apiVersion,
                "error", error
        );
    }
    public String getApiVersion() {
        return apiVersion;
    }

    public Error getError() {
        return error;
    }
}
