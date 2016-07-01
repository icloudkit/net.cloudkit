package net.cloudkit.enterprises.infrastructure.scheduling;

import net.cloudkit.enterprises.infrastructure.utilities.SpringContextHolder;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SomeJob implements Job {

    private static final long serialVersionUID = 1319673450675993416L;


    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        // TODO ApplicationServiceFactroy
        // System.out.println(SpringWiredBean.getInstance().getBean("testJob"));
        System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        System.out.println("------------------------------" + SpringContextHolder.getApplicationContext());

        // Process @Autowired injection for the given target object, based on the current web application context.
        // SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        // ...
    }

    /*
    // MethodInvokingJobDetailFactoryBean
    @Override
    public void execute() throws InterruptedException {

        System.out.println("------------------------------");
    }
    */

}
