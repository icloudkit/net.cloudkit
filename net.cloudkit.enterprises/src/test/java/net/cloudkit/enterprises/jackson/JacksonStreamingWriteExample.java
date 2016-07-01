package net.cloudkit.enterprises.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;


//JsonFactory f = new JsonFactory();
//JsonGenerator g = f.createJsonGenerator(new File("user.json"));
//
//g.writeStartObject();
//g.writeObjectFieldStart("name");
//g.writeStringField("first", "Joe");
//g.writeStringField("last", "Sixpack");
//g.writeEndObject(); // for field 'name'
//g.writeStringField("gender", Gender.MALE);
//g.writeBooleanField("verified", false);
//g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
//byte[] binaryData = ...;
//g.writeBinary(binaryData);
//g.writeEndObject();
//g.close(); // important: will force flushing of output, close underlying output stream
public class JacksonStreamingWriteExample {

    public static void main(String[] args) throws IOException {
        Employee emp = JacksonObjectMapperExample.createEmployee();

        // stream_emp.txt
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("stream_emp.json"));
        //for pretty printing
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartObject(); // start root object
        jsonGenerator.writeNumberField("id", emp.getId());
        jsonGenerator.writeStringField("name", emp.getName());
        jsonGenerator.writeBooleanField("permanent", emp.isPermanent());

        jsonGenerator.writeObjectFieldStart("address"); //start address object
        jsonGenerator.writeStringField("street", emp.getAddress().getStreet());
        jsonGenerator.writeStringField("city", emp.getAddress().getCity());
        jsonGenerator.writeNumberField("zipcode", emp.getAddress().getZipcode());
        jsonGenerator.writeEndObject(); //end address object

        jsonGenerator.writeArrayFieldStart("phoneNumbers");
        for (long num : emp.getPhoneNumbers())
            jsonGenerator.writeNumber(num);
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("role", emp.getRole());

        jsonGenerator.writeArrayFieldStart("cities"); //start cities array
        for (String city : emp.getCities())
            jsonGenerator.writeString(city);
        jsonGenerator.writeEndArray(); //closing cities array

        jsonGenerator.writeObjectFieldStart("properties");
        Set<String> keySet = emp.getProperties().keySet();
        for (String key : keySet) {
            String value = emp.getProperties().get(key);
            jsonGenerator.writeStringField(key, value);
        }
        jsonGenerator.writeEndObject(); //closing properties
        jsonGenerator.writeEndObject(); //closing root object

        jsonGenerator.flush();
        jsonGenerator.close();
    }

}
