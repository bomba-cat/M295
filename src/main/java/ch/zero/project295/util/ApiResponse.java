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

    /**
     * Constructs a new ApiResponse with the specified success status, message, and data.
     *
     * @param success the success status of the response (true if successful, false otherwise)
     * @param message the message providing additional information about the response
     * @param data    the data being returned, of type T
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Gets the success status of the response.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the response.
     *
     * @param success true if the operation was successful, false otherwise
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the message providing additional information about the response.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message providing additional information about the response.
     *
     * @param message the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the data being returned by the response.
     *
     * @return the response data of type T
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data to be returned by the response.
     *
     * @param data the response data of type T
     */
    public void setData(T data) {
        this.data = data;
    }
}
