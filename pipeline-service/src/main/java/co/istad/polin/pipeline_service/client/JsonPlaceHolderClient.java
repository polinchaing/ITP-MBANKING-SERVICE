package co.istad.polin.pipeline_service.client;

import co.istad.polin.pipeline_service.client.dto.UserResponse;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface JsonPlaceHolderClient {

    //https://jsonplaceholder.typicode.com/users
    @GetExchange("/users")
    List<UserResponse> getAllUsers();

}
