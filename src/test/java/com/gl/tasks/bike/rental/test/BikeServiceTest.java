package com.gl.tasks.bike.rental.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gl.tasks.bike.rental.Application;
import com.gl.tasks.bike.rental.bean.Bike;
import com.gl.tasks.bike.rental.dto.BikeDTO;
import com.gl.tasks.bike.rental.enums.BikeType;
import com.gl.tasks.bike.rental.repository.BikeRepository;
import com.gl.tasks.bike.rental.service.BikeService;
import com.gl.tasks.bike.rental.util.DTOConverterUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BikeServiceTest {
    
	@Mock
	private BikeRepository bikeRepository;
	
    @Autowired
    private BikeService bikeService;
    
    private Bike firstBike;
    
    private Bike secondBike;
    
    @Before
    public void setUp() {
    	firstBike = Bike.builder()
    			._id("firstId")
    			.name("ARDIS МТВ HAWK")
    			.price(new BigDecimal(150))
    			.type(BikeType.MOUNTAIN)
    			.build();
    	secondBike = Bike.builder()
    			._id("secondId")
    			.name("secondBike")
    			.price(new BigDecimal(200))
    			.type(BikeType.CLASSIC)
    			.build();

    	bikeService.upsert(DTOConverterUtil.convertToBikeDTO(firstBike));
    	bikeService.upsert(DTOConverterUtil.convertToBikeDTO(secondBike));
    }
    
    @Test
    public void testFindAll_thenBikeListShouldBeReturned() {
        List<BikeDTO> foundBikes = bikeService.findAll();

        assertNotNull(foundBikes);
        assertEquals(4, foundBikes.size());
    }
  
    @Test
    public void testFindById_thenBikeShouldBeReturned() {
        BikeDTO foundBike = bikeService.findById("firstId");

        assertNotNull(foundBike);
        assertEquals(firstBike.getName(), foundBike.getName());
        assertEquals(firstBike.get_id(), foundBike.getId());
    }
    
    @Test
    public void testFindByName_thenBikeListShouldBeReturned() {
        List<BikeDTO> foundBikes = bikeService.findByName("ARDIS МТВ HAWK");

        assertNotNull(foundBikes);
        assertEquals(1, foundBikes.size());
    }
    
    @Test
    public void testFindByType_thenBikeListShouldBeReturned() {
        List<BikeDTO> foundBikes = bikeService.findByType(BikeType.CLASSIC);

        assertNotNull(foundBikes);
        assertEquals(3, foundBikes.size());
    }
    
    @Test
    public void testFindByPriceBetween_thenBikeListShouldBeReturned() {
    	List<BikeDTO> foundBikes = bikeService.findByPriceBetween(new BigDecimal(100), new BigDecimal(300));
    	
    	assertNotNull(foundBikes);
    	assertEquals(4, foundBikes.size());
    }
    
    @Test
    public void testInsert_thenBikeShouldBeReturned() {
    	BikeDTO foundBike = bikeService.upsert(DTOConverterUtil.convertToBikeDTO(firstBike));
    	
    	assertNotNull(foundBike);
        assertEquals(firstBike.getName(), foundBike.getName());
        assertEquals(firstBike.get_id(), foundBike.getId());
    }
    
    @Test
    public void testUpdate_thenBikeShouldBeReturned() {
    	firstBike.update(firstBike.getName(), firstBike.getType(), new BigDecimal(120));
    	BikeDTO foundBike = bikeService.upsert(DTOConverterUtil.convertToBikeDTO(firstBike));
    	
    	assertNotNull(foundBike);
        assertEquals(firstBike.getName(), foundBike.getName());
        assertEquals(firstBike.get_id(), foundBike.getId());
    }

    @Test
    public void testDeleteBikeById() {
        BikeDTO bike = bikeService.delete(secondBike.get_id());

        assertNotNull(bike);
    }
}