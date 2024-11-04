package ch.zero.project295.util;

/**
 * A generic API response wrapper used to standardize responses for the API.
 * <p>
 * This class provides a uniform way to include a success status, a message, and
 * the actual data in the API response.
 * </p>
 *
 * @param <T> The type of the response data
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
