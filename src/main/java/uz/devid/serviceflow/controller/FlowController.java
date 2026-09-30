package uz.devid.serviceflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.devid.serviceflow.entity.Request;
import uz.devid.serviceflow.repository.RequestRepository;

import java.util.List;

@RestController
@RequestMapping("/api/flows")
@RequiredArgsConstructor
public class FlowController {

    private final RequestRepository requestRepository;

    @GetMapping
    public List<Request> getAllFlows() {
        return requestRepository.findAll();
    }

    @org.springframework.web.bind.annotation.PostMapping
    public Request createFlow(@org.springframework.web.bind.annotation.RequestBody Request request) {
        return requestRepository.save(request);
    }
}
