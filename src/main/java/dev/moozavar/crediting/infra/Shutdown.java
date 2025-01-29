package dev.moozavar.crediting.infra;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Shutdown {
    @PreDestroy
    public void destroy() throws InterruptedException {
        log.info("Executing PreDestroy hook before shutting down...");
    }
}
