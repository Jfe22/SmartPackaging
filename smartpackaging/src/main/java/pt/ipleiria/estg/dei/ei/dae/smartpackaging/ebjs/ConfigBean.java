package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.User;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.PackType;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.enums.UserRole;

import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

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
    @EJB
    private UserBean userBean;
    @EJB
    private ConsumerBean consumerBean;
    @EJB
    private OperatorBean operatorBean;
    @EJB
    private ProducerBean producerBean;
    private static final Logger logger = Logger.getLogger("ebjs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello jave ee");

        try {
            productBean.create(1, "first prod", LocalDate.of(2024, 9, 29), 1.3, "massa, peperoni, queijo");
            productBean.create(2, "second prod", LocalDate.of(2030, 9, 29), 0.2, "cpu, gpu, ram");
            productBean.create(3, "3rd prod", LocalDate.now(), 1.2, "isto aquilo e o outro");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            smartPackageBean.create(1, PackType.PRIMARY, "plastic", 1);
            smartPackageBean.create(2, PackType.PRIMARY, "plastic", 2);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            orderBean.create(1, LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 26));
            orderBean.create(2, LocalDate.now(), LocalDate.now().plusDays(7));
            orderBean.create(3, LocalDate.now(), LocalDate.now().plusDays(2));
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            smartPackageBean.addPackageToOrder(1, 1);
            smartPackageBean.addPackageToOrder(2, 1);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            transportPackageBean.create(1, "ali", 1);
            transportPackageBean.create(2, "aqui", 2);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        // users
        // userBean.create(1, "admin", "admin@example.com", "adminpass", UserRole.PRODUCER);
        // userBean.create(2, "user", "user@example.com", "userpass", UserRole.CONSUMER);
        // userBean.create(3, "user2", "user2@example.com", "user2pass", UserRole.OPERATOR);

        // consumers
        try {
            consumerBean.create(5L, "consumer1", "consumer1@example.com", "123", UserRole.CONSUMER, null, null, null);
            consumerBean.create(6L, "consumer2", "consumer2@example.com", "123", UserRole.CONSUMER, null, null, null);
            consumerBean.create(7L, "consumer3", "consumer3@example.com", "123", UserRole.CONSUMER, null, null, null);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        // operators
        try {
            operatorBean.create(10L, "operator1", "operator1@example.com", "123", UserRole.OPERATOR, null, null, null);
            operatorBean.create(11L, "operator2", "operator2@example.com", "123", UserRole.OPERATOR, null, null, null);
            operatorBean.create(12L, "operator3", "operator3@example.com", "123", UserRole.OPERATOR, null, null, null);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        // producers
        try {
            producerBean.create(15L, "producer1", "producer1@example.com", "123", UserRole.PRODUCER, null, null);
            producerBean.create(16L, "producer2", "producer2@example.com", "123", UserRole.PRODUCER, null, null);
            producerBean.create(17L, "producer3", "producer3@example.com", "123", UserRole.PRODUCER, null, null);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
