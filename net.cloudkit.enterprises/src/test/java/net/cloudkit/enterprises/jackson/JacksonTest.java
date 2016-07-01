package net.cloudkit.enterprises.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonTest {

    // 直接写入一个对象(所谓序列化)
    public static void writeJsonObject() {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("Coffee", 25, 0, new Date(), "程序员", 2500.0);
        try {
            // Map<String, Object> map = new HashMap<String, Object>();
            // objectMapper.writeValue(System.out, map);
            // List<AccountBean> list = new ArrayList<AccountBean>();
            // objectMapper.writeValue(System.out, list);
            mapper.writeValue(new File("D:/Users/temp/person.json"), person);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 直接将一个json转化为对象（所谓反序列化）
    public static void readJsonObject() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Person[].class
            // Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
            // List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
            Person person = mapper.readValue(new File("D:/Users/temp/person.json"), Person.class);
            System.out.println(person.toString());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        writeJsonObject();
        readJsonObject();
    }
}

//表示序列化时忽略的属性
@JsonIgnoreProperties(value = {"word"})
@JsonRootName("person")
class Person {

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private int age;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("birthday")
    // 反序列化一个固定格式的Date
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerialize.class)
    private Date birthday;

    private String word;

    // 序列化指定格式的double格式
    @JsonSerialize(using = CustomDoubleSerialize.class)
    @JsonProperty("salary")
    private double salary;

    // address
    // email

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, int sex, Date birthday, String word, double salary) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.word = word;
        this.salary = salary;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", word='" + word + '\'' +
                ", salary=" + salary +
                '}';
    }
}

class CustomDateDeserialize extends JsonDeserializer<Date> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        Date date = null;
        try {
            // System.out.println(jp.getText());
            date = sdf.parse(jp.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

class CustomDoubleSerialize extends JsonSerializer<Double> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeString(df.format(value));
    }
}

class CustomDateSerialize extends JsonSerializer<Date> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeString(sdf.format(value));
    }
}
