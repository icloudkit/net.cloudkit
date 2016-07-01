package net.cloudkit.enterprises.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


//{
//    "id": 123,
//    "name": "Pankaj",
//    "permanent": true,
//    "address": {
//        "street": "Albany Dr",
//        "city": "San Jose",
//        "zipcode": 95129
//    },
//    "phoneNumbers": [
//        123456,
//        987654
//    ],
//    "role": "Manager",
//    "cities": [
//        "Los Angeles",
//        "New York"
//    ],
//    "properties": {
//        "age": "29 years",
//        "salary": "1000 USD"
//    }
//}
public class JacksonObjectMapperExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        // read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));

        // create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // convert json string to object
        Employee emp = objectMapper.readValue(jsonData, Employee.class);

        System.out.println("Employee Object\n" + emp);

        // convert Object to json string
        Employee emp1 = createEmployee();
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, emp1);
        System.out.println("Employee JSON is\n" + stringEmp);


        // TODO Converting json to Map
        byte[] mapData = Files.readAllBytes(Paths.get("data.txt"));
        Map<String, String> myMap = new HashMap<String, String>();

        // ObjectMapper objectMapper = new ObjectMapper();
        myMap = objectMapper.readValue(mapData, HashMap.class);
        System.out.println("Map is: " + myMap);

        // another way
        myMap = objectMapper.readValue(mapData, new TypeReference<HashMap<String, String>>() {
        });
        System.out.println("Map using TypeReference: " + myMap);


        // TODO Read Specific JSON Key
        // read json file data to String
        // byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));

        // create ObjectMapper instance
        // ObjectMapper objectMapper = new ObjectMapper();

        // read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode idNode = rootNode.path("id");
        System.out.println("id = " + idNode.asInt());

        JsonNode phoneNosNode = rootNode.path("phoneNumbers");
        Iterator<JsonNode> elements = phoneNosNode.elements();
        while (elements.hasNext()) {
            JsonNode phone = elements.next();
            System.out.println("Phone No = " + phone.asLong());
        }

        // TODO Edit JSON Document
        // byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));

        // ObjectMapper objectMapper = new ObjectMapper();

        //create JsonNode
        JsonNode rootNode2 = objectMapper.readTree(jsonData);

        //update JSON data
        ((ObjectNode) rootNode2).put("id", 500);
        //add new key value
        ((ObjectNode) rootNode2).put("test", "test value");
        //remove existing key
        ((ObjectNode) rootNode2).remove("role");
        ((ObjectNode) rootNode2).remove("properties");
        objectMapper.writeValue(new File("updated_emp.txt"), rootNode2);

    }

    public static Employee createEmployee() {

        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("David");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[]{123456, 987654});
        emp.setRole("Manager");

        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);

        List<String> cities = new ArrayList<>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);

        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);

        return emp;
    }

}

class Address {

    private String street;
    private String city;
    private int zipcode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return getStreet() + ", " + getCity() + ", " + getZipcode();
    }
}

class Employee {

    private int id;
    private String name;
    private boolean permanent;
    private Address address;
    private long[] phoneNumbers;
    private String role;
    private List<String> cities;
    private Map<String, String> properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(long[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** Employee Details *****\n");
        sb.append("ID=" + getId() + "\n");
        sb.append("Name=" + getName() + "\n");
        sb.append("Permanent=" + isPermanent() + "\n");
        sb.append("Role=" + getRole() + "\n");
        sb.append("Phone Numbers=" + Arrays.toString(getPhoneNumbers()) + "\n");
        sb.append("Address=" + getAddress() + "\n");
        sb.append("Cities=" + Arrays.toString(getCities().toArray()) + "\n");
        sb.append("Properties=" + getProperties() + "\n");
        sb.append("*****************************");

        return sb.toString();
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
