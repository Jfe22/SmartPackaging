package pt.ipleiria.estg.dei.ei.dae.smartpackaging.ebjs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.smartpackaging.entities.Operator;

@Stateless
public class OperatorBean {

    @PersistenceContext
    private EntityManager em;

    public void updateOperatorActivity(Long operatorId, String newLocationAndTrackingData, String newEnvironmentalConditionsData, String newSecurityAlertData) {
        Operator operator = em.find(Operator.class, operatorId);
        if (operator != null) {
            operator.setLocationAndTrackingData(newLocationAndTrackingData);
            operator.setEnvironmentalConditionsData(newEnvironmentalConditionsData);
            operator.setSecurityAlertData(newSecurityAlertData);
            em.merge(operator);
        } else {
            // Handle the case where the operator is not found
            throw new EntityNotFoundException("Operator with ID " + operatorId + " not found.");
        }
    }

    public Operator getOperatorDetails(Long operatorId) {
        return em.find(Operator.class, operatorId);
    }

    // Additional methods as required by your business logic
}
