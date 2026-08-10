package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    @Value("${GROQ_API_KEY}")
    private String API_KEY;

    @PostMapping
    public String chat(@RequestBody String message) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.groq.com/openai/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject body = new JSONObject();
            body.put("model", "llama-3.3-70b-versatile");

            JSONArray messages = new JSONArray();

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);

            messages.put(userMessage);
            body.put("messages", messages);

            HttpEntity<String> request =
                    new HttpEntity<>(body.toString(), headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            JSONObject json = new JSONObject(response.getBody());

            return json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
