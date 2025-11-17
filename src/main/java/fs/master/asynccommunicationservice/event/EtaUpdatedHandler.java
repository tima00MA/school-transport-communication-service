package fs.master.asynccommunicationservice.event;

// Importations nécessaires
import fs.master.asynccommunicationservice.config.RabbitMQConfig;
import fs.master.asynccommunicationservice.service.AsyncService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EtaUpdatedHandler {
    @Autowired
    private AsyncService asyncService;

    @RabbitListener(queues = RabbitMQConfig.ETA_UPDATED_QUEUE)
    public void handleEtaUpdated(String payload) {
        asyncService.notifySubscribers(RabbitMQConfig.ETA_UPDATED_QUEUE, payload);
    }
}