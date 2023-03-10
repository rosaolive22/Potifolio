package TREVO.api.controller;

import TREVO.api.image.Image;
import TREVO.api.image.ImageRepository;
import TREVO.api.product.Product;
import TREVO.api.product.ProductDTO;//
import TREVO.api.product.ProductRepository;//
import jakarta.transaction.Transactional;//
import jakarta.validation.Valid;//
import org.springframework.beans.factory.annotation.Autowired;//
import org.springframework.data.domain.Page;//
import org.springframework.data.domain.Pageable;//
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;//
import org.springframework.web.bind.annotation.*;//

import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ImageRepository imageRepository;

    @PostMapping(value = "/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ProductDTO dados) {
        List<Image> imgs = imageRepository.findByIdIn(dados.imgsIds());
        repository.save(new Product(dados, imgs));
        return ResponseEntity.ok().body("Produto cadastrado com sucesso!");
    }
    @GetMapping(value = "/listar")
    public Page<Product> listar(@PageableDefault(size=10, sort={"name"}) Pageable paginacao){
        //Retorna apenas registros ativos
        //return  repository.findAllByAtivoTrue(paginacao);
        //Retorna todos registros
         return  repository.findAll(paginacao);
    }
    //Id dinâmico como parâmetro que passaremos na URL do insomnia
    @PutMapping(value = "/atualizar/{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid ProductDTO dados, @PathVariable Long id){
        Product product= repository.findById(id).orElse(null);
        assert product != null;
        product.atualizar(dados);
        repository.save(product);
        return ResponseEntity.ok().body("Produto atualizado com sucesso!");
    }
    @DeleteMapping(value = "/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        //Exclusão lógica, mantem arquivado:
        Product product = repository.findById(id).orElse(null);
        assert product != null;
        product.excluir();
        repository.save(product);
        //Excluir definitivamente:
        //repository.deleteById(id_product);
    }
}
