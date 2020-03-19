package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;


public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		 service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao() throws Exception {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme", 2, 5.0);
		
		// a��o
		Locacao locacao = service.alugarFilme(usuario, filme);
			
			// verifica��o
			error.checkThat(locacao.getValor(), is(equalTo(5.0)));
			error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
			
	}
	//elegante
	@Test(expected=FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		//cenario
		
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1",0,5.0);
		
		//acao
		service.alugarFilme(usuario, filme);
		
	}
	 //robusta mais adequada porque � mais completa
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		//cenario
		
		Filme filme = new Filme("Filme 2",1,4.0);
		
		
		
		//acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		}catch(LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
		System.out.println("Forma robusta");
		
	}
	
		//nova
		@Test
		public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
			//cenario
			
			Usuario usuario = new Usuario("Usuario 1");
			
			exception.expect(LocadoraException.class);
			exception.expectMessage("Filme vazio");
			
			//acao
			service.alugarFilme(usuario, null);
			
			System.out.println("Forma robusta");
		}
		
		
	
	
	
	
	
	
	
}
