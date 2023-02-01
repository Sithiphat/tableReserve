package tableReserve;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TableIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TableController tableController;




    @Test
    void initTable() throws Exception {
        mvc.perform(post("/tables/22")).andExpect(status().isOk());


    }
    @Test
    void willThrowWhenAlreadyInitTable() throws Exception {

        assertThatThrownBy(() -> mvc.perform(post("/tables/22"))).isInstanceOf(Exception.class).hasMessageContaining("already init table");


    }
}