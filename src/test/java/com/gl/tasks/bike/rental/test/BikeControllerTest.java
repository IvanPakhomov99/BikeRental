package com.gl.tasks.bike.rental.test;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gl.tasks.bike.rental.bean.Bike;
import com.gl.tasks.bike.rental.enums.BikeType;
import com.gl.tasks.bike.rental.service.BikeService;
import com.gl.tasks.bike.rental.util.DTOConverterUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BikeControllerTest {
	
	@Autowired
    private MockMvc mvc;

    @MockBean
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
    
/*    @Test
    public void givenBikes_whenGetAllBikes_thenReturnJsonArray() throws Exception {
        given(bikeService.findAll()).willReturn(Arrays.asList(DTOConverterUtil.convertToBikeDTO(firstBike)));

        mvc.perform(get("/api/bikes/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(firstBike.getName())));
    }*/
}
