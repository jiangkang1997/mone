package run.mone.mcp.mimeter.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import run.mone.hive.mcp.spec.McpSchema;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
@Slf4j
public class MiMeterFunction implements Function<Map<String, Object>, McpSchema.CallToolResult> {

    private final String name = "MiMeter";

    private final String desc = "Mimeter's operating tool can be used to query pressure testing results";

    private final String toolScheme = """
            {
                "type": "object",
                "properties": {
                    "operation": {
                        "type": "string",
                        "description": "Operation type for MiMeter:\\n1. getResult: Get pressure testing result'"
                    },
                    "reportId": {
                        "type": "string",
                        "description": "scene id of MiMeter. Example: '1094W9P5L'"
                    }
                },
                "required": ["operation","reportId"]
            }
            """;


    @Override
    public McpSchema.CallToolResult apply(Map<String, Object> args) {
        String operation = (String) args.get("operation");
        log.info("Executing MiMeter operation: {}", operation);

        try {
            String result = switch (operation) {
                case "getResult" -> {
                    String reportId = (String) args.get("reportId");
                    if (reportId == null) {
                        throw new IllegalArgumentException("reportId is required for getResult operation");
                    }
                    yield getReport(reportId);
                }
                default -> throw new IllegalArgumentException("Unknown operation: " + operation);
            };
            return new McpSchema.CallToolResult(List.of(new McpSchema.TextContent(result)), false);
        } catch (Exception e) {
            log.error("Failed to execute MiMeter operation: {}", e.getMessage());
            return new McpSchema.CallToolResult(List.of(new McpSchema.TextContent("Error: " + e.getMessage())), true);
        }
    }


    private String getReport(String reportId) {
        String url = "https://one.mi.com/api/mimeter/api/bench/report/info/details?reportId=%s";
        url = String.format(url, reportId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
            log.error("getReport error:{}", response.body().string());
            return "";
        } catch (IOException e) {
            log.error("getReport exception", e);
            return "";
        }
    }
}
