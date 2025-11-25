package com.saep.eletronicos.Configurations;

import com.saep.eletronicos.Entities.*;
import com.saep.eletronicos.Repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Configuration
public class DataLoader {

    // Injeção de todos os Repositórios e do Encoder
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final AtributoEspecificoRepository atributoEspecificoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ProdutoAtributoRepository produtoAtributoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository,
                      AtributoEspecificoRepository atributoEspecificoRepository, ProdutoRepository produtoRepository,
                      EstoqueRepository estoqueRepository, ProdutoAtributoRepository produtoAtributoRepository,
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.atributoEspecificoRepository = atributoEspecificoRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
        this.produtoAtributoRepository = produtoAtributoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner initDatabase() {
        return args -> {
            System.out.println("Iniciando População de Dados Iniciais...");

            carregarUsuarios();

            Categoria eletronicos = carregarCategorias("Eletrônicos");
            Categoria informatica = carregarCategorias("Informática");

            AtributoEspecifico memoria = carregarAtributos("Memória RAM", "GB");
            AtributoEspecifico processador = carregarAtributos("Processador", "Unidade");
            AtributoEspecifico tela = carregarAtributos("Tamanho da Tela", "polegadas");

            carregarProdutoExemplo(informatica, memoria, processador, tela);

            System.out.println("População de Dados Iniciais Concluída.");
        };
    }

    private void carregarUsuarios() {
        if (usuarioRepository.count() == 0) {
            System.out.println("Criando Usuários de Teste...");

            // 🚨 CORREÇÃO: Usando PerfilUsuario.X para criar as entidades Usuario
            Usuario admin = new Usuario("admin", passwordEncoder.encode("123456"), "Administrador Geral", PerfilUsuario.ADMIN);
            Usuario operador = new Usuario("estoque", passwordEncoder.encode("123456"), "Operador Estoque", PerfilUsuario.OPERADOR_ESTOQUE);
            Usuario visualizador = new Usuario("visual", passwordEncoder.encode("123456"), "Visualizador", PerfilUsuario.VISUALIZADOR);

            usuarioRepository.saveAll(List.of(admin, operador, visualizador));
        }
    }

    private Categoria carregarCategorias(String nome) {
        return categoriaRepository.findByNomeCategoriaIgnoreCase(nome)
                .orElseGet(() -> categoriaRepository.save(new Categoria(nome)));
    }


    private AtributoEspecifico carregarAtributos(String nome, String unidade) {
        return atributoEspecificoRepository.findByNomeAtributoIgnoreCase(nome)
                .orElseGet(() -> {
                    UnidadeMedida unidadeEnum = UnidadeMedida.valueOf(unidade.toUpperCase());
                    AtributoEspecifico novoAtributo = new AtributoEspecifico(nome, unidadeEnum);
                    return atributoEspecificoRepository.save(novoAtributo);
                });
    }

    private void carregarProdutoExemplo(Categoria categoria, AtributoEspecifico memoria, AtributoEspecifico processador, AtributoEspecifico tela) {
        if (produtoRepository.findByCodigoSku("LAP-1001") == null) {
            System.out.println("Criando Produto de Exemplo...");

            Produto laptop = new Produto(
                    "Laptop Gamer X100",
                    "LAP-1001",
                    "TechCorp",
                    new BigDecimal("4500.00"),
                    categoria
            );

            Estoque estoque = new Estoque();
            estoque.setProduto(laptop);
            estoque.setQuantidadeAtual(25);
            estoque.setEstoqueMinimo(5);

            laptop.setEstoque(estoque);
            produtoRepository.save(laptop);

            ProdutoAtributo pa1 = criarProdutoAtributo(laptop, memoria, "16");
            ProdutoAtributo pa2 = criarProdutoAtributo(laptop, processador, "Intel i7");
            ProdutoAtributo pa3 = criarProdutoAtributo(laptop, tela, "15.6");

            produtoAtributoRepository.saveAll(Set.of(pa1, pa2, pa3));
        }
    }

    private ProdutoAtributo criarProdutoAtributo(Produto produto, AtributoEspecifico atributo, String valor) {
        ProdutoAtributo pa = new ProdutoAtributo();

        ProdutoAtributoId id = new ProdutoAtributoId();
        id.setIdProduto(produto.getIdProduto());
        id.setIdAtributo(atributo.getIdAtributo());
        pa.setId(id);

        pa.setProduto(produto);
        pa.setAtributoEspecifico(atributo);
        pa.setValorAtributo(valor);

        return pa;
    }
}