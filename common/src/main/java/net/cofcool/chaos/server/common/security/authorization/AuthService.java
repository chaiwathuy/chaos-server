package net.cofcool.chaos.server.common.security.authorization;


import java.io.Serializable;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.cofcool.chaos.server.common.core.Message;
import net.cofcool.chaos.server.common.security.AbstractLogin;
import net.cofcool.chaos.server.common.security.Auth;
import net.cofcool.chaos.server.common.security.User;

/**
 * 授权管理
 *
 * @param <T> User中用户详细数据
 * @param <D> T中对应的详细数据
 * @param <ID> ID
 *
 * @author CofCool
 */
public interface AuthService<T extends Auth<D, ID>, D extends Serializable, ID extends Serializable> {

    /**
     * 登陆
     * @param loginUser 登陆时携带的参数
     * @return 登陆数据
     */
    Message<User<T, D, ID>> login(AbstractLogin loginUser);

    /**
     * 退出登陆
     */
    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 检查权限
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @return 是否通过验证
     */
    boolean checkPermission(ServletRequest servletRequest, ServletResponse servletResponse);

    /**
     * 读取当前登陆用户数据
     * @return {@link User}
     */
    @Nullable
    User<T, D, ID> readCurrentUser();

}
