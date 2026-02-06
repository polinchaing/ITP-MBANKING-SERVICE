package co.istad.polin.pipeline_service.stream;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {
    private String code;
    private int qty;
}
