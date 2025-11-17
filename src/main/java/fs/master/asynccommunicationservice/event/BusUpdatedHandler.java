package fs.master.asynccommunicationservice.event;

// Importations nécessaires
import fs.master.asynccommunicationservice.config.RabbitMQConfig;
import fs.master.asynccommunicationservice.service.AsyncService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusUpdatedHandler {
    @Autowired
    private AsyncService asyncService; // Service asynchrone

    // Consomme les messages de la file bus.updated
    @RabbitListener(queues = RabbitMQConfig.BUS_UPDATED_QUEUE)
    public void handleBusUpdated(String payload) {
        asyncService.notifySubscribers(RabbitMQConfig.BUS_UPDATED_QUEUE, payload); // Notifie abonnés
    }
}