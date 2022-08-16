package com.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.model.Usuario;

/**
 * 	Anotação @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) define a classe como uma classe de testes e habilita uma uma porta aleatória
 *  Anotação @TestInstance(TestInstance.Lifecycle.PER_CLASS) define se os testes serão feitos metodo a metodo ou a Classe inteira, neste caso por Classe (PER_CLASS)
 *  Anotação @BeforeAll define o que será feito antes dos testes serem iniciados
 *  Anotação @Test define que é um metodo a ser testado
 *  Anotação @DisplayName("Retorna 1 usuario") define um display que será mostrado como identificação do metodo no feedback do teste
 * */


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		usuarioRepository.save(new Usuario(0L, "joao da Silva","i.imgur","joao@email.com","12345678"));
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "https://i.imgur.com/NtyGneo.jpg", "manuela@email.com", "13465278"));	
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "https://i.imgur.com/mB3VM2N.jpg", "adriana@email.com", "13465278"));
        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "https://i.imgur.com/JR7kUFU.jpg", "paulo@email.com", "13465278"));

	}
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("joao da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));	
	}


}
