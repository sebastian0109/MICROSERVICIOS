package cl.duoc.ms_cursos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CursoModuloApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CURSO_JSON =
            "{\"titulo\":\"%s\",\"descripcion\":\"Curso de prueba\",\"precio\":19990,\"instructorId\":1}";

    @Test
    void crearCursoDevuelve201() throws Exception {
        mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(CURSO_JSON, "Java Básico")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void tituloDuplicadoDevuelve409() throws Exception {
        String body = String.format(CURSO_JSON, "Spring Boot");
        mockMvc.perform(post("/api/cursos").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/cursos").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void cursoSinTituloDevuelve400() throws Exception {
        mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"\",\"precio\":1000}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cursoInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/api/cursos/99999")).andExpect(status().isNotFound());
    }

    @Test
    void moduloConCursoInexistenteDevuelve404() throws Exception {
        mockMvc.perform(post("/api/modulos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Intro\",\"orden\":1,\"curso\":{\"id\":99999}}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void noSePuedeEliminarCursoConModulos() throws Exception {
        String respuesta = mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(CURSO_JSON, "Curso con módulos")))
                .andReturn().getResponse().getContentAsString();
        String id = respuesta.replaceAll(".*\"id\":(\\d+).*", "$1");

        mockMvc.perform(post("/api/modulos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Intro\",\"orden\":1,\"curso\":{\"id\":" + id + "}}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/cursos/" + id)).andExpect(status().isConflict());
    }
}
