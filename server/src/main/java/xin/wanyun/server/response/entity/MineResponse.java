package xin.wanyun.server.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MineResponse implements Serializable {

    @JsonProperty("id")
    private Long Id;

    @JsonProperty("username")
    private String Username;

    @JsonProperty("permissions")
    private List<String> permissions;
}
