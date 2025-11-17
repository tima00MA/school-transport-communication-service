package fs.master.asynccommunicationservice.service;

import fs.master.asynccommunicationservice.model.MessageDTO;
import fs.master.asynccommunicationservice.model.MessageLog;
import fs.master.asynccommunicationservice.model.MessageLogDTO;
import fs.master.asynccommunicationservice.model.Subscription;
import fs.master.asynccommunicationservice.repository.MessageLogRepository;
import fs.master.asynccommunicationservice.repository.SubscriptionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsyncService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageLogRepository messageLogRepository;
    private final SubscriptionRepository subscriptionRepository;

    public AsyncService(RabbitTemplate rabbitTemplate, MessageLogRepository messageLogRepository,
                        SubscriptionRepository subscriptionRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageLogRepository = messageLogRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void publishMessage(MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(messageDTO.queue(), messageDTO.payload());
        MessageLog log = new MessageLog();
        log.setQueue(messageDTO.queue());
        log.setPayload(messageDTO.payload());
        log.setTimestamp(java.time.LocalDateTime.now());
        messageLogRepository.save(log);
    }

    public void subscribe(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public List<MessageLogDTO> getMessageLogs() {
        return messageLogRepository.findAll().stream()
                .map(log -> new MessageLogDTO(
                        log.getId(),
                        log.getQueue(),
                        log.getPayload(),
                        log.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    public void notifySubscribers(String queue, String payload) {
        List<Subscription> subscriptions = subscriptionRepository.findByQueue(queue);
        // Logic to notify subscribers via REST callbacks (omitted for brevity)
    }
}