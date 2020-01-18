package com.alvin.framework.message.push.v2.application.recorder;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import com.alvin.framework.message.push.v2.wrapper.recorder.WrappedTunnelRecorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * datetime 2020/1/18 20:38
 *
 * @author zhouwenxiang
 */
@Component
@ConditionalOnMissingBean(WrappedTunnelRecorder.class)
public class InMemoryTunnelRecorder implements WrappedTunnelRecorder {

    private Map<String, CopyOnWriteArrayList<LocalDateTime>> successRecordMap;
    private Map<String, CopyOnWriteArrayList<LocalDateTime>> attemptRecordMap;

    @PostConstruct
    public void init() {
        successRecordMap = new ConcurrentHashMap<>(4);
        attemptRecordMap = new ConcurrentHashMap<>(4);
    }

    @Override
    public LocalDateTime lastSuccessTime(AbstractTunnel tunnel) {
        CopyOnWriteArrayList<LocalDateTime> records = successRecordMap.get(tunnel.getName());
        if (records != null) {
            return records.get(0);
        }
        return null;
    }

    @Override
    public LocalDateTime lastSuccessTime(AbstractTunnel tunnel, long rindex) {
        CopyOnWriteArrayList<LocalDateTime> records = successRecordMap.get(tunnel.getName());
        if (records != null) {
            return records.get((int) rindex - 1);
        }
        return null;
    }

    @Override
    public LocalDateTime lastAttemptTime(AbstractTunnel tunnel) {
        CopyOnWriteArrayList<LocalDateTime> records = attemptRecordMap.get(tunnel.getName());
        if (records != null) {
            return records.get(0);
        }
        return null;
    }

    @Override
    public LocalDateTime lastAttemptTime(AbstractTunnel tunnel, long rindex) {
        CopyOnWriteArrayList<LocalDateTime> records = attemptRecordMap.get(tunnel.getName());
        if (records != null) {
            return records.get((int) rindex - 1);
        }
        return null;
    }

    @Override
    public void recordSuccess(AbstractTunnel tunnel) {
        String name = tunnel.getName();
        if (successRecordMap.containsKey(name)) {
            successRecordMap.get(name).add(0, LocalDateTime.now());
        } else {
            CopyOnWriteArrayList<LocalDateTime> records = new CopyOnWriteArrayList<>();
            records.add(LocalDateTime.now());
            successRecordMap.put(name, records);
        }
    }

    @Override
    public void recordAttempt(AbstractTunnel tunnel) {
        String name = tunnel.getName();
        if (attemptRecordMap.containsKey(name)) {
            attemptRecordMap.get(name).add(0, LocalDateTime.now());
        } else {
            CopyOnWriteArrayList<LocalDateTime> records = new CopyOnWriteArrayList<>();
            records.add(LocalDateTime.now());
            attemptRecordMap.put(name, records);
        }
    }
}
