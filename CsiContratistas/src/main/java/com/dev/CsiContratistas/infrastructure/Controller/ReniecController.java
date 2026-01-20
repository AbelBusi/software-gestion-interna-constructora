package com.dev.CsiContratistas.infrastructure.Controller;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/api/reniec")
public class ReniecController {
     @GetMapping("/dni/{dni}")
    public ResponseEntity<String> buscarDni(@PathVariable String dni) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Referer", "https://apis.net.pe/consulta-dni-api");
        headers.set("Authorization", "Bearer apis-token-16674.04EUMRKvRrJErq0Vm7yf7cDu5HrIluxR");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"No encontrado\"}");
        }
    }
    
}
