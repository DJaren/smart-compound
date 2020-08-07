package cn.gov.suzhou.system.api.factory;

import cn.gov.suzhou.system.api.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.gov.suzhou.common.core.domain.R;
import cn.gov.suzhou.system.api.RemoteUserService;
import feign.hystrix.FallbackFactory;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public R<UserInfo> getUserInfo(String username)
            {
                return null;
            }
        };
    }
}
