package com.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blogpessoal.model.Usuario;
import com.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuario")
	public void deveCriarUmUsuario() {
		HttpEntity <Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Paulo Antunes", "https://i.imgur.com/JR7kUFU.jpg", "paulo@email.com", "13465278"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getFoto(), resposta.getBody().getFoto());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
		
		}
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do usuario")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Maria da Silva", "https://i.imgur.com/NtyGneo.jpg", "maria_silva@email.com.br", "13465278"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Maria da Silva", "https://i.imgur.com/NtyGneo.jpg", "maria_silva@email.com.br", "13465278"));

		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Juliana Andrews", "i.imgur.jpg", "juliana_andrews@email.com", "juliana123"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
			"Juliana Andrews Ramos", "i.imgur.jpg", "juliana_ramos@email.com", "juliana123");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getFoto(), resposta.getBody().getFoto());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("listar todos os usuarios")
	public void deveMostrarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario (0L,
				"Sabrina","url","sabrina@email.com","sabrina321"));
		
		usuarioService.cadastrarUsuario(new Usuario (0L,
				"Ricardo","url","ricardo@email.com","ricardo321"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}


