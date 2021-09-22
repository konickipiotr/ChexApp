package com.chex.modules.checkplace;

import android.os.AsyncTask;

import com.chex.config.Settings;
import com.chex.utils.Coords;
import com.chex.utils.HttpRequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


public class CheckPlaceAsync extends AsyncTask<Coords, Void, CheckPlaceResponse> {
    @Override
    protected CheckPlaceResponse doInBackground(Coords... coords) {
        Coords coord = coords[0];
        String path = Settings.ROOT_PATH + "/checkplace";
        UriComponents build = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("latitude", coord.latitude)
                .queryParam("longitude", coord.longitude)
                .build();

        HttpRequestUtils requestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
        RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        CheckPlaceResponse ret = new CheckPlaceResponse();
        try {
            ResponseEntity<CheckPlaceResponse> response = restTemplate.exchange(build.toUriString(), HttpMethod.GET, new HttpEntity<>(requestHeaders), CheckPlaceResponse.class);
            ret = response.getBody();
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                ret.setResponseStatus(CheckPlaceResponseStatus.NOTFOUND);
            else
                ret.setResponseStatus(CheckPlaceResponseStatus.UNKNOWNERROR);
        }catch (RestClientException e){
            ret.setResponseStatus(CheckPlaceResponseStatus.SERVERCONNECTIONERROR);
        }
        return ret;
    }
}
