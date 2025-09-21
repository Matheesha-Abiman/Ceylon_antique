package lk.ijse.backend.controller;

import lk.ijse.backend.dto.ChatGPTRequest;
import lk.ijse.backend.dto.ChatGPTResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate template;

    @GetMapping("/chat")
    public ChatGPTResponseDTO chat(@RequestParam("prompt") String prompt) {
        System.out.println("Prompt: " + prompt);

        try {
            // Build request
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);

            // Add headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

            // Make POST request
            ChatGPTResponseDTO responseDTO = template.postForObject(apiURL, entity, ChatGPTResponseDTO.class);

            System.out.println("Response: " + responseDTO);

            return responseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // Use constructor to create error DTO
            ChatGPTResponseDTO errorDTO = new ChatGPTResponseDTO();
            errorDTO.setError("Sorry, I'm having trouble connecting to the AI service. Please try again later.");
            return errorDTO;
        }
    }


//    @PostMapping("/chat")
//    public String chatPost(@RequestBody String prompt){
//        return chat(prompt);
//    }
}