package cn.edu.tit.forum.dto;

import exception.CustomizeErrorCode;
import exception.CustomizeException;
import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/12
 */
@Data
public class ResultDTO<T> {
    // 唯一标识的提示码
    private Integer code;
    // 提示信息
    private String message;
    // 泛型T，期望它可以返回list或者Object类型
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okof() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okof(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }
}
