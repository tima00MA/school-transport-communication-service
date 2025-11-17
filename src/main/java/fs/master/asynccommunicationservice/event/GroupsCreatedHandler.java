package fs.master.asynccommunicationservice.event;

// Importations nécessaires
import fs.master.asynccommunicationservice.config.RabbitMQConfig;
import fs.master.asynccommunicationservice.service.AsyncService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupsCreatedHandler {
    @Autowired
    private AsyncService asyncService;

    @RabbitListener(queues = RabbitMQConfig.GROUPS_CREATED_QUEUE)
    public void handleGroupsCreated(String payload) {
        asyncService.notifySubscribers(RabbitMQConfig.GROUPS_CREATED_QUEUE, payload);
    }
}