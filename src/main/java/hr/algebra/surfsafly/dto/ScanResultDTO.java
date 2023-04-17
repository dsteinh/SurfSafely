package hr.algebra.surfsafly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ScanResultDTO {
    private String sha256;
    private String scanDate;
    private int totalEngines;
    private int positives;
    private boolean isMalicious;
    private List<String> detectedBy;
    private List<String> undetectedBy;
}
