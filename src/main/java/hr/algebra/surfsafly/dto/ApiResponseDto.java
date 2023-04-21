package hr.algebra.surfsafly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiResponseDto {
    private Object data;
    private String error;

    public static ApiResponseDto ok(final Object data) {
        return new ApiResponseDto(data, "");
    }
    public static ApiResponseDto ok(final Object data, String message) {
        return new ApiResponseDto(data, message);
    }
    public static ApiResponseDto error(final Object data, final String error ) {
        return new ApiResponseDto(data, error);
    }

}

