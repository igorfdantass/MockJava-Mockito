import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.service.GeradorDePagamento;

class GeradorDePagamentoTest{
    
    private GeradorDePagamento gerador;

    @Mock
    private PagamentoDao pagamentoDao;

    @Captor
    private ArgumentCaptor<Pagamento> captor;


    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.gerador = new GeradorDePagamento(pagamentoDao);
    }
    
    @Test
    void deveriaCriarPagamentoParaVencedorLeilao(){
        Leilao leilao = leilao();
        Lance lanceVencedor = leilao.getLanceVencedor();
        gerador.gerarPagamento(lanceVencedor);

        Mockito.verify(pagamentoDao).salvar(captor.capture());
        Pagamento pagamento = captor.getValue();
        assertEquals(LocalDate.now().plusDays(1), pagamento.getVencimento());
        assertEquals(lanceVencedor.getValor(), pagamento.getValor());
        
    }

    private Leilao leilao() {
		Leilao leilao = new Leilao("Celular", 
            new BigDecimal("500"), 
            new Usuario("Fulano"));
		Lance lance = new Lance(new Usuario("Beltrano"), 
				new BigDecimal("600"));
		leilao.propoe(lance);
		leilao.setLanceVencedor(lance);
		return leilao;
	}




}