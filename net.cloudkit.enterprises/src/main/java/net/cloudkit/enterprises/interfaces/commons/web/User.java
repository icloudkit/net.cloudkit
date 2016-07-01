package net.cloudkit.enterprises.interfaces.commons.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

// @XStreamAlias("userDetails")
@XmlRootElement(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1476029579160603381L;

    @JsonProperty
    // @XStreamAsAttribute
    // @XStreamAlias("userName")
    // @XmlElement
    // @XmlAttribute(name = "username")
    // @XmlTransient

    // 分组验证 groups = {First.class, Second.class}

    //
    // public class Severity {
    //     public static class Info implements Payload {};
    //     public static class Error implements Payload {};
    // }
    // @NotNull(message="would be nice if we had one", payload=Severity.Info.class)
    // @NotNull(message="the city is mandatory", payload=Severity.Error.class)

    @NotEmpty(message = "{NotNull.user.username}")
    @Length(min = 5, max = 20, message = "{Length.user.username}")
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "{Pattern.user.username}")
    private String username;

    @JsonProperty
    @NotNull(message = "{NotNull.user.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
