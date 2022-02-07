package xin.wanyun.server.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xin.wanyun.server.response.MessageResponse;

@RestController
public class FileController {


    public ResponseEntity<Object> store(@RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok().body(new MessageResponse("上传成功"));
    }



}
