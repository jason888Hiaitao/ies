package com.example.wmsiescore.exception;

import com.example.wmsiescore.common.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 异常处理测试类
 */
@SpringBootTest
public class ExceptionHandlingTest {

    @Test
    public void testBusinessException() {
        BusinessException exception = new BusinessException("测试业务异常");
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), exception.getCode());
        assertEquals("测试业务异常", exception.getMessage());
    }

    @Test
    public void testResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("试卷", 123L);
        assertEquals(ResponseCode.NOT_FOUND.getCode(), exception.getCode());
        assertEquals("试卷不存在: 123", exception.getMessage());
    }

    @Test
    public void testParameterValidationException() {
        ParameterValidationException exception = new ParameterValidationException("name", "不能为空");
        assertEquals(ResponseCode.BAD_REQUEST.getCode(), exception.getCode());
        assertEquals("参数验证失败 - name: 不能为空", exception.getMessage());
    }

    @Test
    public void testUnauthorizedException() {
        UnauthorizedException exception = new UnauthorizedException("用户未登录");
        assertEquals(ResponseCode.UNAUTHORIZED.getCode(), exception.getCode());
        assertEquals("用户未登录", exception.getMessage());
    }

    @Test
    public void testForbiddenException() {
        ForbiddenException exception = new ForbiddenException("无权限访问该资源");
        assertEquals(ResponseCode.FORBIDDEN.getCode(), exception.getCode());
        assertEquals("无权限访问该资源", exception.getMessage());
    }

    @Test
    public void testDataAccessException() {
        DataAccessException exception = new DataAccessException("数据库连接失败");
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), exception.getCode());
        assertEquals("数据访问异常: 数据库连接失败", exception.getMessage());
    }
}