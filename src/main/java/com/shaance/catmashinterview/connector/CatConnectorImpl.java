package com.shaance.catmashinterview.connector;

import com.shaance.catmashinterview.dto.CatDataDto;
import com.shaance.catmashinterview.entity.Cat;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Component
public class CatConnectorImpl implements CatConnector {

	@Override
	public Stream<Cat> getCatsFromURI(@NonNull URI uri) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CatDataDto> response;
		try{
			response = restTemplate.getForEntity(uri, CatDataDto.class);
			return catDataDtoToCatEntity(Objects.requireNonNull(response.getBody()));
		} catch (HttpClientErrorException e){
			log.error("Error while retrieving cat data.", e);
		} catch (IllegalArgumentException e){
			log.error("Error while transforming catDto to cat entity.", e);
		}
		return Stream.empty();
	}


	private Stream<Cat> catDataDtoToCatEntity(@NonNull CatDataDto catDataDto){
		return catDataDto.getImages()
				.stream()
				.map(item -> {
					try {
						return new Cat(item.getId(), new URI(item.getUrl()));
					} catch (URISyntaxException e) {
						throw new IllegalArgumentException("URI is badly formed.", e);
					}
				});
	}
}
