package com.controle.estoque.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.controle.estoque.enums.TipoMovimentacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movimentacoes")
public class Movimentacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private BigDecimal quantidade;

    @Column(name = "data_movimentacao", updatable = false)
    private LocalDateTime dataMovimentacao;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produtoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioId;  
    
    @ManyToOne
    @JoinColumn(name = "cidade_origem_id")
    private Cidade cidadeOrigemId;

    @ManyToOne
    @JoinColumn(name = "cidade_destino_id")
    private Cidade cidadeDestinoId;


    @PrePersist
    public void prePersist() {
        this.dataMovimentacao = LocalDateTime.now();
    }

}
