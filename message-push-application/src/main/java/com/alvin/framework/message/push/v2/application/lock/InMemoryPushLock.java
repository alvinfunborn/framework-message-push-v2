package com.alvin.framework.message.push.v2.application.lock;

import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * datetime 2020/1/18 20:32
 *
 * @author zhouwenxiang
 */
@Component
@ConditionalOnMissingBean(AbstractPushLock.class)
public class InMemoryPushLock extends AbstractPushLock {

    private Map<String, String> lockMap;

    @PostConstruct
    public void init() {
        lockMap = new ConcurrentHashMap<>(32);
    }

    @Override
    public boolean tryLock(String name) {
        return lockMap.putIfAbsent(name, "") == null;
    }

    @Override
    public void unlock(String name) {
        lockMap.remove(name);
    }
}
