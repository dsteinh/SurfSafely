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

    public ApiResponseDto(final Object data) {
        this.data = data;
    }

    public ApiResponseDto(final String error) {
        this.error = error;
    }

    public static ApiResponseDto ok(final Object data) {
        return new ApiResponseDto(data, "");
    }
    public static ApiResponseDto error(final Object data, final String error ) {
        return new ApiResponseDto(data, error);
    }

}

