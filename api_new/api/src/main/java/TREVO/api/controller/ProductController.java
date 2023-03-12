package TREVO.api.controller;

import TREVO.api.catalog.Catalog;
import TREVO.api.catalog.CatalogRepository;
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
    @Autowired
    private CatalogRepository catalogRepository;

    @PostMapping(value = "/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ProductDTO dados) {
        List<Image> imgs = imageRepository.findByIdIn(dados.imgsIds());
        List<Catalog> catalogs = catalogRepository.findByIdIn(dados.catalogIds());
        repository.save(new Product(dados, imgs, catalogs));
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
    @DeleteMapping(value = "excluir/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id){
        //Exclusão lógica, mantem arquivado:
        Product product = repository.findById(id).orElse(null);
        assert product != null;
        product.excluir();
        repository.save(product);
        return ResponseEntity.ok().body("Exclusão lógica concluida.");
        //Excluir definitivamente:
        //repository.deleteById(id_product);
    }
}
