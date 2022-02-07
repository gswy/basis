package xin.wanyun.server.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse implements Serializable {

    @JsonProperty("id")
    private Long Id;

    @JsonProperty("name")
    private String Name;

}
