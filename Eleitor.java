/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroeleitoral;

/**
 *
 * @author flaviorgs
 */
public class Eleitor extends Pessoa {
    
    private int zonaEleitoral;
    private String titulo;

    public Eleitor(String nome, String cpf, String dataNascimento, String endereco_rua, String endereco_num, String endereco_bairro, String endereco_cep, String cidade, String estado) {
        super(nome, cpf, dataNascimento, endereco_rua, endereco_num, endereco_bairro, endereco_cep, cidade, estado);
        this.zonaEleitoral = 0;
        this.titulo = "";
    }

    public int getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(int zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
    
    
    
}
