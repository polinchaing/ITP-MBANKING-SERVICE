package co.istad.polin.pipeline_service.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class DataRecord {

    @JsonProperty("RECID")
    private String RECID;

    @JsonProperty("XMLDATA")
    @JsonDeserialize(using = XmlDataDeserializer.class)
    private XmlData XMLDATA;
}
