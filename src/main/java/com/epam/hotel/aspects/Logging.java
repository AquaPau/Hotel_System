package com.epam.hotel.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class Logging {

    @Pointcut("execution(* *.create(..))")
    private void allCreateMethods() {}

    @Pointcut("execution(* *.update(..))")
    private void allUpdateMethods() {}

    @Pointcut("execution(* *.getAll(..))")
    private void allGetAllMethods() {}

    @Pointcut("execution(* *.delete(..))")
    private void allDeleteMethods() {}

    @Pointcut("execution(* *.getById(..))")
    private void allGetByIdMethods() {}

    @Before("allCreateMethods()")
    public void logBeforeCreate(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName() + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allCreateMethods()", returning = "value")
    public void logAfterCreate(Object value) {
        if (value != null) { log.info("Added " + value.getClass().getSimpleName() + " to database"); }
    }

    @AfterThrowing(pointcut = "allCreateMethods()", throwing = "exc")
    public void logIfCreateFails(Throwable exc) {
        log.warn("Thrown: "+ exc);
    }

    @Before("allUpdateMethods()")
    public void logBeforeUpdate(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName() + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allUpdateMethods()", returning = "value")
    public void logAfterUpdate(Object value) {
        if (value != null && value.toString().toUpperCase().equals("TRUE")) { log.info("Updated entity in database"); }
    }

    @AfterThrowing(pointcut = "allUpdateMethods()", throwing = "exc")
    public void logIfUpdateFails(Throwable exc) {
        log.warn("Thrown: "+ exc);
    }

    @Before("allDeleteMethods()")
    public void logBeforeDelete(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName() + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allDeleteMethods()", returning = "value")
    public void logAfterDelete(Object value) {
        if (value != null && value.toString().toUpperCase().equals("TRUE")) { log.info("Deleted entity in database"); }
    }

    @AfterThrowing(pointcut = "allDeleteMethods()", throwing = "exc")
    public void logIfDeleteFails(Throwable exc) {
        log.warn("Thrown: "+ exc);
    }

    @Before("allGetAllMethods()")
    public void logBeforeGetAll(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName() + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allGetAllMethods()", returning = "value")
    public void logAfterGetAll(Object value) {
        if (value != null) { log.info("Retrieved a list"); }
    }

    @AfterThrowing(pointcut = "allGetAllMethods()", throwing = "exc")
    public void logIfGetAllFails(Throwable exc) {
        log.warn("Thrown: "+ exc);
    }

    @Before("allGetByIdMethods()")
    public void logBeforeGetById(JoinPoint joinPoint) {
        log.info(joinPoint.getTarget().getClass().getSimpleName() + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allGetByIdMethods()", returning = "value")
    public void logAfterGetById(Object value) {
        log.info("Accessed " + value.getClass().getSimpleName() + " from a database by id");
    }

    @AfterThrowing(pointcut = "allGetByIdMethods()", throwing = "exc")
    public void logIfGetByIdFails(Throwable exc) {
        log.warn("Thrown: "+ exc);
    }

}