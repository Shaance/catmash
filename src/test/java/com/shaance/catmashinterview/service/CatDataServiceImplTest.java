package com.shaance.catmashinterview.service;

import com.shaance.catmashinterview.connector.CatConnector;
import com.shaance.catmashinterview.dao.CatDao;
import com.shaance.catmashinterview.entity.Cat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

public class CatDataServiceImplTest {

	@Mock
	private CatConnector catConnector;

	@Mock
	private CatDao catDao;

	@InjectMocks
	private CatDataServiceImpl catDataService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getCatsBadURI() {
		setUriField("11http:/s./s");
		Flux<Cat> cats = catDataService.getCats();
		Assert.assertEquals(Flux.empty(), cats);
	}

	@Test
	public void getCatsWhenCatDaoFindNothingConnectorIsCalledButIsEmpty() {
		Mockito.when(catDao.findAll()).thenReturn(Flux.empty());
		setUriField("http://bla.com");
		Mockito.when(catConnector.getCatsFromURI(any(URI.class))).thenReturn(Stream.empty());
		Mockito.when(catDao.saveAll(any(Flux.class))).thenReturn(Flux.empty());
		Flux<Cat> cats = catDataService.getCats();
		Mockito.verify(catConnector, Mockito.times(1)).getCatsFromURI(any(URI.class));
		Mockito.verify(catDao, Mockito.never()).save(any(Cat.class));
		Assert.assertFalse(cats.hasElements().block());
	}

	@Test
	public void getCatsWhenCatDaoFindNothingConnectorIsCalled() throws URISyntaxException {
		Mockito.when(catDao.findAll()).thenReturn(Flux.empty());
		setUriField("http://bla.com");
		Mockito.when(catConnector.getCatsFromURI(any(URI.class)))
				.thenReturn(Stream.of(
						new Cat("id1", new URI("uri1")),
						new Cat("id2", new URI("uri2"))
				));
		Mockito.when(catDao.saveAll(any(Flux.class))).thenReturn(Flux.just(new Cat(null, null)));
		Flux<Cat> cats = catDataService.getCats();
		Assert.assertTrue(cats.hasElements().block());
		Mockito.verify(catConnector, Mockito.times(1)).getCatsFromURI(any(URI.class));
		Mockito.verify(catDao, Mockito.times(1)).saveAll(any(Flux.class));
	}

	@Test
	public void getCats() throws URISyntaxException {
		Mockito.when(catDao.findAll())
				.thenReturn(Flux.just(
				new Cat("id1", new URI("uri1")),
				new Cat("id2", new URI("uri2"))
		));
		setUriField("http://bla.com");
		Mockito.when(catConnector.getCatsFromURI(any(URI.class))).thenReturn(Stream.empty());
		Mockito.when(catDao.saveAll(any(Flux.class))).thenReturn(Flux.empty());
		Flux<Cat> cats = catDataService.getCats();
		Assert.assertTrue(cats.hasElements().block());
	}

	private void setUriField(String s) {
		Field field = ReflectionUtils.findField(CatDataServiceImpl.class, "uri");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, catDataService, s);
	}


}