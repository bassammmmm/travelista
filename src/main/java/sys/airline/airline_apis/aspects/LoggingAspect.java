package sys.airline.airline_apis.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(sys.airline.airline_apis.services..*)")
    public void serviceLayer() {}

    @Pointcut("within(sys.airline.airline_apis.controllers..*)")
    public void controllerLayer() {}

    @Before("serviceLayer() || controllerLayer()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Start method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning("serviceLayer() || controllerLayer()")
    public void logAfterReturning(JoinPoint joinPoint) {
        logger.info("Completed method: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "serviceLayer() || controllerLayer()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName() + " with cause: " + error.getMessage());
    }

    @Around("serviceLayer() || controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Method " + joinPoint.getSignature().getName() + " execution time: " + timeTaken + "ms");
        return result;
    }
}
