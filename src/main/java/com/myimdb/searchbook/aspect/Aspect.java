package com.myimdb.searchbook.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
class LogginAspect {


    @After("execution(public String com.myimdb.searchbook.controller.*.getHello())  "
            +
            " && execution(public String com.myimdb.searchbook.controller.*.getBye()"
    )
    public void logAfter()
    {
        System.out.println("BYE_Request Received-3");
    }
//
////    @Around("execution(public String com.myimdb.searchbook.controller.BookController.getHello())")
////    public void logAround()
////    {
////        System.out.println("Request Received-2");
////    }
//
//    @Before("execution(public String com.myimdb.searchbook.controller.BookController.getHello())")
//    public void logBefore()
//    {
//        System.out.println("Request Received-3");
//    }
//
//
//    @Before("execution(public String com.myimdb.searchbook.controller.BookController.*(..))")
//    public void logBefore_Bye()
//    {
//        System.out.println("BYE_Request Received-3");
//    }
//
//    @After("execution(public String com.myimdb.searchbook.controller.BookController.*(..))")
//    public void logAfter_Bye()
//    {
//        System.out.println("BYE_Request Received-3");
//    }

    @Before("within(com.myimdb.searchbook.controller.*)")
    public void log_for_all_controller()
    {
        System.out.println("Request For Controller Package");
    }
    @Before("within(com.myimdb.searchbook.controller.BookController)")
    public void log_for_BookController()
    {
        System.out.println("Request For BookController");
    }

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void log_for_RestController()
    {
        System.out.println("Request For RestController");
    }

    @Before("execution(* com.myimdb.searchbook.controller.BookController && args(id)")
    public void demo()
    {
        System.out.println("Request for Method having String, String as input");
    }

    @Before()

}
