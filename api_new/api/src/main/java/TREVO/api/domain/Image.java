package TREVO.api.domain;


import TREVO.api.DTOs.ImageDTO;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;

@Table(name = "tb_image")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private URL img;
    private Boolean ativo;
    public Image(ImageDTO dados) {
        this.img = dados.img();
        this.ativo = true;
    }

    public void atualizar(ImageDTO dados) {
        if(dados.img() != null){
            this.img = dados.img();
        }
    }
    public void excluir() {
        this.ativo = false;
    }
}
