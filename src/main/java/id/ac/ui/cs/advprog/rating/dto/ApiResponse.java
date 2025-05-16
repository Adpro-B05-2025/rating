package id.ac.ui.cs.advprog.rating.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int success;
    private String message;
    private List<T> data;
}
