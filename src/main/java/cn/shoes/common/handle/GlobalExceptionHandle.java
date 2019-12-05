package cn.shoes.common.handle;

import cn.shoes.common.dto.ResponseCode;
import cn.shoes.common.enums.StatusEnums;
import cn.shoes.common.exception.GlobalException;
import cn.shoes.common.utils.HttpUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 简单处理全局异常信息
 *
 * @author shoes
 * @date 2019-02-13
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseCode exception(Exception e) {
        e.printStackTrace();
        return new ResponseCode(StatusEnums.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public Object handleAuthorizationException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        if (HttpUtil.isAjaxRequest(request)) {
            return new ResponseCode(StatusEnums.PERMISSION_ERROR);
        } else {
            ModelAndView view = new ModelAndView();
            view.setViewName("error/403");
            return view;
        }
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResponseCode globalExceptionHandle(GlobalException e, HttpServletResponse response) {
        e.printStackTrace();
        return new ResponseCode(response.getStatus(), e.getMsg());
    }
}