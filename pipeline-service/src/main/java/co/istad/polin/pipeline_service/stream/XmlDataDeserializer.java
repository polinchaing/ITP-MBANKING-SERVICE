package co.istad.polin.pipeline_service.stream;

public class XmlDataDeserializer extends XmlStringDeserializer<XmlData> {
    public XmlDataDeserializer() {
        super(XmlData.class);
    }
}
