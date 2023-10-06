package com.eduardo.teste.testeeduardo.Controller;
import com.eduardo.teste.testeeduardo.Model.ClimaModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@RestController
@Validated

@RequestMapping("/")
public class ClimaController {
    static final Logger logger = LogManager.getLogger(ClimaController.class.getName());

    @Value("${consultaCnpj}")
    private String consultaCnpjUrl;

    @Value("${consultaClima}")
    private String consultaClimaUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/consultar")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> consultarClima(@RequestParam @Valid String cnpj, Model model, HttpServletRequest request) {

       try {
           logger.trace("iniciando a aplicação");

           String cnpjResponse = restTemplate.getForObject(consultaCnpjUrl + cnpj, String.class);

            JSONObject cnpjData = new JSONObject(cnpjResponse);
            String latitude = cnpjData.optString("latitude");
            String longitude = cnpjData.optString("longitude");

            String climaResponse = restTemplate.getForObject(consultaClimaUrl + "?lat=-19.8218131" + latitude + "&lon=-44.0094874" + longitude, String.class);

            JSONObject climaData = new JSONObject(climaResponse);

            JSONArray weatherArray = climaData.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String condicaoDoTempo = weatherObject.optString("description");

            JSONObject tempObject = climaData.getJSONObject("main");
            String temperaturaAtual = tempObject.optString("temp");
            String temperaturaMinima = tempObject.optString("temp_min");
            String temperaturaMaxima = tempObject.optString("temp_max");

            JSONObject resultado = new JSONObject();
            resultado.put("temperatura_atual", temperaturaAtual);
            resultado.put("Condicao_tempo",condicaoDoTempo);

            JSONObject temperaturas = new JSONObject();
            temperaturas.put("temperaturaMinima",temperaturaMinima);
            temperaturas.put("temperaturaMaxima",temperaturaMaxima);

            resultado.put("temperatura_min_max",temperaturas);

           logger.trace("Host: {}, IP do solicitante: {}, URL da requisição: {}, Retorno: {}",
                    request.getRemoteHost(), request.getRemoteAddr(), request.getRequestURL(), resultado.toString());

            return ResponseEntity.ok(resultado.toString());
        } catch (Exception e) {
           logger.error("Erro ao consultar o clima.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar o clima.");
        }
    }
}
