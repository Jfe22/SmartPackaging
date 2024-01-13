package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton


public class ConfigBean {
    @EJB
    private SmartPackageBean smartPackageBean;
    @PostConstruct
    public void populateDB() {
        System.out.println("Hello jave ee");

        smartPackageBean.create(1, "first");
        smartPackageBean.create(2, "second");
        smartPackageBean.create(3, "3rd");
        smartPackageBean.create(4, "4rd");
    }
}
