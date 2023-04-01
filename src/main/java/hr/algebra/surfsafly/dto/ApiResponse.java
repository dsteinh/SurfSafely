package hr.algebra.surfsafly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private String error;

    public ApiResponse(final Object data) {
        this.data = data;
    }

    public ApiResponse(final String error) {
        this.error = error;
    }

    public static ApiResponse ok(final Object data) {
        return new ApiResponse(data, "");
    }
    public static ApiResponse error(final Object data, final String error ) {
        return new ApiResponse(data, error);
    }

}

