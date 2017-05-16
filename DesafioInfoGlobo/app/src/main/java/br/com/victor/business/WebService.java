package br.com.victor.business;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import br.com.victor.annotation.Path;
import br.com.victor.desafioinfoglobo.R;
import br.com.victor.model.Capa;

/**
 * @author Victor Oliveira
 */
public class WebService {

    private RestTemplate restTemplate = null;
    private ObjectMapper objectMapper = null;
    private String url;

    public ArrayList<Capa> toList(Class<?> classEntity, Context context) throws Exception {
        ResponseEntity<String> responseEntity = null;
        ArrayList<Capa> list = null;
        try {
            url = context.getString(R.string.url_base);
            url += montarPath(classEntity);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            Log.i("URL toList()", url);

            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            responseEntity = getRestTemplateInstance().exchange(url, HttpMethod.GET, requestEntity, String.class);

            if (responseEntity.getBody() != null) {
                if (responseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                    String result = responseEntity.getBody().toString();
                    list = new ArrayList<>();
                    if (result != null && !result.isEmpty()) {
                        if (result.charAt(0) == '[') {
                            JavaType type = getObjectMapperInstance().getTypeFactory().constructCollectionType(List.class, classEntity);
                            list = getObjectMapperInstance().readValue(result, type);
                        } else {
                            list.add((Capa) getObjectMapperInstance().readValue(result, classEntity));
                        }
                    }
                } else {
                    throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
                }
            }
        } catch (Exception e) {
            throw new Exception(responseEntity.getStatusCode().value() + "," + responseEntity.getBody().toString());
        }
        return list;
    }


    private RestTemplate getRestTemplateInstance() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        }
        return restTemplate;
    }

    private ObjectMapper getObjectMapperInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

    private String montarPath(Class<?> classEntity) {
        String url = "";

        if (haAnnotation(classEntity, Path.class)) {
            url = this.url.charAt(this.url.length() - 1) == '/' ? getPathName(classEntity) : "/" + getPathName(classEntity);
        } else {
            url = this.url.charAt(this.url.length() - 1) == '/' ? classEntity.getSimpleName() : "/" + classEntity.getSimpleName();
        }
        return url;
    }

    private String getPathName(Class<?> targetClass) {
        return (targetClass.isAnnotationPresent(Path.class) ? targetClass
                .getAnnotation(Path.class).value() : targetClass.getSimpleName().toLowerCase());
    }

    private boolean haAnnotation(Class<?> targetClass, Class annotation) {
        return targetClass.isAnnotationPresent(annotation);
    }

}
