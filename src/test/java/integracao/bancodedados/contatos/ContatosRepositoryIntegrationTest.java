package integracao.bancodedados.contatos;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.bancodedados.model.ContatoModel;
import integracao.bancodedados.repository.ContatoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest //injeta todas as dependencias necessarias para subir a classe de teste
public class ContatosRepositoryIntegrationTest {

	@Autowired
	private ContatoRepository contatoRepository; //injetnado repository

	@Before //carrega esta classe primeiro
	public void start() {
		ContatoModel contato = new ContatoModel("Chefe", "0y", "9xxxxxxx9");
		if (contatoRepository.findFirstByNome(contato.getNome()) == null)
			contatoRepository.save(contato);
		// instancia novos contatos e salva no banco de dados
		contato = new ContatoModel("Novo Chefe", "0y", "8xxxxxxx8");
		if (contatoRepository.findFirstByNome(contato.getNome()) == null)
			contatoRepository.save(contato);

		contato = new ContatoModel("chefe Mais Antigo", "0y", "7xxxxxxx7");
		if (contatoRepository.findFirstByNome(contato.getNome()) == null)
			contatoRepository.save(contato);

		contato = new ContatoModel("Amigo", "0z", "5xxxxxxx5");
		if (contatoRepository.findFirstByNome(contato.getNome()) == null)
			contatoRepository.save(contato);
	}

	@Test
	public void findByNomeRetornaContato() throws Exception {
		//essa classe testa pra ver se o metodo find by nome esta funcionando e retornando o dado certo 
		ContatoModel contato = contatoRepository.findFirstByNome("Chefe");

		Assert.assertTrue(contato.getNome().equals("Chefe")); //assert true retorna a validação da resposta no caso "chefe"
	}

	@Test
	public void findAllByNomeIgnoreCaseRetornaTresContato() {
		//esta classe valida a quantidade de valores que retornam na lista
		List<ContatoModel> contatos = contatoRepository.findAllByNomeIgnoreCaseContaining("chefe");
		
		Assert.assertEquals(3, contatos.size()); //assert equals valida se o valor adquirido é igual o esperado
	}

	@After //usada para limpar a base de dados ao termino de cada teste
	public void end() {
		contatoRepository.deleteAll();
	}

}