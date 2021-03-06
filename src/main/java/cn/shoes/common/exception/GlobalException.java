package cn.shoes.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shoes
 * @date 2019-02-13
 */
public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String message) {
        this.msg = message;
    }
}
