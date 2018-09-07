package com.management;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ManagementApplication {

    public static void main(String[] args){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(

                    "classpath:/provider-dubbo.xml"

        );
        try{
            System.out.println("management MicroServce provided, main thead wait!");
            byte[] b = new byte[0];
            synchronized(b){
                b.wait();
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            ctx.close();
            ctx.destroy();
        }

    }
}
