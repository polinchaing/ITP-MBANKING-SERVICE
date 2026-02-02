package co.istad.polin.pipeline_service.client.dto;

public record UserResponse (
        Integer id,
        String name,
        String username,
        String phone,
        String website
){
}
