package cn.repeatlink.module.judicial.vo.estate;

import lombok.Data;

import java.util.Date;

@Data
public class FaceVo {
    private String faceId;
    private Date updateTime;

    public FaceVo(String faceId, Date updateTime) {
        this.faceId = faceId;
        this.updateTime = updateTime;
    }

    public FaceVo() {
    }
}
