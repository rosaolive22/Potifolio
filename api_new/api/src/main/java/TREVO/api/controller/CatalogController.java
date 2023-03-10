package TREVO.api.controller;

import TREVO.api.catalog.Catalog;
import TREVO.api.catalog.CatalogRepository;
import TREVO.api.catalog.ONCatalog;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("catalog")

public class CatalogController {
    @Autowired
    private CatalogRepository repository;
    @PostMapping
    public void catalog(@RequestBody ONCatalog dados){
        repository.save(new Catalog(dados));
    }
    @GetMapping(value ="/listar")
    public Page<Catalog> listar(@PageableDefault(page = 0, size = 10, sort = {"id"}) Pageable paginacao) {
        return repository.findAll(paginacao);
    }
    //Id dinâmico como parâmetro que passaremos na URL do insomnia
    @PutMapping(value = "/atualizar/{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid ONCatalog dados, @PathVariable Long id){
        Catalog catalog= repository.findById(id).orElse(null);
        assert catalog != null;
        catalog.atualizar(dados);
        repository.save(catalog);
        return ResponseEntity.ok().body("Catalogo atualizado com sucesso!");
    }
    @DeleteMapping(value = "/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        //Exclusão lógica, mantem arquivado:
        Catalog catalog= repository.findById(id).orElse(null);
        catalog.excluir();
        //Exclui definitivamente:
        //repository.deleteById(id);
    }
}
