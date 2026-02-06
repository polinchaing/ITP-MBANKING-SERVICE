package co.istad.polin.pipeline_service.stream;

import ITP.CORE_BANKING.RECORD_XML.Envelope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StreamConfig {

    // Supplier for producing message into kafka topic
    // Function for processing message and send to destination kafka topic
    // Consumer for consuming message from kafka topic

    // Jackson mapper injected by Spring
    private final ObjectMapper objectMapper;

    /*
     ---------------------------------------------------------
     Spring Cloud Stream Consumer (NO @KafkaListener needed)
     ---------------------------------------------------------
     Flow:
     Kafka (Avro) → Schema Registry → Deserializer →
     GenericRecord → toString() → JSON → Jackson → POJO
     ---------------------------------------------------------
     */
//    @Bean
//    public Consumer<Message<Objects>> processOracleXmlData() {
//        return msg -> {
//            try {
//                Object body = msg.getPayload();
//
//                log.info("Payload class: {}", body.getClass().getName());
//
//                // convert Avro GenericRecord → JSON text
//                String jsonText = body.toString();
//
//                // map JSON → Debezium envelope
//                DebeziumEnvelope<DataRecord> event =
//                            objectMapper.readValue(jsonText, new TypeReference<>() {});
//
//                // operation type (c/u/d/r)
//                System.out.println("Operation: " + event.getOp());
//
//                // choose after or before
//                DataRecord data =
//                        event.getAfter() != null
//                                ? event.getAfter()
//                                : event.getBefore();
//
//                if (data == null) return;
//
//                System.out.println("RECID: " + data.getRECID());
//
//                XmlData xml = data.getXMLDATA();
//                if (xml != null) {
//                    System.out.println("Name: " + xml.getName());
//                    System.out.println("Role: " + xml.getRole());
//                }
//
//                System.out.println();
//
//            } catch (Exception ex) {
//                log.error("processOracleXmlData error", ex);
//            }
//        };
//    }

    //    @Bean
    //    public Consumer<Message<Object>> processOracleXmlData(){
    //        return message -> {
    //            try {
    //                // Step 1: Convert Kafka payload -> JSON
    //                String payloadText =
    //                        objectMapper.writeValueAsString(message.getPayload());
    //
    //                // Step 2: Deserialize JSON -> DebeziumEnvelope<Data>
    //                DebeziumEnvelope<DataRecord> event =
    //                        objectMapper.readValue(payloadText, new TypeReference<>() {});
    //
    //                // Step 3: Get operation type (c/u/d/r)
    //                String operation = event.getOp();
    //
    //                // Step 4: Choose AFTER for insert/update, BEFORE for delete
    //                DataRecord record = event.getAfter() != null
    //                        ? event.getAfter()
    //                        : event.getBefore();
    //
    //                if (record == null) return;
    //
    //                // Step 5: Extract parsed XML data
    //                XmlData details = record.getXMLDATA();
    //
    //                // Step 6: Print result
    //                System.out.println("Operation : " + operation);
    //                System.out.println("RECID     : " + record.getRECID());
    //                System.out.println("Name      : " + (details != null ? details.getName() : null));
    //                System.out.println("Role      : " + (details != null ? details.getRole() : null));
    //                System.out.println();
    //
    //            } catch (Exception e) {
    //                log.error("processOracleXmlData error", e);
    //            }
    //        };
    //    }

    @Bean
    public Function<Product, Product> processProductDetail() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());

            // processing
            product.setCode("ISTAD-" + product.getCode());
            return product;
        };
    }

    @Bean
    public Consumer<Product> processProduct() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());
        };
    }

    // A simple processor: Takes a string, makes it uppercase, and sends it on
    @Bean
    public Consumer<String> processMessage() {
        return input -> {
            System.out.println("Processing: " + input);
        };
    }

    @Bean
    public Consumer<GenericRecord> processDebeziumProduct() {
        return record -> {

            GenericRecord after = (GenericRecord) record.get("after");

            // skip deletes or tombstone
            if (after == null) {
                return;
            }

            String code = after.get("code").toString();
            Integer qty = (Integer) after.get("qty");

            String newCode = "ISTAD-" + code;

            System.out.println("====== PRODUCT EVENT ======");
            System.out.println("code : " + newCode);
            System.out.println("qty  : " + qty);
            System.out.println("==========================");
        };
    }

    @Bean
    public Consumer<Message<Envelope>> captureEnvelope(){
        return record->{
            System.out.println("Dbz Envelope : " + record.getPayload()
                    .getAfter());
        };
    }

    @Bean
    public Function<Message<Object>, DataRecord> processOracleXmlData(ObjectMapper objectMapper) {
        return record -> {
            try {
                DebeziumEnvelope<DataRecord> capturedRecord =
                        objectMapper.readValue(record.getPayload().toString(),
                                new TypeReference<>(){});
                return switch (capturedRecord.getOp()) {
                    case "r", "c" -> {
                        System.out.println("Prepare to insert new record");
                        DataRecord after = capturedRecord.getAfter();
                        System.out.println(after.getXMLDATA().getName());
                        yield after;
                    }
                    case "u" -> {
                        System.out.println("Prepare to update existing record");
                        DataRecord after = capturedRecord.getAfter();
                        System.out.println("Updated: " + after.getXMLDATA().getName());
                        yield after;
                    }
                    case "d" -> {
                        System.out.println("Prepare to delete existing record");
                        System.out.println("Delete ID = " + capturedRecord.getBefore().getRECID());
                        yield capturedRecord.getBefore();
                    }
                    default -> throw new IllegalStateException("Invalid Operation..!");
                };
            } catch (JsonProcessingException e) {
                System.out.println("Error deserialized");
                throw new RuntimeException("Error deserialized");
            }
        };
    }


}
