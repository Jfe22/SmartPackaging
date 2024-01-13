package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

import java.util.Date;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private SmartPackageBean smartPackageBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private OrderBean orderBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello jave ee");

        productBean.create(1, "first prod", "2024/2/21", 1.3, "massa, peperoni, queijo");
        productBean.create(2, "second prod", "2030/2/23", 0.2, "cpu, gpu, ram");

        smartPackageBean.create(1, PackType.PRIMARY, "plastic", 1);
        smartPackageBean.create(2, PackType.PRIMARY, "plastic", 2);
    }
}
