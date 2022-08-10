package com.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tb_temas")
public class Tema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "O atributo Descrição é obrigatório")
	private String descricao;
	
	@OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE) //foreign key vilidada e no posta foi criada
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagem;//var / tipo
	
	public Long getId() {
	return this.id;
	}
	public void setId(Long id) {
	this.id = id;
	}
	public String getDescricao() {
	return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Postagem> getPostagem() {
		return postagem;
	}
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
}
	
	
