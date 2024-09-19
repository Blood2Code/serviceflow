package uz.devid.serviceflow.listener;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.devid.serviceflow.config.RabbitConfig;
import uz.devid.serviceflow.entity.Request;
import uz.devid.serviceflow.repository.RequestRepository;

@Service
public class RequestListener {

    @Autowired
    private RequestRepository requestRepository;

    @RabbitListener(queues = RabbitConfig.SERVICE_FLOW_REQUEST_QUEUE)
    @Transactional
    public void handleRequest(Request request) {
        requestRepository.save(request);
    }
}
