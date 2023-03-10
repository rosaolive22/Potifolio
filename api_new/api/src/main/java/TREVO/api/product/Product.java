package TREVO.api.product;

import TREVO.api.catalog.Catalog;
import TREVO.api.catalog.Culture;
import TREVO.api.image.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.*;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Table(name = "tb_product")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private String size;
    @NotNull
    @Column(name = "status")
    private Boolean status;
    @NotNull
    @Column(name = "desciption", columnDefinition = "Text")//nome no banco de dados
    private String technical_desciption;
    @Column(name = "date_register")
    private LocalDate date_register = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Culture culture;
    private Boolean ativo;
    @OneToMany
    @JoinTable
            //Nome da table new/N coluna new/id desta tabela/2ªlinha refe. da entiadade/table do relacionamento
            (
                    name = "product_image",
                    joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "URL_img", referencedColumnName = "id")}
            )
    private List<Image>imgs;
    @ManyToMany
    @JoinTable
            (
                    name = "product_catalog",
                    joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")}
            )
    private List<Catalog>catalogs;

    public Product(ProductDTO dados, List<Image>imgs, List<Catalog>catalogs) {
        this.name = dados.name();
        this.size = dados.size();
        this.status = dados.status();
        this.culture = dados.culture();
        this.imgs = imgs;
        this.catalogs = catalogs;
        this.ativo = true;
    }

    public void atualizar(ProductDTO dados) {
        if (dados.name() != null) {
            this.name = dados.name();
        }
        if (dados.size() != null) {
            this.size = dados.size();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
        if (dados.culture() != null) {
            this.culture = dados.culture();
        }
    }
    public void excluir() {
        this.ativo = false;
    }
}
