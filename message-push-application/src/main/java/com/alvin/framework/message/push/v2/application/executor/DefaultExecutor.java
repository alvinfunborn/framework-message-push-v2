package com.alvin.framework.message.push.v2.application.executor;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * datetime 2020/1/18 20:17
 *
 * @author zhouwenxiang
 */
@Component
@ConditionalOnMissingBean(Executor.class)
public class DefaultExecutor implements Executor {

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        // todo block when full
        executorService = new ThreadPoolExecutor(
                1, 1, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));
    }

    @Override
    public void execute(Runnable r) {
        executorService.execute(r);
    }

    @Override
    public void execute(Runnable r, boolean first) {
        // todo 加到线程池头部
    }

    @Override
    public void executeOnSchedule(Runnable r, LocalDateTime time) {
        // todo 创建定时任务
    }
}
