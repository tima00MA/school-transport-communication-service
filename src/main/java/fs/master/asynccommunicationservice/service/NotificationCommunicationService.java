package fs.master.asynccommunicationservice.service;

import fs.master.asynccommunicationservice.dto.NotificationHistoryDTO;
import fs.master.asynccommunicationservice.dto.NotificationTypeDTO;
import fs.master.asynccommunicationservice.dto.NotificationSubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationCommunicationService {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl; // ex: http://localhost:8090/notifications

    /**
     * Get notification history for a specific user
     */
    public List<NotificationHistoryDTO> getNotificationHistoryByUserId(String userId) {
        String url = notificationServiceUrl + "/history/" + userId;
        NotificationHistoryDTO[] arr = restTemplate.getForObject(url, NotificationHistoryDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    /**
     * Get all notification types
     */
    public List<NotificationTypeDTO> getAllNotificationTypes() {
        String url = notificationServiceUrl + "/types";
        NotificationTypeDTO[] arr = restTemplate.getForObject(url, NotificationTypeDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    /**
     * Get all subscriptions of a user
     */
    public List<NotificationSubscriptionDTO> getUserSubscriptions(String userId) {
        String url = notificationServiceUrl + "/subscriptions/" + userId;
        NotificationSubscriptionDTO[] arr = restTemplate.getForObject(url, NotificationSubscriptionDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }
}
