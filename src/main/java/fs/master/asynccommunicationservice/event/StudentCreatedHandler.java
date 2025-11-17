package fs.master.asynccommunicationservice.event;

// Importations nécessaires
import fs.master.asynccommunicationservice.config.RabbitMQConfig;
import fs.master.asynccommunicationservice.service.AsyncService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentCreatedHandler {
    @Autowired
    private AsyncService asyncService;

    @RabbitListener(queues = RabbitMQConfig.STUDENT_CREATED_QUEUE)
    public void handleStudentCreated(String payload) {
        asyncService.notifySubscribers(RabbitMQConfig.STUDENT_CREATED_QUEUE, payload);
    }
}