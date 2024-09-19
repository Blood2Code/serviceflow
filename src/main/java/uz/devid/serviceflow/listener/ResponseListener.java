package uz.devid.serviceflow.listener;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.devid.serviceflow.config.RabbitConfig;
import uz.devid.serviceflow.entity.Request;
import uz.devid.serviceflow.entity.Response;
import uz.devid.serviceflow.repository.RequestRepository;
import uz.devid.serviceflow.repository.ResponseRepository;

import java.util.Optional;

@Service
public class ResponseListener {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private RequestRepository requestRepository;

    @RabbitListener(queues = RabbitConfig.SERVICE_FLOW_RESPONSE_QUEUE)
    @Transactional
    public void handleResponse(Response response) {
        Optional<Request> request = requestRepository.findLastByRequestPath(response.getSourceService());
        if (request.isPresent()) {
            Request updateRequest = request.get();
            updateRequest.setResponse(response);
            requestRepository.save(updateRequest);
            responseRepository.save(response);
        }
    }
}
