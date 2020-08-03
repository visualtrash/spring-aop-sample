package com.nik2.aspect;

import com.nik2.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Around("execution(* com.nik2.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //print method
        String method = proceedingJoinPoint.getSignature().toShortString();
        myLogger.info("\n=====>>> Executing @Around on method: " + method);

        //get begin timestamp
        long begin = System.currentTimeMillis();

        //execute method
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            //log exc
            myLogger.warning(e.getMessage());

//            //give message to user
//            result = "Major accident! But dont worry";

            //rethrow exc
            throw e;
        }

        //get end timestamp
        long end = System.currentTimeMillis();

        //compute duration and print it
        long duration = end - begin;
        myLogger.info("\n=====>>> Duration: " + duration / 1000.0 + " sec");

        return result;
    }

    @After("execution(* com.nik2.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {


        //print method
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n=====>>> Executing @After Finally on method: " + method);

    }

    @AfterThrowing(
            pointcut = "execution(* com.nik2.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc"
    )
    public void afterThrowingFindAccountsAdvice(
            JoinPoint joinPoint, Throwable theExc) {

        //print method
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n=====>>> Executing @AfterThrowing on method: " + method);

        //print exc
        myLogger.info("\n=====>>> exc is : " + theExc);
    }

    //add new advice @AfterReturning of the findAccount()
    @AfterReturning(
            pointcut = "execution(* com.nik2.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(
            JoinPoint joinPoint, List<Account> result) {

        //print method
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n=====>>> Executing @AfterReturning on method: " + method);

        //print result
        myLogger.info("\n=====>>> result is : " + result);

        //modify data

        //convert acc names to uppercase
        converAccountNamesToUpperCase(result);

        myLogger.info("\n=====>>> result is : " + result);

    }

    @Before("com.nik2.aspect.NikAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint joinPoint) {

        myLogger.info("\n=====>>> Executing @Before advice on method");

        //display method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        myLogger.info("Method: " + methodSignature);

        //display method arguments

        //get args
        Object[] args = joinPoint.getArgs();

        //loop thru args
        for (Object tempArg : args) {
            myLogger.info(tempArg.toString());

            if (tempArg instanceof Account) {
                //downcast and print Acc spec stuff
                Account account = (Account) tempArg;

                myLogger.info("account name: " + account.getName());
                myLogger.info("account level: " + account.getLevel());
            }
        }
    }

    private void converAccountNamesToUpperCase(List<Account> result) {

        //loop thro accs
        for (Account tempAcc : result) {

            //get uppercase
            String upperName = tempAcc.getName().toUpperCase();

            //update names
            tempAcc.setName(upperName);
        }
    }
}