package com.mariahhau.events.Controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mariahhau.events.Database.Documents.Event;
import com.mariahhau.events.Security.JwtAuthenticationFilter;
import com.mariahhau.events.Service.EventService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EventController.class)
public class EventControllerTest {

    @MockBean
    private JwtAuthenticationFilter filter;

    @MockBean
    private EventService eventService;
    
    
    private MockMvc mockMvc;

    @BeforeEach
    void setup () {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new EventController(this.eventService))
            .build();
    }


    @WithMockUser
    @Test
    public void retrieveEventInfoById() throws Exception{
        
        Event mockEvent = new Event();
        mockEvent.setId(1000);
        mockEvent.setTitle("Juhlat");
        mockEvent.setDescription("OPMx2");
        mockEvent.setStartDate("2023-11-11");
        mockEvent.setEndDate("2023-11-11");
        mockEvent.setStartTime("18:00");
        mockEvent.setEndTime("23:00");
        mockEvent.setLocation("Muumilaakso");
        mockEvent.setOrganizer("Hemuli");
        
        Mockito.when(eventService.singleEvent(Mockito.anyLong())).thenReturn(Optional.of(mockEvent));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
            "http://localhost:8080/api/events/1000").accept(MediaType.ALL);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedResult = """
            {\"id\":1000,
            \"title\":\"Juhlat\",
            \"description\":\"OPMx2\",
            \"startDate\":\"2023-11-11\",
            \"endDate\":\"2023-11-11\",
            \"startTime\":\"18:00\",
            \"endTime\":\"23:00\",
            \"organizer\":\"Hemuli\",
            \"location\":\"Muumilaakso\",
            \"participants\":null,
            \"unregParticipants\":null,
            \"maxParticipants\": -1,
            \"participantCount\": 0}
            """;
        

        JSONAssert.assertEquals(expectedResult, result.getResponse().getContentAsString(), false);
        
    }

    
}
