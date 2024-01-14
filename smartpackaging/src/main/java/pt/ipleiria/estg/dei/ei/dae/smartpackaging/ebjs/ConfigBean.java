package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;

import java.time.LocalDate;
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
    @EJB
    private TransportPackageBean transportPackageBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello jave ee");

        productBean.create(1, "first prod", LocalDate.of(2024, 9, 29), 1.3, "massa, peperoni, queijo");
        productBean.create(2, "second prod", LocalDate.of(2030, 9, 29), 0.2, "cpu, gpu, ram");

        smartPackageBean.create(1, PackType.PRIMARY, "plastic", 1);
        smartPackageBean.create(2, PackType.PRIMARY, "plastic", 2);

        orderBean.create(1, LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 26));
        orderBean.create(2, LocalDate.now(), LocalDate.now().plusDays(7));

        smartPackageBean.addPackageToOrder(1, 1);
        smartPackageBean.addPackageToOrder(2, 1);

        transportPackageBean.create(1, "ali", 1);
        transportPackageBean.create(2, "aqui", 2);
    }
}
