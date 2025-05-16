package id.ac.ui.cs.advprog.rating.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseTest {

    @Test
    void testBuilder() {
        RatingResponse response = RatingResponse.builder()
                .score(5)
                .comment("Excellent!")
                .build();

        ApiResponse<RatingResponse> apiResponse = ApiResponse.<RatingResponse>builder()
                .success(1)
                .message("OK")
                .data(Collections.singletonList(response))
                .build();

        assertEquals(1, apiResponse.getSuccess());
        assertEquals("OK", apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
        assertEquals(1, apiResponse.getData().size());
        assertEquals("Excellent!", apiResponse.getData().get(0).getComment());
    }

    @Test
    void testSettersAndGetters() {
        RatingResponse response = new RatingResponse();
        response.setScore(4);
        response.setComment("Good!");

        ApiResponse<RatingResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(1);
        apiResponse.setMessage("Success");
        apiResponse.setData(List.of(response));

        assertEquals(1, apiResponse.getSuccess());
        assertEquals("Success", apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
        assertEquals(1, apiResponse.getData().size());
        assertEquals("Good!", apiResponse.getData().get(0).getComment());
    }
}
