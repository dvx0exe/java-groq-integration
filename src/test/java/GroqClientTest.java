import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroqClientTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        // Configuração importante para ignorar campos desconhecidos, igual ao main
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void testDeserializacaoLivroUnico() throws JsonProcessingException {
        // Cenário: Um JSON válido representando um único livro
        String jsonLivro = """
                {
                    "nome_do_livro": "Alice no País das Maravilhas",
                    "ano_publicacao": "1865",
                    "editora_classica": "Macmillan",
                    "numero_paginas_estimado": 200,
                    "genero_literario": "Fantasia",
                    "resumo_sinopse": "Uma menina cai na toca de um coelho..."
                }
                """;

        // Ação: Converter JSON para Objeto
        GroqClient.Livro livro = mapper.readValue(jsonLivro, GroqClient.Livro.class);

        // Verificação: O objeto foi populado corretamente?
        assertNotNull(livro);
        assertEquals("Alice no País das Maravilhas", livro.getNomeDoLivro());
        assertEquals("1865", livro.getAnoPublicacao());
        assertEquals("Macmillan", livro.getEditoraClassica());
        assertEquals(200, livro.getNumeroPaginasEstimado());
        assertEquals("Fantasia", livro.getGeneroLiterario());
        assertEquals("Uma menina cai na toca de um coelho...", livro.getResumoSinopse());
    }

    @Test
    void testDeserializacaoBibliografiaCompleta() throws JsonProcessingException {
        // Cenário: O JSON completo esperado da API (objeto com lista 'bibliografia')
        String jsonCompleto = """
                {
                    "bibliografia": [
                        {
                            "nome_do_livro": "Livro A",
                            "ano_publicacao": "2000",
                            "editora_classica": "Editora A",
                            "numero_paginas_estimado": 100,
                            "genero_literario": "Drama",
                            "resumo_sinopse": "Sinopse A"
                        },
                        {
                            "nome_do_livro": "Livro B",
                            "ano_publicacao": "2001",
                            "editora_classica": "Editora B",
                            "numero_paginas_estimado": 150,
                            "genero_literario": "Comédia",
                            "resumo_sinopse": "Sinopse B"
                        }
                    ]
                }
                """;

        // Ação
        GroqClient.Bibliografia bibliografia = mapper.readValue(jsonCompleto, GroqClient.Bibliografia.class);

        // Verificação
        assertNotNull(bibliografia);
        assertNotNull(bibliografia.getBibliografia());
        assertEquals(2, bibliografia.getBibliografia().size());

        GroqClient.Livro livro1 = bibliografia.getBibliografia().get(0);
        assertEquals("Livro A", livro1.getNomeDoLivro());

        GroqClient.Livro livro2 = bibliografia.getBibliografia().get(1);
        assertEquals("Livro B", livro2.getNomeDoLivro());
    }

    @Test
    void testDeserializacaoComCamposExtrasIgnorados() throws JsonProcessingException {
        // Cenário: A API retorna campos que não mapeamos (ex: 'isbn')
        // O mapper deve ignorar isso devido ao FAIL_ON_UNKNOWN_PROPERTIES = false
        String jsonComExtra = """
                {
                    "nome_do_livro": "Teste Extra",
                    "isbn": "123-456-789",
                    "ano_publicacao": "2024"
                }
                """;

        GroqClient.Livro livro = mapper.readValue(jsonComExtra, GroqClient.Livro.class);

        assertNotNull(livro);
        assertEquals("Teste Extra", livro.getNomeDoLivro());
        assertEquals("2024", livro.getAnoPublicacao());
    }

    @Test
    void testDeserializacaoListaVazia() throws JsonProcessingException {
        String jsonVazio = "{ \"bibliografia\": [] }";

        GroqClient.Bibliografia dados = mapper.readValue(jsonVazio, GroqClient.Bibliografia.class);

        assertNotNull(dados);
        assertNotNull(dados.getBibliografia());
        assertTrue(dados.getBibliografia().isEmpty());
    }

    @Test
    void testBibliografiaGetterSetter() {
        // Teste simples do POJO Bibliografia (já que ele tem setter público)
        GroqClient.Bibliografia biblio = new GroqClient.Bibliografia();
        assertNull(biblio.getBibliografia());

        List<GroqClient.Livro> lista = List.of(new GroqClient.Livro());
        // Nota: Não conseguimos setar dados no Livro aqui pois ele não tem setters,
        // mas podemos testar a lista em si.

        biblio.setBibliografia(lista);
        assertEquals(1, biblio.getBibliografia().size());
    }
}