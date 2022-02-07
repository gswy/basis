package xin.wanyun.server.socket.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineMessage implements Serializable {

    @JsonProperty("type")
    private String Type;

    @JsonProperty("count")
    private int Count;
}
