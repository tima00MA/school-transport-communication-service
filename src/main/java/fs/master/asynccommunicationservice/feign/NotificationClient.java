package fs.master.asynccommunicationservice.feign;


import fs.master.asynccommunicationservice.model.ApiResponse;
import fs.master.asynccommunicationservice.model.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service", url = "${services.notification.url}")
public interface NotificationClient {

    @PostMapping("/notifications/send")
    ApiResponse sendNotification(@RequestBody Notification notification);

    @GetMapping("/notifications/history/{user_id}")
    ApiResponse getNotificationHistory(@PathVariable("user_id") Long userId);
}
