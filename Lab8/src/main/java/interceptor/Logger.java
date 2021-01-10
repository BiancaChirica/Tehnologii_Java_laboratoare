package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;

@Interceptor
@Transactional
@InterceptorBinding
public class Logger {
    public static String loggerFilePath = "C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\lab8\\src\\main\\java\\loggerFile.txt";

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();
        String target = className + "." + methodName + "()";
        long t1 = System.currentTimeMillis();
        try {
            return ctx.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long t2 = System.currentTimeMillis();
            String message = target + " took " + (t2 - t1) + "ms to execute\n";
            System.out.println(message);
            File file = new File(loggerFilePath);
            FileWriter fr = new FileWriter(file, true);
            fr.write(message);
            fr.close();
        }
    }
}
